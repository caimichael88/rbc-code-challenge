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

  @Column(name = "high")
  private String high;

  @Column(name = "low")
  private String low;

  @Column(name = "close")
  private String close;

  @Column(name = "volume")
  private String volume;

  @Column(name = "percent_change_price")
  private String percent_change_price;

  @Column(name = "percent_change_volume_over_last_wk")
  private String percent_change_volume_over_last_wk;

  @Column(name = "previous_weeks_volume")
  private String previous_weeks_volume;

  @Column(name = "next_weeks_open")
  private String next_weeks_open;

  @Column(name = "next_weeks_close")
  private String next_weeks_close;

  @Column(name = "percent_change_next_weeks_price")
  private String percent_change_next_weeks_price;

  @Column(name = "days_to_next_dividend")
  private String days_to_next_dividend;

  @Column(name = "percent_return_next_dividend")
  private String percent_return_next_dividend;

  public StockEntity(String quarter, String stock, String date, String open,
                     String high,
                     String low,
                     String close,
                     String volume,
                     String percent_change_price,
                     String percent_change_volume_over_last_wk,
                     String previous_weeks_volume,
                     String next_weeks_open,
                     String next_weeks_close,
                     String percent_change_next_weeks_price,
                     String days_to_next_dividend,
                     String percent_return_next_dividend) {
    this.quarter = quarter;
    this.stock = stock;
    this.date = date;
    this.open = open;
    this.high = high;
    this.low = low;
    this.close = close;
    this.volume = volume;
    this.percent_change_price = percent_change_price;
    this.percent_change_volume_over_last_wk = percent_change_volume_over_last_wk;
    this.previous_weeks_volume = previous_weeks_volume;
    this.next_weeks_open = next_weeks_open;
    this.next_weeks_close = next_weeks_close;
    this.percent_change_next_weeks_price = percent_change_next_weeks_price;
    this.days_to_next_dividend = days_to_next_dividend;
    this.percent_return_next_dividend = percent_return_next_dividend;
  }

}
