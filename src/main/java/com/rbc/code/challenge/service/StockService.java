package com.rbc.code.challenge.service;

import com.rbc.code.challenge.dao.entity.StockEntity;
import com.rbc.code.challenge.dao.repository.StockRepository;
import com.rbc.code.challenge.message.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class StockService {

  private final StockRepository stockRepository;

  @Autowired
  public StockService(StockRepository stockRepository) {
    this.stockRepository = stockRepository;
  }

  public ResponseEntity<ResponseMessage> uploadStocks(MultipartFile attachment) {

    final String TYPE = "text/csv";
    log.info("Calling StockService to upload stocks");

    if(TYPE.equals(attachment.getContentType())) {
      try {
        saveStocks(attachment);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseMessage("Uploaded the file successfully: "
                        + attachment.getOriginalFilename()));
      } catch(Exception e) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                new ResponseMessage("Could not upload the file: " +
                        attachment.getOriginalFilename() + "!"));
      }
    }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            new ResponseMessage("Please upload a csv file!"));
  }

  public ResponseEntity<StockEntity> insertStock(StockEntity newStock) {

    log.info("Calling StockService to insert new stock {}", newStock);
    StockEntity createdStock = stockRepository.save(newStock);

    return new ResponseEntity<>(createdStock, HttpStatus.CREATED);
  }


  public void saveStocks(MultipartFile file) {

    log.info("Calling StockService to save stocks from uploaded file");
    try {
      InputStream is = file.getInputStream();
      BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

      CSVParser csvParser = new CSVParser(fileReader,
              CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
      Iterable<CSVRecord> csvRecords = csvParser.getRecords();

      ModelMapper modelMapper = new ModelMapper();

      List<StockEntity> transformedDTOs = StreamSupport
              .stream(csvRecords.spliterator(), false)
              .map(csvRecord -> createStockEntityFromRecord(csvRecord))
              .collect(Collectors.toList());
      stockRepository.saveAll(transformedDTOs);

    }catch(IOException e) {
      throw new RuntimeException("fail to store csv data: " + e.getMessage());
    }
  }

  private StockEntity createStockEntityFromRecord(CSVRecord csvRecord) {
    return new StockEntity(
            0,
            csvRecord.get("quarter"),
            csvRecord.get("stock"),
            csvRecord.get("date"),
            csvRecord.get("open"),
            csvRecord.get("high"),
            csvRecord.get("low"),
            csvRecord.get("close"),
            csvRecord.get("volume"),
            csvRecord.get("percent_change_price"),
            csvRecord.get("percent_change_volume_over_last_wk"),
            csvRecord.get("previous_weeks_volume"),
            csvRecord.get("next_weeks_open"),
            csvRecord.get("next_weeks_close"),
            csvRecord.get("percent_change_next_weeks_price"),
            csvRecord.get("days_to_next_dividend"),
            csvRecord.get("percent_return_next_dividend"));
  }


  public ResponseEntity<List<StockEntity>> getStocksByStock(String stockName) {

    log.info("Calling StockService to get stocks by stock {}", stockName);
    try {
      List<StockEntity> stocks = getStocksByStockName(stockName);

      if (stocks.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(stocks, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public List<StockEntity> getStocksByStockName(String stockName) {
    Iterable<StockEntity> iterable = stockRepository.findByStock(stockName);
    return StreamSupport
            .stream(iterable.spliterator(), false)
            .collect(Collectors.toList());
  }

  public ResponseEntity<List<StockEntity>> getAllStocks() {

    log.info("Calling StockService to get all stocks");

    try {
      List<StockEntity> stocks = getStocks();

      if (stocks.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(stocks, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public List<StockEntity> getStocks() {
    Iterable<StockEntity> iterable = stockRepository.findAll();
    return StreamSupport
            .stream(iterable.spliterator(), false)
            .collect(Collectors.toList());
  }
}
