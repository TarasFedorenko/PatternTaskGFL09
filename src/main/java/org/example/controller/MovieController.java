package org.example.controller;

import org.example.entity.Customer;
import org.example.entity.Movie;
import org.example.entity.Rental;
import org.example.enums.MovieType;
import org.example.service.CustomerService;
import org.example.service.CustomerServiceImpl;
import org.example.service.MovieService;
import org.example.service.MovieServiceImpl;
import org.example.utils.InfoUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;

public class MovieController {

    private MovieService movieService = new MovieServiceImpl();
    private CustomerService customerService = new CustomerServiceImpl();

    public void run() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Greetings in movie rental application \n");
        System.out.println("***********MENU************");
        String select;
        showMenu();
        while ((select = bf.readLine()) != null) {
            menu(bf, select);
        }
    }

    private void showMenu() {
        System.out.println();
        System.out.println("Movie catalog - 1");
        System.out.println("Movie search - 2");
        System.out.println("Customers list - 3");
        System.out.println("Quit - 4");
    }

    private void menu(BufferedReader bf, String select) throws IOException {
        switch (select) {
            case "1" -> movieCatalog(bf);
            case "2" -> movieSearch(bf);
            case "3" -> customerList(bf);
            case "4" -> exit();
            default -> System.out.println("Wrong input");
        }
    }

    private void movieSearch(BufferedReader bf) throws IOException {
        while (true) {
            System.out.println();
            System.out.println("Search by movie type - 1");
            System.out.println("Search by director - 2");
            System.out.println("Search by release year - 3");
            System.out.println("Back to main menu - 4");

            String choice = bf.readLine();
            switch (choice) {

                case "1" -> searchByMovieType(bf);
                case "2" -> searchByDirector(bf);
                case "3" -> searchByReleaseYear(bf);
                case "4" -> {
                    showMenu();
                    return;
                }
                default -> System.out.println("Wrong input");
            }
        }
    }

    private void searchByReleaseYear(BufferedReader bf) throws IOException {
        System.out.println("Search some movie by release year");
        System.out.print("Please enter year: ");
        int year = Integer.parseInt(bf.readLine());
        List<Movie> movieList = movieService.findMoviesByReleaseYear(year);
        for (Movie movie : movieList) {
            System.out.println(movie);
        }
    }

    private void searchByDirector(BufferedReader bf) throws IOException {
        System.out.println("Search some movie by director");
        System.out.print("Please enter director name: ");
        String director = bf.readLine();
        List<Movie> movieList = movieService.findMoviesByDirector(director);
        for (Movie movie : movieList) {
            System.out.println(movie);
        }
    }

    private void searchByMovieType(BufferedReader bf) throws IOException {
        System.out.println("Search some movie by type");
        System.out.print("Please enter movie type: ");
        String type = bf.readLine();
        List<Movie> movieList = movieService.findMoviesByType(type);
        for (Movie movie : movieList) {
            System.out.println(movie);
        }
    }

    private void customerList(BufferedReader bf) throws IOException {
        while (true) {
            System.out.println();
            System.out.println("List of all customer - 1");
            System.out.println("Create record of new customer - 2");
            System.out.println("Delete record of customer - 3");
            System.out.println("Information of chosen customer - 4");
            System.out.println("Add rental to chosen customer - 5");
            System.out.println("Remove rental of chosen customer - 6");
            System.out.println("Find rental by id - 7");
            System.out.println("Back to main menu - 8");

            String choice = bf.readLine();
            switch (choice) {
                case "1" -> findAllCustomer();
                case "2" -> createCustomer(bf);
                case "3" -> deleteCustomer(bf);
                case "4" -> findCustomer(bf);
                case "5" -> addRental(bf);
                case "6" -> deleteRental(bf);
                case "7" -> findRental(bf);
                case "8" -> {
                    showMenu();
                    return;

                }
                default -> System.out.println("Wrong input");
            }
        }
    }

    private void findRental(BufferedReader bf) throws IOException {
        System.out.println("You want to find rental");
        System.out.println("Please enter rental ID");
        long rentalID = Long.parseLong(bf.readLine());
        Rental rental = customerService.findRental(rentalID);
        if (rental == null) {
            System.out.println("Wrong ID");
        } else {
            System.out.println(rental);
        }
    }

    private void deleteRental(BufferedReader bf) throws IOException {
        System.out.println("You want to remove rental movie from customer");
        System.out.println("Please enter id of rental");
        long rentalID = Long.parseLong(bf.readLine());
        customerService.removeRental(rentalID);
    }

    private void addRental(BufferedReader bf) throws IOException {
        System.out.println("You want to add rental movie to customer");
        System.out.println("Please enter id of customer");
        long customerID = Long.parseLong(bf.readLine());
        System.out.println("Please enter id of movie");
        long movieId = Long.parseLong(bf.readLine());
        System.out.println("Please enter how many days movie in rent");
        int daysRented = Integer.parseInt(bf.readLine());
        Customer customer = customerService.findById(customerID);
        Rental rental = new Rental();
        rental.setId(generateRandomId());
        rental.setMovie(movieService.findById(movieId));
        rental.setDaysRented(daysRented);
        customerService.addRental(customer, rental);
    }


    private void findCustomer(BufferedReader bf) throws IOException {
        System.out.println("You want to find customer");
        System.out.println("Please enter customer ID");
        long customerID = Long.parseLong(bf.readLine());
        Customer customer = customerService.findById(customerID);
        if (customer == null) {
            System.out.println("Wrong ID");
        } else {
            System.out.println(customer);
            System.out.println(InfoUtil.showInfo(customer));
        }
    }


    private void deleteCustomer(BufferedReader bf) throws IOException {
        System.out.println("You want to delete customers profile");
        System.out.println("Please enter movie ID");
        long customerID = Long.parseLong(bf.readLine());
        customerService.removeCustomer(customerID);
    }

    private void createCustomer(BufferedReader bf) throws IOException {
        System.out.println("You want to create profile of new customer");
        System.out.println("Please enter customer name");
        String name = bf.readLine();
        int rentalPoints = 0;

        Customer customer = new Customer();
        customer.setId(generateRandomId());
        customer.setName(name);
        customer.setRentalPoints(rentalPoints);

        customerService.addCustomer(customer);
    }

    private void findAllCustomer() {
        System.out.println("You want to find all customers");
        List<Customer> customers = customerService.findAll();
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    private void movieCatalog(BufferedReader bf) throws IOException {
        while (true) {
            System.out.println();
            System.out.println("List of all movies - 1");
            System.out.println("Create record of new movie - 2");
            System.out.println("Delete record of movie - 3");
            System.out.println("Information of chosen movie - 4");
            System.out.println("Back to main menu - 5");

            String choice = bf.readLine();
            switch (choice) {
                case "1" -> findAllMovies();
                case "2" -> createMovie(bf);
                case "3" -> deleteMovie(bf);
                case "4" -> findMovieById(bf);
                case "5" -> {
                    showMenu();
                    return;
                }
                default -> System.out.println("Wrong input");
            }
        }
    }

    private void findMovieById(BufferedReader bf) throws IOException {
        System.out.println("You want to find movie");
        System.out.println("Please enter movie ID");
        long movieID = Long.parseLong(bf.readLine());
        Movie movie = movieService.findById(movieID);
        if (movie == null) {
            System.out.println("Wrong ID");
        } else {
            System.out.println(movie);
        }

    }

    private void deleteMovie(BufferedReader bf) throws IOException {
        System.out.println("You want to delete movies profile");
        System.out.println("Please enter movie ID");
        long moviesID = Long.parseLong(bf.readLine());
        movieService.removeMovie(moviesID);
    }

    private void createMovie(BufferedReader bf) throws IOException {
        System.out.println("You want to create profile of new movie");
        System.out.println("Please enter movies title");
        String title = bf.readLine();
        System.out.println("Please enter movies director");
        String director = bf.readLine();
        System.out.println("Please enter movies type");
        String movieType = bf.readLine();
        System.out.println("Please enter movies release year");
        Integer releaseYear = Integer.parseInt(bf.readLine());

        Movie movie = new Movie();
        movie.setId(generateRandomId());
        movie.setTitle(title);
        movie.setDirector(director);
        movie.setPriceCode(MovieType.valueOf(movieType));
        movie.setReleaseYear(releaseYear);

        movieService.addMovie(movie);
    }

    private static int generateRandomId() {
        Random random = new Random();
        return random.nextInt(10000);
    }

    private void findAllMovies() {
        System.out.println("You want to find all movies");
        List<Movie> movies = movieService.findAll();
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }

    private void exit() {
        System.out.println("Good bye");
        System.exit(0);
    }
}