package com.dataset.manager.service;

import com.dataset.manager.entity.DatasetRecord;
import com.dataset.manager.repository.DatasetRecordRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatasetService {

  @Autowired private DatasetRecordRepository repository;

  public DatasetRecord insert(String datasetName, String jsonData) {
    DatasetRecord record = new DatasetRecord();
    record.setDatasetName(datasetName);
    record.setJsonData(jsonData);
    return repository.save(record);
  }

  public List<Map<String, Object>> findByDataset(String datasetName)
      throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    List<DatasetRecord> records = repository.findByDatasetName(datasetName);
    List<Map<String, Object>> result = new ArrayList<>();
    for (DatasetRecord record : records) {
      result.add(mapper.readValue(record.getJsonData(), Map.class));
    }
    return result;
  }

  public Map<String, List<Map<String, Object>>> groupBy(
      List<Map<String, Object>> records, String groupByKey) {
    return records.stream()
        .filter(map -> map.containsKey(groupByKey))
        .collect(Collectors.groupingBy(map -> map.get(groupByKey).toString()));
  }

  public List<Map<String, Object>> sortBy(
      List<Map<String, Object>> records, String sortByKey, String order) {
    Comparator<Map<String, Object>> comparator =
        Comparator.comparing(map -> ((Comparable) map.get(sortByKey)));
    if ("desc".equalsIgnoreCase(order)) {
      comparator = comparator.reversed();
    }
    return records.stream()
        .filter(map -> map.containsKey(sortByKey))
        .sorted(comparator)
        .collect(Collectors.toList());
  }
}
