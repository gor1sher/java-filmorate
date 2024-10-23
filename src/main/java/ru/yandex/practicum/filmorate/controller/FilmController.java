package ru.yandex.practicum.filmorate.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
@RequiredArgsConstructor
public class FilmController {

    private final LocalDate beginning = LocalDate.of(1895, 12, 28);

    @Autowired
    protected FilmService filmService;

    @GetMapping
    public Collection<Film> findAll() {
        log.info("получение всех фильмов");
        return filmService.findAll();
    }

    @GetMapping("/{id}")
    public Film filmById(@PathVariable(name = "id") Long id) {
        log.info("получение фильма по идентификатору id: {}", id);

        return filmService.filmById(id);
    }

    @GetMapping("/popular")
    public List<Film> popularFilms(@RequestParam(defaultValue = "10", name = "count") int count) {
        return filmService.getListOfPopularFilms(count);
    }

    @PostMapping
    public Film create(@RequestBody Film newFilm) {
        log.info("регистрация нового фильма");

        validateFilmCreation(newFilm);
        Film film = filmService.create(newFilm);

        log.info("успешное создание фильма id: {}", film.getId());
        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film newFilm) {
        log.info("обновление данных фильма id: {}", newFilm.getId());

        validateUpdate(newFilm);
        Film film = filmService.update(newFilm);

        log.info("успешное обновление данных о фильме id: {}", film.getId());
        return film;
    }

    @PutMapping("/{id}/like/{userId}")
    public void userLikesTheFilm(@PathVariable(name = "id") Long id, @PathVariable(name = "userId") Long userId) {
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeUserLikeTheFilm(@PathVariable(name = "id") Long id, @PathVariable(name = "userId") Long userId) {
        filmService.removeLike(id, userId);
    }

    private void validateUpdate(Film film) {
        filmService.checkingFilmInStorage(film);

        if (film.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        }

        checkTitle(film);
        validateObjectCriteria(film);
    }

    private void checkTitle(Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            log.error("ошибка: название фильма не указано");
            throw new ConditionsNotMetException("Название фильма не может быть пустым");
        }
    }

    private void validateObjectCriteria(Film film) {
        if ((film.getDescription().length() > 200)
                || film.getReleaseDate().isBefore(beginning) || (film.getDuration() < 0)) {

            log.error("ошибка: условия регистрации фильма не выполнены id: {}", film.getId());
            throw new ConditionsNotMetException("Не выполнены условия для регистрации фильма в приложении");
        }
    }

    private void validateFilmCreation(Film film) {
        checkTitle(film);
        validateObjectCriteria(film);
    }
}
