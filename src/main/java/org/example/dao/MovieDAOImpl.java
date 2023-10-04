package org.example.dao;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.example.entity.Movie;
import org.example.enums.MovieType;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

public class MovieDAOImpl implements MovieDAO {

    public static final String CSV_FILE_PATH = "movies.csv";

    @Override
    public void addMovie(Movie movie) {
        try (CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(CSV_FILE_PATH, true), CSVFormat.DEFAULT)) {
            csvPrinter.printRecord(movie.getId(), movie.getTitle(), movie.getDirector(), movie.getPriceCode(), movie.getReleaseYear());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeMovie(long id) {
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
                CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withHeader("Title", "Director", "PriceCode", "ReleaseYear"));
                csvPrinter.printRecords(records);
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Movie> findById(long id) {
        try {
            FileReader fileReader = new FileReader(CSV_FILE_PATH);
            CSVParser csvParser = CSVParser.parse(fileReader, CSVFormat.DEFAULT.withHeader().withIgnoreHeaderCase().withTrim());
            List<CSVRecord> records = csvParser.getRecords();
            for (CSVRecord record : records) {
                long recordId = Long.parseLong(record.get("Id"));
                if (recordId == id) {
                    String title = record.get("Title");
                    String director = record.get("Director");
                    String priceCode = record.get("PriceCode");
                    int year = Integer.parseInt(record.get("ReleaseYear"));
                    Movie movie = new Movie();
                    movie.setId(recordId);
                    movie.setTitle(title);
                    movie.setDirector(director);
                    movie.setPriceCode(MovieType.valueOf(priceCode));
                    movie.setReleaseYear(year);
                    return Optional.of(movie);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Movie> findAll() {
        List<Movie> movies = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(CSV_FILE_PATH);
            CSVParser csvParser = CSVParser.parse(fileReader, CSVFormat.DEFAULT.withHeader().withIgnoreHeaderCase().withTrim());
            for (CSVRecord csvRecord : csvParser) {
                long id = Long.parseLong(csvRecord.get("Id"));
                String title = csvRecord.get("Title");
                String director = csvRecord.get("Director");
                String priceCode = csvRecord.get("PriceCode");
                int year = Integer.parseInt(csvRecord.get("ReleaseYear"));
                Movie movie = new Movie();
                movie.setId(id);
                movie.setTitle(title);
                movie.setDirector(director);
                movie.setPriceCode(MovieType.valueOf(priceCode));
                movie.setReleaseYear(year);
                movies.add(movie);
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movies;
    }


    @Override
    public List<Movie> findMoviesByType(String type) {
        List<Movie> result = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(CSV_FILE_PATH);
            CSVParser csvParser = CSVParser.parse(fileReader, CSVFormat.DEFAULT.withHeader().withIgnoreHeaderCase().withTrim());
            for (CSVRecord record : csvParser) {
                String typeField = record.get("PriceCode");
                if (typeField.equalsIgnoreCase(type)) {
                    long id = Long.parseLong(record.get("Id"));
                    String title = record.get("Title");
                    String director = record.get("Director");
                    int year = Integer.parseInt(record.get("ReleaseYear"));
                    Movie movie = new Movie();
                    movie.setId(id);
                    movie.setTitle(title);
                    movie.setDirector(director);
                    movie.setPriceCode(MovieType.valueOf(type));
                    movie.setReleaseYear(year);

                    result.add(movie);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Movie> findMoviesByReleaseYear(int year) {
        List<Movie> result = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(CSV_FILE_PATH);
            CSVParser csvParser = CSVParser.parse(fileReader, CSVFormat.DEFAULT.withHeader().withIgnoreHeaderCase().withTrim());
            for (CSVRecord record : csvParser) {
                int yearField = Integer.parseInt(record.get("ReleaseYear"));
                if (yearField == year) {
                    long id = Long.parseLong(record.get("Id"));
                    String title = record.get("Title");
                    String director = record.get("Director");
                    String priceCode = record.get("PriceCode");
                    Movie movie = new Movie();
                    movie.setId(id);
                    movie.setTitle(title);
                    movie.setDirector(director);
                    movie.setPriceCode(MovieType.valueOf(priceCode));
                    movie.setReleaseYear(year);
                    result.add(movie);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Movie> findMoviesByDirector(String director) {
        List<Movie> result = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(CSV_FILE_PATH);
            CSVParser csvParser = CSVParser.parse(fileReader, CSVFormat.DEFAULT.withHeader().withIgnoreHeaderCase().withTrim());
            for (CSVRecord record : csvParser) {
                String directorField = record.get("Director");
                if (directorField.equalsIgnoreCase(director)) {
                    long id = Long.parseLong(record.get("Id"));
                    String title = record.get("Title");
                    String priceCode = record.get("PriceCode");
                    int year = Integer.parseInt(record.get("ReleaseYear"));
                    Movie movie = new Movie();
                    movie.setId(id);
                    movie.setTitle(title);
                    movie.setDirector(director);
                    movie.setPriceCode(MovieType.valueOf(priceCode));
                    movie.setReleaseYear(year);
                    result.add(movie);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}

