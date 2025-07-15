package com.dataset.manager.model;

import java.util.List;
import java.util.Map;

public class SortedResponse {

  private List<Map<String, Object>> sortedRecords;

  public SortedResponse() {}

  public SortedResponse(List<Map<String, Object>> sortedRecords) {
    this.sortedRecords = sortedRecords;
  }

  public List<Map<String, Object>> getSortedRecords() {
    return sortedRecords;
  }

  public void setSortedRecords(List<Map<String, Object>> sortedRecords) {
    this.sortedRecords = sortedRecords;
  }
}
