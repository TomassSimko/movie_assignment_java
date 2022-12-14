/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.dal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import movierecsys.be.Movie;
import movierecsys.bll.OwsLogicFacade;

import static java.nio.file.StandardOpenOption.APPEND;

/**
 * @author pgn
 */
public class MovieDAO implements IMovieDAO {

    private static final String MOVIE_SOURCE = "data/movie_titles.txt";

    /**
     * Gets a list of all movies in the persistence storage.
     *
     * @return List of movies.
     */
    public List<Movie> getAllMovies() {
        List<Movie> allMovies = new ArrayList<>();
        File file = new File(MOVIE_SOURCE);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {

                    Movie mov = stringToMovieObject(line);
                    allMovies.add(mov);
                } catch (Exception ex) {
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return allMovies;
    }

    /**
     * Reads a movie from a , s
     *
     * @param t
     * @return
     * @throws NumberFormatException
     */
    private Movie stringToMovieObject(String t) {
        String[] arrMovie = t.split(",");

        int id = Integer.parseInt(arrMovie[0]);
        int year = Integer.parseInt(arrMovie[1]);
        String title = arrMovie[2];
        if (arrMovie.length > 3) {
            for (int i = 3; i < arrMovie.length; i++) {
                title += "," + arrMovie[i];
            }
        }
        return new Movie(id, year, title);
    }

    /**
     * @return highestId + 1 into the file ;
     */
    private int getNextId() {
        List<Movie> movies = getAllMovies();
        int highestId = 0;
        for (Movie movie : movies) {
            if (highestId < movie.getId())
                highestId = movie.getId();
        }
        return highestId + 1;
    }


    /**
     * Creates a movie in the persistence storage.
     *
     * @param releaseYear The release year of the movie
     * @param title       The title of the movie
     * @return The object representation of the movie added to the persistence
     * storage.
     */
    public Movie createMovie(int releaseYear, String title) {
        int id = getNextId();
        try {
            Files.writeString(
                    Path.of(MOVIE_SOURCE),
                    id + "," + releaseYear + "," + title + "\n",
                    APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new Movie(id, releaseYear, title);
    }

    /**
     * Deletes a movie from the persistence storage.
     *
     * @param movie The movie to delete.
     */
    public void deleteMovie(Movie movie) {
        try {
            List<Movie> movies = getAllMovies();

            String movieString = "";
            for (Movie m : movies) {
                if (m.getId() != movie.getId()) {
                    movieString += m.getId() + "," +
                            m.getYear() + "," +
                            m.getTitle() + "\n";
                }
            }
            Files.writeString(Path.of(MOVIE_SOURCE), movieString);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the movie in the persistence storage to reflect the values in the
     * given Movie object.
     *
     * @param movie The updated movie.
     */
    public void updateMovie(Movie movie) {
        try {
            List<Movie> movies = getAllMovies();

            String movieString = "";
            for (Movie m : movies) {
                if (m.getId() == movie.getId()) {
                    m = movie;
                }
                movieString += m.getId() + "," +
                        m.getYear() + "," +
                        m.getTitle() + "\n";
            }

            Files.writeString(Path.of(MOVIE_SOURCE), movieString);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets a the movie with the given ID.
     *
     * @param id ID of the movie.
     * @return A Movie object.
     */
    public Movie getMovie(int id) {
        try {
            List<String> lines = Files.readAllLines(Path.of(MOVIE_SOURCE));
            for (String line : lines) {
                if (line != null) {
                    Movie currentMovie = stringToMovieObject(line);
                    if (currentMovie.getId() == id) {
                        return currentMovie;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
