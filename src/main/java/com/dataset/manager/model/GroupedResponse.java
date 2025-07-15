package com.dataset.manager.model;

import java.util.List;
import java.util.Map;

public class GroupedResponse {

  private Map<String, List<Map<String, Object>>> groupedRecords;

  public GroupedResponse() {}

  public GroupedResponse(Map<String, List<Map<String, Object>>> groupedRecords) {
    this.groupedRecords = groupedRecords;
  }

  public Map<String, List<Map<String, Object>>> getGroupedRecords() {
    return groupedRecords;
  }

  public void setGroupedRecords(Map<String, List<Map<String, Object>>> groupedRecords) {
    this.groupedRecords = groupedRecords;
  }
}
