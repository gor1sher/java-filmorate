package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private final LocalDate beginning = LocalDate.of(1895, 12, 28);
    private final Map<Long, Film> films = new HashMap<>();

    public Collection<Film> findAll() {
        return films.values();
    }

    public Film filmByIdentifier(Long filmId) {
        if (films.containsKey(filmId)) {
            return films.get(filmId);
        } else {
            throw new NotFoundException("Указан неверный идентификатор");
        }
    }

    @Override
    public Film create(Film film) {
        validateCreate(film);

        film.setId(getNextId());
        films.put(film.getId(), film);

        return film;
    }

    @Override
    public Film update(Film newFilm) {

        validateUpdate(newFilm);
        Film oldFilm = changingOldDataToNewOnes(newFilm);

        return oldFilm;
    }

    @Override
    public void remove(Film film) {

    }

    public List<Film> filmList() {
        return (List<Film>) films.values();
    }

    private void validateCreate(Film film) {
        checkTitle(film);
        checkingObjectCriteria(film);
    }

    private void validateUpdate(Film film) {
        if (!films.containsKey(film.getId())) {
            log.error("ошибка: фильм с id = {} не найден", film.getId());
            throw new NotFoundException("Фильм с id = " + film.getId() + " не найден");
        }

        if (film.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        }

        checkTitle(film);
        checkingObjectCriteria(film);
    }

    private void checkingObjectCriteria(Film film) {
        if ((film.getDescription().length() < 200)
                && film.getReleaseDate().isAfter(beginning) && (film.getDuration() > 0)) {

            log.error("ошибка: условия регистрации фильма не выполнены id: {}", film.getId());
            throw new ConditionsNotMetException("Не выполнены условия для регистрации фильма в приложении");
        }
    }

    private void checkTitle(Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            log.error("ошибка: название фильма не указано");
            throw new ConditionsNotMetException("Название фильма не может быть пустым");
        }
    }

    private Film changingOldDataToNewOnes(Film newFilm) {
        Film oldFilm = films.get(newFilm.getId());
        oldFilm.setDescription(newFilm.getDescription());
        oldFilm.setDuration(newFilm.getDuration());
        oldFilm.setName(newFilm.getName());
        oldFilm.setReleaseDate(newFilm.getReleaseDate());

        return oldFilm;
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
