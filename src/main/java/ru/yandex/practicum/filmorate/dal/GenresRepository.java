package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.mappers.GenreRowMapper;

import java.util.List;
import java.util.Optional;

@Repository
public class GenresRepository extends BaseRepository {

    public GenresRepository(JdbcTemplate jdbc) {
        super(jdbc, new GenreRowMapper());
    }

    public static final String FIND_ALL = "SELECT name FROM genres";
    public static final String FIND_BY_ID = "SELECT name FROM genres WHERE id = ?";

    public List<String> findAll() {
        return findMany(FIND_ALL);
    }

    public Optional<String> findById(Long filmId) {
        return findOne(FIND_BY_ID, filmId);
    }
}
