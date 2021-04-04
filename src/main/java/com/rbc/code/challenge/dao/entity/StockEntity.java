package com.rbc.code.challenge.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stock")
public class StockEntity {

  @Id
  private int id;

  @Column(name = "quarter")
  private String quarter;

  @Column(name = "stock")
  private String stock;

  @Column(name = "date")
  private String date;

  @Column(name = "open")
  private String open;

}
