package com.dataset.manager.repository;

import com.dataset.manager.entity.DatasetRecord;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatasetRecordRepository extends JpaRepository<DatasetRecord, Long> {
  List<DatasetRecord> findByDatasetName(String datasetName);
}
