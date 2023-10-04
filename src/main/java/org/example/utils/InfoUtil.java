package org.example.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.example.entity.Customer;
import org.example.entity.Movie;
import org.example.enums.MovieType;
import org.example.service.MovieService;
import org.example.service.MovieServiceImpl;
import org.example.strategy.MoviePricingStrategy;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.List;


public class InfoUtil {
    public static final String CSV_FILE_PATH = "customer.csv";
    public static final String RENTAL_FILE_PATH = "rentals.csv";
    private static MovieService movieService = new MovieServiceImpl();

    private InfoUtil() {
    }

    public static String showInfo(Customer customer) throws IOException {

        FileReader rentalsFileReader = new FileReader(RENTAL_FILE_PATH);
        CSVParser rentalsCsvParser = CSVParser.parse(rentalsFileReader, CSVFormat.DEFAULT.withHeader().withIgnoreHeaderCase().withTrim());

        double totalAmount = 0;
        int renterPoints = 0;
        String result = "Rental Record for " + customer.getName() + "\n";
        for (CSVRecord record : rentalsCsvParser) {
            long customerId = Long.parseLong(record.get("CustomerId"));
            if (customerId == customer.getId()) {
                long movieId = Long.parseLong(record.get("MovieId"));
                Movie movie = movieService.findById(movieId);
                MovieType priceCode = movie.getPriceCode();
                int rentalDays = getRentalDays(customerId, movieId);

                double movieCost = calculateMovieCost(priceCode, rentalDays);

                totalAmount += movieCost;

                renterPoints += calculateBonusPoints(priceCode, rentalDays, customer);
            }
        }
        //add footer lines
        result += "Amount owed is " + totalAmount + "\n";
        result += "You earned " + renterPoints + " frequent renter points";
        return result;

    }

    private static int calculateBonusPoints(MovieType priceCode, int rentalDays, Customer customer) {
        int initialPoints = getInitialBonusPoints(customer.getId());
        int bonusPoints = 1;
        if (priceCode == MovieType.NEW_RELEASE && rentalDays > 2) {
            bonusPoints++;
        }
        int bonusSum = initialPoints + bonusPoints;

        if (bonusSum != initialPoints + 1) {
            try {
                FileReader fileReader = new FileReader("customer.csv");
                CSVParser csvParser = CSVParser.parse(fileReader, CSVFormat.DEFAULT.withHeader().withIgnoreHeaderCase().withTrim());

                List<CSVRecord> records = csvParser.getRecords();

                for (int i = 0; i < records.size(); i++) {
                    CSVRecord record = records.get(i);
                    long customerId = Long.parseLong(record.get("Id"));
                    if (customerId == customer.getId()) {
                        record.toMap().put("Points", String.valueOf(bonusSum));
                    }
                }

                FileWriter fileWriter = new FileWriter(CSV_FILE_PATH);
                CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withHeader(csvParser.getHeaderMap().keySet().toArray(new String[0])));

                for (CSVRecord record : records) {
                    csvPrinter.printRecord(record);
                }

                csvPrinter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bonusSum;
    }

    private static int getInitialBonusPoints(long customerId) {
        int initialPoints = 0;

        try (Reader reader = new FileReader(CSV_FILE_PATH);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {

            for (CSVRecord record : csvParser) {
                long id = Long.parseLong(record.get("Id"));
                if (id == customerId) {
                    initialPoints = Integer.parseInt(record.get("Points"));
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return initialPoints;
    }

    private static double calculateMovieCost(MovieType priceCode, int rentalDays) {
        MoviePricingStrategy pricingStrategy = MoviePricingStrategyFactory.createPricingStrategy(priceCode);
        return pricingStrategy.calculateCost(rentalDays);
    }


    private static int getRentalDays(long customerId, long movieId) {
        try {
            FileReader fileReader = new FileReader(RENTAL_FILE_PATH);
            CSVParser csvParser = CSVParser.parse(fileReader, CSVFormat.DEFAULT.withHeader().withIgnoreHeaderCase().withTrim());

            for (CSVRecord record : csvParser) {
                long recordCustomerId = Long.parseLong(record.get("CustomerId"));
                long recordMovieId = Long.parseLong(record.get("MovieId"));

                if (recordCustomerId == customerId && recordMovieId == movieId) {
                    return Integer.parseInt(record.get("RentalDays"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
