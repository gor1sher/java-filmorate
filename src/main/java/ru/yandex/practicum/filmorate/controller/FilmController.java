package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private final LocalDate beginning = LocalDate.of(1895, 12, 28);
    private final Map<Long, Film> films = new HashMap<>();

    @GetMapping
    public Collection<Film> findAll() {
        log.info("получение всех фильмов");
        return films.values();
    }

    @PostMapping
    public Film create(@RequestBody Film film) {
        log.info("регистрация нового фильма id: {}", film.getId());
        if (film.getName() == null || film.getName().isBlank()) {
            log.error("ошибка: название фильма не указано");
            throw new ConditionsNotMetException("Название фильма не может быть пустым");
        }

        if ((film.getDescription().length() < 200) && (film.getReleaseDate().isAfter(beginning))
                && (film.getDuration() > 0)) {

            film.setId(getNextId());

            films.put(film.getId(), film);
            log.info("успешное создание фильма id: {}", film.getId());
            return film;
        }
        log.error("ошибка: условия регистрации фильма не выполнены id: {}", film.getId());
        throw new ConditionsNotMetException("Не выполнены условия для регистрации фильма в приложении");
    }

    @PutMapping
    public Film update(@RequestBody Film newFilm) {
        log.info("обновление данных фильма id: {}", newFilm.getId());
        if (newFilm.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        }

        if (newFilm.getName() == null || newFilm.getName().isBlank()) {
            log.error("ошибка: название не может быть пустым id: {}", newFilm.getId());
            throw new ConditionsNotMetException("Название не может быть пустым");
        }

        if (films.containsKey(newFilm.getId())) {
            if ((newFilm.getDescription().length() < 200)
                    && newFilm.getReleaseDate().isAfter(beginning) && (newFilm.getDuration() > 0)) {

                Film oldFilm = films.get(newFilm.getId());
                oldFilm.setDescription(newFilm.getDescription());
                oldFilm.setDuration(newFilm.getDuration());
                oldFilm.setName(newFilm.getName());
                oldFilm.setReleaseDate(newFilm.getReleaseDate());

                log.info("успешное обновление данных о фильме id: {}", oldFilm.getId());
                return oldFilm;
            }
        }
        log.error("ошибка: фильм с id = {} не найден", newFilm.getId());
        throw new NotFoundException("Фильм с id = " + newFilm.getId() + " не найден");
    }

    private long getNextId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        long nextId = ++currentMaxId;
        log.info("создание нового id: {}", nextId);
        return nextId;
    }
}
