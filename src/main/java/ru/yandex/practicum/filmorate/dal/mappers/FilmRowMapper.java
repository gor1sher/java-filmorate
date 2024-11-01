package ru.yandex.practicum.filmorate.dal.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;

@Component
public class FilmRowMapper implements RowMapper<Film> {
    @Override
    public Film mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Film film = new Film();

        film.setId(resultSet.getLong("id"));
        film.setName(resultSet.getString("name"));
        film.setDescription(resultSet.getString("description"));
        Timestamp releaseDate = resultSet.getTimestamp("releaseDate");
        film.setReleaseDate(releaseDate.toLocalDateTime().toLocalDate());
        film.setDuration(resultSet.getInt("duration"));
        film.setLikeList(Arrays.stream(resultSet.getString("likeList").split(",")).map(Long::valueOf).toList());
        film.setGenre(Genre.valueOf(resultSet.getString("genre")));
        film.setMpaRating(MpaRating.valueOf(resultSet.getString("mpaRating")));

        return film;
    }
}