package ru.yandex.practicum.filmorate.dal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.mappers.FilmRowMapper;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class FilmRepository extends BaseRepository<Film> {

    private static final String FIND_ALL_QUERY = "SELECT * FROM films";
    private static final String FIND_BY_EMAIL_QUERY = "SELECT * FROM films WHERE email = ?";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM films f WHERE id = ? JOIN ";
    private static final String INSERT_QUERY = "INSERT INTO films(name, description, releaseDate, duration, likeList, genre, mpaRating)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE films SET name = ?, description = ?, releaseDate = ?, duration = ?, likeList = ?, genre = ?, mpaRating = ? WHERE id = ?";
    private static final String DELETE_ROW = "DELETE FROM films WHERE id = ?";

    @Autowired
    public FilmRepository(JdbcTemplate jdbc) {
        super(jdbc, new FilmRowMapper());
    }

    public List<Film> findAll() {
        return findMany(FIND_ALL_QUERY);
    }

    public Boolean deleteRow(Long id) {
        return delete(DELETE_ROW, id);
    }

    public Optional<Film> findByEmail(String email) {
        return findOne(FIND_BY_EMAIL_QUERY, email);
    }

    public Optional<Film> findById(Long filmId) {
        return findOne(FIND_BY_ID_QUERY, filmId);
    }

    public Film save(Film film) {
        long id = insert(
                INSERT_QUERY,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getLikeList().stream().map(String::valueOf).collect(Collectors.joining(",")),
                film.getGenres().stream().map(String::valueOf).collect(Collectors.joining(",")),
                film.getMpa().getId()
        );
        film.setId(id);
        return film;
    }

    public Film update(Film film) {
        update(
                UPDATE_QUERY,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getLikeList().stream().map(String::valueOf).collect(Collectors.joining(",")),
                film.getGenres().stream().map(String::valueOf).collect(Collectors.joining(",")),
                film.getMpa().getId(),
                film.getId()
                );
        return film;
    }

    public static class MpaRepository {
    }
}
