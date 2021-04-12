package com.rbc.code.challenge.helper;

import com.rbc.code.challenge.dao.entity.StockEntity;
import org.apache.commons.csv.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class StockHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERs = { "Id", "Title", "Description", "Published" };

    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<StockEntity> csvToStocks(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<StockEntity> tutorials = new ArrayList<StockEntity>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            return StreamSupport
                    .stream(csvRecords.spliterator(), false)
                    .map(csvRecord -> new StockEntity(
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
                            csvRecord.get("percent_return_next_dividend")

                    ))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

}