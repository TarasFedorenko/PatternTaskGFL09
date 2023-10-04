package org.example;

import org.example.controller.MovieController;
import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {
        MovieController controller = new MovieController();
        controller.run();
    }
}