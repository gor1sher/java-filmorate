package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    InMemoryFilmStorage inMemoryFilmStorage = new InMemoryFilmStorage();
    FilmService filmService = new FilmService();

    @GetMapping
    public Collection<Film> findAll() {
        log.info("получение всех фильмов");
        return inMemoryFilmStorage.findAll();
    }

    @GetMapping("/{id}")
    public Film filmByIdentifier(@RequestParam Long id) {
        log.info("получение фильма по идентификатору id: {}", id);

        return inMemoryFilmStorage.filmByIdentifier(id);
    }

    @GetMapping("/popular?count={count}")
    public List<Film> popularFilms(@RequestParam(defaultValue = "10") int count) {
        return filmService.listOfPopularFilms(count);
    }

    @PostMapping
    public Film create(@RequestBody Film newFilm) {
        log.info("регистрация нового фильма");

        Film film = inMemoryFilmStorage.create(newFilm);

        log.info("успешное создание фильма id: {}", film.getId());
        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film newFilm) {
        log.info("обновление данных фильма id: {}", newFilm.getId());

        Film film = inMemoryFilmStorage.update(newFilm);

        log.info("успешное обновление данных о фильме id: {}", film.getId());
        return film;
    }

    @PutMapping("/{id}/like/{userId}")
    public void userLikesTheFilm(@RequestParam Long id, @RequestParam Long userId) {
        filmService.userLikeFilm(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeUserLikeTheFilm(@RequestParam Long id, @RequestParam Long userId) {
        filmService.removeUserLikeFilm(id, userId);
    }
}
