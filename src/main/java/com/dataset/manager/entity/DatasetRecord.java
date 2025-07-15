package com.dataset.manager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "dataset_records")
public class DatasetRecord {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String datasetName;

  @Column(columnDefinition = "TEXT")
  private String jsonData;

  public DatasetRecord() {}

  public DatasetRecord(Long id, String datasetName, String jsonData) {
    this.id = id;
    this.datasetName = datasetName;
    this.jsonData = jsonData;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDatasetName() {
    return datasetName;
  }

  public void setDatasetName(String datasetName) {
    this.datasetName = datasetName;
  }

  public String getJsonData() {
    return jsonData;
  }

  public void setJsonData(String jsonData) {
    this.jsonData = jsonData;
  }
}
