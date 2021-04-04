package com.rbc.code.challenge.controller;

import com.rbc.code.challenge.dao.entity.StockEntity;
import com.rbc.code.challenge.dao.entity.Tutorial;
import com.rbc.code.challenge.helper.StockHelper;
import com.rbc.code.challenge.message.ResponseMessage;
import com.rbc.code.challenge.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Slf4j
//@CrossOrigin("http://localhost:8081")
@Controller
@RequestMapping("/api/csv")
public class StockController {

  @Autowired
  StockService stockService;

  @PostMapping("/upload")
  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
    String message = "";

    if (StockHelper.hasCSVFormat(file)) {
      try {
        stockService.save(file);

        message = "Uploaded the file successfully: " + file.getOriginalFilename();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
      } catch (Exception e) {
        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
      }
    }

    message = "Please upload a csv file!";
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
  }

  @PostMapping("/insert")
  public ResponseEntity<StockEntity> insertStock(@Valid @RequestBody StockEntity newStock) {
    log.info("Calling controller to insert new stock");
    StockEntity createdStock = stockService.insertStock(newStock);

    return new ResponseEntity<>(createdStock, HttpStatus.CREATED);
  }


  @GetMapping(path="/get/{stockName}")
  public ResponseEntity<List<StockEntity>> getStocksByStock (@Valid @PathVariable String stockName) {
    log.info("Calling controller to get stocks by stock {}", stockName);

    try {
      List<StockEntity> stocks = stockService.getStocksByStockName(stockName);

      if (stocks.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(stocks, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  @GetMapping("/stocks")
  public ResponseEntity<List<StockEntity>> getAllStocks() {
    try {
      List<StockEntity> stocks = stockService.getStocks();

      if (stocks.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(stocks, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/tutorials")
  public ResponseEntity<List<Tutorial>> getAllTutorials() {
    try {
      List<Tutorial> tutorials = stockService.getAllTutorials();

      if (tutorials.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(tutorials, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/download")
  public ResponseEntity<Resource> getFile() {
    String filename = "tutorials.csv";
    InputStreamResource file = new InputStreamResource(stockService.load());

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
        .contentType(MediaType.parseMediaType("application/csv"))
        .body(file);
  }

}
