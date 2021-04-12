package com.rbc.code.challenge.controller;

import com.rbc.code.challenge.dao.entity.StockEntity;
import com.rbc.code.challenge.message.ResponseMessage;
import com.rbc.code.challenge.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/api/rbc")
public class StockController {

  private StockService stockService;

  @Autowired
  public StockController(StockService stockService) {
    this.stockService = stockService;
  }

//  @PostMapping("/upload")
//  public ResponseEntity<ResponseMessage> uploadStockFile(@RequestParam("attachment") MultipartFile attachment) {
//    String message = "";
//    log.info("Calling controller to upload file");
//
//    if (StockHelper.hasCSVFormat(attachment)) {
//      try {
//        stockService.saveStocks(attachment);
//
//        message = "Uploaded the file successfully: " + attachment.getOriginalFilename();
//        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
//      } catch (Exception e) {
//        message = "Could not upload the file: " + attachment.getOriginalFilename() + "!";
//        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
//      }
//    }
//
//    message = "Please upload a csv file!";
//    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
//  }

  @PostMapping("/upload")
  public ResponseEntity<ResponseMessage> uploadStocks(@RequestParam("attachment") MultipartFile attachment) {

    log.info("Calling controller to upload file");
    return stockService.uploadStocks(attachment);
  }

//  @PostMapping("/insert")
//  public ResponseEntity<StockEntity> insertStock(@Valid @RequestBody StockEntity newStock) {
//    log.info("Calling controller to insert new stock {}", newStock);
//    StockEntity createdStock = stockService.insertStock(newStock);
//
//    return new ResponseEntity<>(createdStock, HttpStatus.CREATED);
//  }

  @PostMapping("/insert")
  public ResponseEntity<StockEntity> insertStock(@Valid @RequestBody StockEntity newStock) {

    log.info("Calling controller to insert new stock {}", newStock);
    return stockService.insertStock(newStock);
  }


//  @GetMapping(path="/get/{stockName}")
//  public ResponseEntity<List<StockEntity>> getStocksByStock (@Valid @PathVariable String stockName) {
//    log.info("Calling controller to get stocks by stock {}", stockName);
//
//    try {
//      List<StockEntity> stocks = stockService.getStocksByStockName(stockName);
//
//      if (stocks.isEmpty()) {
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//      }
//
//      ResponseEntity<List<StockEntity>> listResponseEntity = new ResponseEntity<>(stocks, HttpStatus.OK);
//
//      return listResponseEntity;
//    } catch (Exception e) {
//      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//  }

  @GetMapping(path="/get/{stockName}")
  public ResponseEntity<List<StockEntity>> getStocksByStock (@Valid @PathVariable String stockName) {

    log.info("Calling controller to get stocks by stock {}", stockName);

    return stockService.getStocksByStock(stockName);
  }

//  @GetMapping("/stocks")
//  public ResponseEntity<List<StockEntity>> getAllStocks() {
//
//    log.info("Calling controller to get all stocks");
//
//    try {
//      List<StockEntity> stocks = stockService.getStocks();
//
//      if (stocks.isEmpty()) {
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//      }
//
//      return new ResponseEntity<>(stocks, HttpStatus.OK);
//    } catch (Exception e) {
//      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//  }

  @GetMapping("/stocks")
  public ResponseEntity<List<StockEntity>> getAllStocks() {

    log.info("Calling controller to get all stocks");
    return stockService.getAllStocks();
  }

}
