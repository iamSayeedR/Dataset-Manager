package com.dataset.manager.controller;

import com.dataset.manager.entity.DatasetRecord;
import com.dataset.manager.model.GroupedResponse;
import com.dataset.manager.model.SortedResponse;
import com.dataset.manager.service.DatasetService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dataset")
public class DatasetController {

  @Autowired private DatasetService service;

  @PostMapping("/{datasetName}/record")
  public ResponseEntity<?> insert(@PathVariable String datasetName, @RequestBody String record) {
    DatasetRecord saved = service.insert(datasetName, record);
    return ResponseEntity.ok(
        Map.of(
            "message",
            "Record added successfully",
            "dataset",
            datasetName,
            "recordId",
            saved.getId()));
  }

  @GetMapping("/{datasetName}/query")
  public ResponseEntity<?> query(
      @PathVariable String datasetName,
      @RequestParam(required = false) String groupBy,
      @RequestParam(required = false) String sortBy,
      @RequestParam(required = false, defaultValue = "asc") String order)
      throws Exception {
    List<Map<String, Object>> records = service.findByDataset(datasetName);

    if (groupBy != null) {
      return ResponseEntity.ok(new GroupedResponse(service.groupBy(records, groupBy)));
    } else if (sortBy != null) {
      return ResponseEntity.ok(new SortedResponse(service.sortBy(records, sortBy, order)));
    }

    return ResponseEntity.ok(Map.of("records", records));
  }
}
