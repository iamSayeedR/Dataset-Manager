package com.dataset.manager.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.dataset.manager.entity.DatasetRecord;
import com.dataset.manager.repository.DatasetRecordRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class DatasetServiceTest {

  private final ObjectMapper objectMapper = new ObjectMapper();
  @InjectMocks private DatasetService service;
  @Mock private DatasetRecordRepository repository;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldInsertDatasetRecord() {
    String datasetName = "employee_dataset";
    String jsonData = "{\"id\":1,\"name\":\"John\",\"age\":30}";

    DatasetRecord mockRecord = new DatasetRecord(null, datasetName, jsonData);
    when(repository.save(any())).thenReturn(mockRecord);

    DatasetRecord result = service.insert(datasetName, jsonData);

    assertThat(result.getDatasetName()).isEqualTo(datasetName);
    assertThat(result.getJsonData()).isEqualTo(jsonData);
    verify(repository).save(any(DatasetRecord.class));
  }

  @Test
  void shouldReturnParsedJsonList() throws Exception {
    String json = "{\"id\":1,\"name\":\"John\"}";
    List<DatasetRecord> mockList = List.of(new DatasetRecord(1L, "ds", json));

    when(repository.findByDatasetName("ds")).thenReturn(mockList);

    List<Map<String, Object>> result = service.findByDataset("ds");

    assertThat(result).hasSize(1);
    assertThat(result.get(0)).containsEntry("name", "John");
  }

  @Test
  void shouldGroupByKey() {
    Map<String, Object> r1 = Map.of("dept", "HR", "name", "Alice");
    Map<String, Object> r2 = Map.of("dept", "IT", "name", "Bob");
    Map<String, Object> r3 = Map.of("dept", "HR", "name", "Charlie");

    List<Map<String, Object>> input = List.of(r1, r2, r3);

    Map<String, List<Map<String, Object>>> result = service.groupBy(input, "dept");

    assertThat(result).containsKeys("HR", "IT");
    assertThat(result.get("HR")).hasSize(2);
  }

  @Test
  void shouldSortByKeyAsc() {
    Map<String, Object> r1 = Map.of("age", 30);
    Map<String, Object> r2 = Map.of("age", 20);
    Map<String, Object> r3 = Map.of("age", 25);

    List<Map<String, Object>> input = List.of(r1, r2, r3);
    List<Map<String, Object>> sorted = service.sortBy(input, "age", "asc");

    assertThat(sorted.get(0).get("age")).isEqualTo(20);
    assertThat(sorted.get(2).get("age")).isEqualTo(30);
  }
}
