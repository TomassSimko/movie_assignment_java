/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.dal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;
import movierecsys.be.Movie;

/**
 *
 * @author pgn
 */
public class FileReaderTester
{

    /**
     * Example method. This is the code I used to create the users.txt files.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException
    {
        // FETCHING ALL MOVIES
        IMovieDAO movieDao = new MovieDAO();
        List<Movie> fetchedMovies = movieDao.getAllMovies();
        for (Movie allMov : fetchedMovies)
        {
            System.out.println(allMov.getTitle());
        }
        System.out.println("Movie count: " + fetchedMovies.size());

        // CREATING MOVIES
        Movie createdMovie = movieDao.createMovie(3400,"Picovinka 2 ");
        System.out.println(createdMovie.getTitle() + " " + createdMovie.getYear());
    }

   
}
