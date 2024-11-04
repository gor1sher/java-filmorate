package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.mappers.MpaRowMapper;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.Optional;

@Repository
public class MpaRepository extends BaseRepository<Mpa> {

    public MpaRepository(JdbcTemplate jdbc) {
        super(jdbc, new MpaRowMapper());
    }

    public static final String FIND_ALL = "SELECT name FROM mpa_rating";
    public static final String FIND_BY_ID = "SELECT name FROM mpa_rating WHERE id = ?";

    public List<Mpa> findAll() {
        return findMany(FIND_ALL);
    }

    public Optional<Mpa> findById(Long filmId) {
        return findOne(FIND_BY_ID, filmId);
    }
}
