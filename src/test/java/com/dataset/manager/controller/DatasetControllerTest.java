package com.dataset.manager.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dataset.manager.entity.DatasetRecord;
import com.dataset.manager.service.DatasetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class DatasetControllerTest {

  @InjectMocks DatasetController datasetController;

  @Autowired private MockMvc mockMvc;

  @Mock private DatasetService service;

  @Autowired private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(datasetController).build();
  }

  @Test
  void shouldInsertDatasetRecord() throws Exception {
    String datasetName = "employee_dataset";
    String jsonBody = "{\"id\":1,\"name\":\"John\"}";
    DatasetRecord mockRecord = new DatasetRecord(1L, datasetName, jsonBody);

    when(service.insert(eq(datasetName), anyString())).thenReturn(mockRecord);

    mockMvc
        .perform(
            post("/api/dataset/{datasetName}/record", datasetName)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value("Record added successfully"))
        .andExpect(jsonPath("$.dataset").value(datasetName));
  }

  @Test
  void shouldQueryWithGroupBy() throws Exception {
    String datasetName = "employee_dataset";
    Map<String, List<Map<String, Object>>> grouped =
        Map.of(
            "IT", List.of(Map.of("name", "Alice")),
            "HR", List.of(Map.of("name", "Bob")));

    when(service.findByDataset(datasetName))
        .thenReturn(
            List.of(
                Map.of("name", "Alice", "department", "IT"),
                Map.of("name", "Bob", "department", "HR")));
    when(service.groupBy(any(), eq("department"))).thenReturn(grouped);

    mockMvc
        .perform(
            get("/api/dataset/{datasetName}/query", datasetName).param("groupBy", "department"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.groupedRecords.IT[0].name").value("Alice"));
  }
}
