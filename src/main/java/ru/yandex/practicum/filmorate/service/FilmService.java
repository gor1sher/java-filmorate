package ru.yandex.practicum.filmorate.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {

    @NonNull
    private InMemoryFilmStorage inMemoryFilmStorage;

    @NonNull
    private InMemoryUserStorage inMemoryUserStorage;

    public Collection<Film> findAll() {
        return inMemoryFilmStorage.findAll();
    }

    public Film filmById(Long id) {
        return inMemoryFilmStorage.filmById(id);
    }

    public Film create(Film newFilm) {
        newFilm.setListLikes(new ArrayList<>());
        return inMemoryFilmStorage.create(newFilm);
    }

    public Film update(Film newFilm) {
        return inMemoryFilmStorage.update(newFilm);
    }

    public void addLike(Long filmId, Long userId) {
        Film film = inMemoryFilmStorage.filmById(filmId);
        inMemoryUserStorage.checkIdentifier(userId);

        List<Long> likes = (List<Long>) film.getListLikes();
        likes.add(userId);
        film.setListLikes((ArrayList<Long>) likes);
    }

    public void removeLike(Long filmId, Long userId) {
        Film film = inMemoryFilmStorage.filmById(filmId);
        inMemoryUserStorage.checkIdentifier(userId);

        List<Long> likes = (List<Long>) film.getListLikes();
        likes.remove(userId);
        film.setListLikes((ArrayList<Long>) likes);
    }

    public int getLength(Film film) {
        return film.getListLikes().size();
    }

    public void checkingFilmInStorage(Film film) {
        inMemoryFilmStorage.checkingFilmInStorage(film);
    }

    public List<Film> getListOfPopularFilms(int count) {
        return inMemoryFilmStorage.getListFilms().stream()
                .sorted(Comparator.comparingInt(film -> getLength((Film) film)).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }
}
