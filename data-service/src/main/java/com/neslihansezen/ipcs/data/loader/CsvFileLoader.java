package com.neslihansezen.ipcs.data.loader;

import com.neslihansezen.ipcs.data.entity.RegionMap;
import com.neslihansezen.ipcs.data.enums.Region;
import com.neslihansezen.ipcs.data.exception.CsvFileReadException;
import com.neslihansezen.ipcs.data.exception.InvalidCsvFileException;
import com.neslihansezen.ipcs.data.repository.RegionRepository;
import com.opencsv.CSVReader;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.neslihansezen.ipcs.data.constants.Messages.CSV_FILE_NOT_FOUND;
import static com.neslihansezen.ipcs.data.constants.Messages.CSV_FILE_READ_ERROR;

/**
 * A component responsible for loading data from a specified CSV file containing mappings of postcodes to regions
 * into a database. The CSV file is configured via application properties, and the data is stored in the
 * {@link RegionRepository}.
 * <p>
 * The component is initialized after the application context is started, and it validates the existence
 * of the CSV file and manages the data loading process.
 * <p>
 * Constructor Parameters:
 * - {@code RegionRepository repository}: The repository interface for persisting region mappings.
 * - {@code String csvFilePath}: Path to the CSV file as defined in application properties.
 * - {@code int keyColumn_Postcode}: The index of the column in the CSV file representing postcodes.
 * - {@code int valueColumn_Region}: The index of the column in the CSV file representing regions.
 * <p>
 * Behavior:
 * - Checks if the CSV file exists. If the file does not exist, an {@code InvalidCsvFileException} is thrown.
 * - If the data is already loaded into the database (as determined by repository data count), the operation will be skipped.
 * - Reads data from the CSV file and constructs a map of postcodes to regions.
 * - Saves the data into the database by creating entries in the {@code postcode_region_map} table.
 * - Converts each region to its corresponding region factor using the {@code Region} enumeration.
 * <p>
 * Exceptions:
 * - {@code InvalidCsvFileException} is thrown if the CSV file is not found at the specified path.
 * - {@code CsvFileReadException} is thrown if there's an error reading from the CSV file or processing its data.
 */
@Component
public class CsvFileLoader {
    private final String csvFilePath;
    private final int keyColumn_Postcode;
    private final int valueColumn_Region;
    private final RegionRepository repository;

    public CsvFileLoader(RegionRepository repository,
                         @Value("${csv.file.path}") String csvFilePath,
                         @Value("${csv.file.postcode.column}") int keyColumn_Postcode,
                         @Value("${csv.file.region.column}") int valueColumn_Region) {
        this.repository = repository;
        this.csvFilePath = csvFilePath;
        this.keyColumn_Postcode = keyColumn_Postcode;
        this.valueColumn_Region = valueColumn_Region;
    }

    @PostConstruct
    private void initializeDataFromCsv() {
        validateCsvFile();

        if (isDataAlreadyLoaded()) {
            System.out.println("CSV file already loaded");
            return;
        }

        System.out.println("CsvFile loading...");
        Map<String, String> records = loadRecordsFromCsv();

        saveRecordsToDatabase(records);
        System.out.println("CsvFile successfully loaded to DB: " + csvFilePath);
    }

    private void validateCsvFile() {
        ClassPathResource resource = new ClassPathResource(csvFilePath);
        if (!resource.exists()) {
            throw new InvalidCsvFileException(CSV_FILE_NOT_FOUND + ": " + csvFilePath);
        }
    }

    private boolean isDataAlreadyLoaded() {
        return repository.count() > 0;
    }

    private Map<String, String> loadRecordsFromCsv() {
        final int INITIAL_CAPACITY = 8232;
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(
                new ClassPathResource(csvFilePath).getInputStream(), StandardCharsets.UTF_8))) {
            Map<String, String> records = new HashMap<>(INITIAL_CAPACITY);
            csvReader.readNext(); // Skip header
            String[] record;
            while ((record = csvReader.readNext()) != null) {
                records.putIfAbsent(record[keyColumn_Postcode], record[valueColumn_Region]);
            }
            return records;
        } catch (Exception e) {
            throw new CsvFileReadException(CSV_FILE_READ_ERROR + ": " + e.getMessage());
        }
    }

    /**
     *
     * @param records A map where the key represents a postcode and the value represents a region.
     *                These key-value pairs will be used to populate the RegionMap entity and store it in the database.
     *                The region factor is derived dynamically based on the region using the {Region} enum.
     */
    private void saveRecordsToDatabase(Map<String, String> records) {
        records.forEach((postcode, region) -> repository.save(
                RegionMap.builder()
                        .postcode(postcode)
                        .region(region)
                        .regionFactor(Region.getFactorByRegion(region))
                        .build()
        ));
    }
}
