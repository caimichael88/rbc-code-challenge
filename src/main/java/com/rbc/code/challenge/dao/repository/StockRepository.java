package com.rbc.code.challenge.dao.repository;

import com.rbc.code.challenge.dao.entity.StockEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends CrudRepository<StockEntity, Long> {

    List<StockEntity> findByStock(String stock);
}