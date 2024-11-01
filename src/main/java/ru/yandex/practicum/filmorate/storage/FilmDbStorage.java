package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.FilmRepository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class FilmDbStorage implements FilmStorage {

    @Autowired
    private FilmRepository filmRepository;

    @Override
    public Collection<Film> findAll() {
        return List.of();
    }

    public Optional<Film> filmById(Long filmId) {
        if (filmRepository.findById(filmId).isPresent()) {
            return filmRepository.findById(filmId);
        } else {
            throw new NotFoundException("Указан неверный идентификатор");
        }
    }

    @Override
    public Film create(Film film) {
        film.setId(getNextId());

        return filmRepository.save(film);
    }

    @Override
    public Film update(Film newFilm) {
        return replaceOldData(newFilm);
    }

    @Override
    public List<Film> getListFilms() {
        return List.of();
    }

    @Override
    public void checkingFilmInStorage(Film film) {
        filmById(film.getId());
    }

    private Film replaceOldData(Film newFilm) {
        Optional<Film> oldFilmOptional = filmById(newFilm.getId());
        if (oldFilmOptional.isPresent()) {
            Film oldFilm = oldFilmOptional.get();
            oldFilm.setDescription(newFilm.getDescription());
            oldFilm.setDuration(newFilm.getDuration());
            oldFilm.setName(newFilm.getName());
            oldFilm.setReleaseDate(newFilm.getReleaseDate());
            return filmRepository.update(oldFilm);
        } else {
            throw new RuntimeException("Фильм не найден: " + newFilm.getId());
        }
    }

    private long getNextId() {
        long currentMaxId = filmRepository.findAll()
                .stream()
                .mapToLong(Film::getId)
                .max()
                .orElse(0);
        long nextId = ++currentMaxId;
        log.info("создание нового id: {}", nextId);
        return nextId;
    }
}

