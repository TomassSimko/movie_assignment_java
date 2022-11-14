package movierecsys.dal;

import movierecsys.be.Movie;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface IMovieDAO {
    List<Movie> getAllMovies() throws FileNotFoundException, IOException;
}
