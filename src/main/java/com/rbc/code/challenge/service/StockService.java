package com.rbc.code.challenge.service;

import com.rbc.code.challenge.dao.entity.StockEntity;
import com.rbc.code.challenge.dao.entity.Tutorial;
import com.rbc.code.challenge.dao.repository.StockRepository;
import com.rbc.code.challenge.dao.repository.TutorialRepository;
import com.rbc.code.challenge.helper.StockHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StockService {
  @Autowired
  TutorialRepository repository;

  @Autowired
  StockRepository stockRepository;

  public void save(MultipartFile file) {
    try {
      List<Tutorial> tutorials = StockHelper.csvToTutorials(file.getInputStream());
      repository.saveAll(tutorials);
    } catch (IOException e) {
      throw new RuntimeException("fail to store csv data: " + e.getMessage());
    }
  }

  public ByteArrayInputStream load() {
    List<Tutorial> tutorials = repository.findAll();

    ByteArrayInputStream in = StockHelper.tutorialsToCSV(tutorials);
    return in;
  }

  public List<Tutorial> getAllTutorials() {
    return repository.findAll();
  }

  public List<StockEntity> getStocks() {
    Iterable<StockEntity> iterable = stockRepository.findAll();
    return StreamSupport
            .stream(iterable.spliterator(), false)
            .collect(Collectors.toList());
  }

  public List<StockEntity> getStocksByStockName(String stockName) {
    Iterable<StockEntity> iterable = stockRepository.findByStock(stockName);
    return StreamSupport
            .stream(iterable.spliterator(), false)
            .collect(Collectors.toList());
  }

  public StockEntity insertStock(StockEntity newStock) {
    return stockRepository.save(newStock);
  }
}
