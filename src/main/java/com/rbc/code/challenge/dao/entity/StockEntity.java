package com.rbc.code.challenge.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "stock")
public class StockEntity {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private int id;

  @Column(name = "quarter")
  private String quarter;

  @Column(name = "stock")
  private String stock;

  @Column(name = "date")
  private String date;

  @Column(name = "open")
  private String open;

  public StockEntity(String quarter, String stock, String date, String open) {
    this.quarter= quarter;
    this.stock = stock;
    this.date = date;
    this.open = open;
  }

}
