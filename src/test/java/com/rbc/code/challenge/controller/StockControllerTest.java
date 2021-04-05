package com.rbc.code.challenge.controller;


import com.rbc.code.challenge.dao.entity.StockEntity;
import com.rbc.code.challenge.helper.StockHelper;
import com.rbc.code.challenge.service.StockService;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class StockControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StockService stockService;

    @InjectMocks
    private StockController controller;

    @BeforeEach
    public void setup() {
        StandaloneMockMvcBuilder builder = MockMvcBuilders.standaloneSetup(controller);
        mockMvc = builder.build();
    }

    @Test
    @SneakyThrows
    public void uploadStockFileSuccess() {

        final String fileName = "test.csv";
        byte[] fileContent = "This is the file content".getBytes();
        MockMultipartFile attachment = new MockMultipartFile("attachment", fileName, "text/csv", fileContent);

        mockMvc.perform(multipart("/api/rbc/upload")
                .file(attachment))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @SneakyThrows
    public void uploadStockFileFail() {

        final String fileName = "test.csv";
        byte[] fileContent = "This is the file content".getBytes();
        MockMultipartFile attachment = new MockMultipartFile("attachment", fileName, "text", fileContent);

        mockMvc.perform(multipart("/api/rbc/upload")
                .file(attachment))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @SneakyThrows
    public void ShouldInsertStock() {

        String requestBody = getJsonString("/newStock.json");

        mockMvc.perform(post("/api/rbc/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

    }

    @Test
    @SneakyThrows
    public void shouldGetEmptyStocksByStock() {

        final String stockName = "AA";
        List<StockEntity> stocks = new ArrayList<>();

        when(stockService.getStocksByStockName(stockName)).thenReturn(stocks);
        mockMvc.perform(get("/api/rbc/get/{stockName}", stockName)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    public void shouldGetStocksByStock() {

        final String stockName = "AA";
        List<StockEntity> stocks = new ArrayList<>();
        StockEntity mock = new StockEntity();
        mock.setClose("12.34");
        mock.setQuarter("1");
        stocks.add(mock);

        when(stockService.getStocksByStockName(stockName)).thenReturn(stocks);
        mockMvc.perform(get("/api/rbc/get/" + stockName)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @SneakyThrows
    public void shouldGetEmptyStocks() {

        List<StockEntity> stocks = new ArrayList<>();

        when(stockService.getStocks()).thenReturn(stocks);
        mockMvc.perform(get("/api/rbc/stocks")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    public void shouldGetStocks() {

        List<StockEntity> stocks = new ArrayList<>();
        StockEntity mock = new StockEntity();
        mock.setClose("12.34");
        mock.setQuarter("1");
        stocks.add(mock);

        when(stockService.getStocks()).thenReturn(stocks);
        mockMvc.perform(get("/api/rbc/stocks")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    private String getJsonString(String path) {
        try (InputStream is = this.getClass().getResourceAsStream(path)) {
            return IOUtils.toString(is, StandardCharsets.UTF_8.toString());
        } catch (IOException e) {
            return "{}";
        }
    }


}


