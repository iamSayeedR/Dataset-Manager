package com.dataset.manager.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.dataset.manager.entity.DatasetRecord;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DatasetRecordRepositoryTest {

  @Autowired private DatasetRecordRepository repository;

  @Test
  @DisplayName("Should save and retrieve DatasetRecord by dataset name")
  void testSaveAndFindByDatasetName() {
    // given
    DatasetRecord record1 =
        new DatasetRecord(null, "test_dataset", "{\"id\":1,\"name\":\"Alice\"}");
    DatasetRecord record2 = new DatasetRecord(null, "test_dataset", "{\"id\":2,\"name\":\"Bob\"}");
    DatasetRecord record3 =
        new DatasetRecord(null, "other_dataset", "{\"id\":3,\"name\":\"Charlie\"}");

    repository.save(record1);
    repository.save(record2);
    repository.save(record3);

    // when
    List<DatasetRecord> result = repository.findByDatasetName("test_dataset");

    // then
    assertThat(result).hasSize(2);
    assertThat(result.get(0).getDatasetName()).isEqualTo("test_dataset");
    assertThat(result.get(1).getJsonData()).contains("Bob");
  }

  @Test
  @DisplayName("Should return empty list when dataset name not found")
  void testFindByDatasetNameEmpty() {
    List<DatasetRecord> result = repository.findByDatasetName("nonexistent");
    assertThat(result).isEmpty();
  }
}
