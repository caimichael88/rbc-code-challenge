package com.rbc.code.challenge.service;

import com.rbc.code.challenge.controller.StockController;
import com.rbc.code.challenge.dao.repository.StockRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.anyList;

@ExtendWith(MockitoExtension.class)
public class StockServiceTest {

    private MockMvc mockMvc;

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockService stockService;

    @BeforeEach
    public void setup() {
    }

    @Test
    @SneakyThrows
    public void shouldSaveStocks() {
        final String fileName = "test.csv";
        byte[] fileContent = "This is the file content".getBytes();
        MockMultipartFile attachment = new MockMultipartFile("attachment", fileName, "text/csv", fileContent);

        stockService.saveStocks(attachment);
        Mockito.verify(stockRepository).saveAll(anyList());
    }


}