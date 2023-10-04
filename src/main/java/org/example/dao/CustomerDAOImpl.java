package org.example.dao;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.example.entity.Customer;
import org.example.entity.Movie;
import org.example.entity.Rental;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDAOImpl implements CustomerDAO {
    public static final String CSV_FILE_PATH = "customer.csv";
    public static final String RENTAL_FILE_PATH = "rentals.csv";

    private MovieDAO movieDAO = new MovieDAOImpl();

    @Override
    public void addCustomer(Customer customer) {
        try (CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(CSV_FILE_PATH, true), CSVFormat.DEFAULT)) {
            csvPrinter.printRecord(customer.getId(), customer.getName(), customer.getRentalPoints());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeCustomer(long id) {
        try {
            FileReader fileReader = new FileReader(CSV_FILE_PATH);
            CSVParser csvParser = CSVParser.parse(fileReader, CSVFormat.DEFAULT.withHeader().withIgnoreHeaderCase().withTrim());
            List<CSVRecord> records = csvParser.getRecords();
            int indexToRemove = -1;
            for (int i = 0; i < records.size(); i++) {
                CSVRecord record = records.get(i);
                long recordId = Long.parseLong(record.get("Id"));
                if (recordId == id) {
                    indexToRemove = i;
                    break;
                }
            }
            if (indexToRemove != -1) {
                records.remove(indexToRemove);
                FileWriter fileWriter = new FileWriter(CSV_FILE_PATH);
                CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withHeader(csvParser.getHeaderMap().keySet().toArray(new String[0])));
                for (CSVRecord record : records) {
                    csvPrinter.printRecord(record);
                }
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Optional<Customer> findById(long id) {
        try {
            FileReader fileReader = new FileReader(CSV_FILE_PATH);
            CSVParser csvParser = CSVParser.parse(fileReader, CSVFormat.DEFAULT.withHeader().withIgnoreHeaderCase().withTrim());
            List<CSVRecord> records = csvParser.getRecords();
            for (CSVRecord record : records) {
                long recordId = Long.parseLong(record.get("Id"));
                if (recordId == id) {
                    String name = record.get("Name");
                    int points = Integer.parseInt(record.get("Points"));
                    Customer customer = new Customer();
                    customer.setId(recordId);
                    customer.setName(name);
                    customer.setRentalPoints(points);
                    return Optional.of(customer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(CSV_FILE_PATH);
            CSVParser csvParser = CSVParser.parse(fileReader, CSVFormat.DEFAULT.withHeader().withIgnoreHeaderCase().withTrim());
            for (CSVRecord csvRecord : csvParser) {
                long id = Long.parseLong(csvRecord.get("Id"));
                String name = csvRecord.get("Name");
                int points = Integer.parseInt(csvRecord.get("Points"));
                Customer customer = new Customer();
                customer.setId(id);
                customer.setName(name);
                customer.setRentalPoints(points);
                customers.add(customer);
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public void addRental(Customer customer, Rental rental) {
        try (CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(RENTAL_FILE_PATH, true), CSVFormat.DEFAULT)) {
            csvPrinter.printRecord(rental.getId(), rental.getMovie().getId(), rental.getDaysRented(), customer.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeRental(long id) {
        try {
            FileReader fileReader = new FileReader(RENTAL_FILE_PATH);
            CSVParser csvParser = CSVParser.parse(fileReader, CSVFormat.DEFAULT.withHeader().withIgnoreHeaderCase().withTrim());

            List<CSVRecord> records = csvParser.getRecords();
            int indexToRemove = -1;

            for (int i = 0; i < records.size(); i++) {
                CSVRecord record = records.get(i);
                long recordId = Long.parseLong(record.get("Id"));
                if (recordId == id) {
                    indexToRemove = i;
                    break;
                }
            }

            if (indexToRemove != -1) {
                records.remove(indexToRemove);
                FileWriter fileWriter = new FileWriter(RENTAL_FILE_PATH);
                String[] headers = csvParser.getHeaderMap().keySet().toArray(new String[0]);
                fileWriter.append(String.join(",", headers));
                fileWriter.append("\n");
                CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT);
                for (CSVRecord record : records) {
                    csvPrinter.printRecord(record);
                }
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Rental> findRental(long id) {
        try {
            FileReader fileReader = new FileReader(RENTAL_FILE_PATH);
            CSVParser csvParser = CSVParser.parse(fileReader, CSVFormat.DEFAULT.withHeader().withIgnoreHeaderCase().withTrim());
            List<CSVRecord> records = csvParser.getRecords();
            for (CSVRecord record : records) {
                long recordId = Long.parseLong(record.get("Id"));
                if (recordId == id) {
                    long movieId = Long.parseLong(record.get("MovieId"));
                    int days = Integer.parseInt(record.get("DaysRented"));
                    long customerId = Long.parseLong(record.get("CustomerId"));
                    Optional<Movie> movie = movieDAO.findById(movieId);
                    Rental rental = new Rental();
                    rental.setId(recordId);
                    rental.setMovie(movie.get());
                    rental.setDaysRented(days);

                    return Optional.of(rental);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
