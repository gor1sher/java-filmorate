package ru.yandex.practicum.filmorate.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {

    @NonNull
    private FilmStorage inMemoryFilmStorage;

    @NonNull
    private UserStorage inMemoryUserStorage;

    public Collection<Film> findAll() {
        return inMemoryFilmStorage.findAll();
    }

    public Film filmById(Long id) {
        return inMemoryFilmStorage.filmById(id);
    }

    public Film create(Film newFilm) {
        return inMemoryFilmStorage.create(newFilm);
    }

    public Film update(Film newFilm) {
        return inMemoryFilmStorage.update(newFilm);
    }

    public void addLike(Long filmId, Long userId) {
        Film film = inMemoryFilmStorage.filmById(filmId);
        inMemoryUserStorage.checkIdentifier(userId);

        List<Long> likes = film.getLikeList();
        likes.add(userId);
        film.setLikeList(likes);
    }

    public void removeLike(Long filmId, Long userId) {
        Film film = inMemoryFilmStorage.filmById(filmId);
        inMemoryUserStorage.checkIdentifier(userId);

        List<Long> likes = film.getLikeList();
        likes.remove(userId);
        film.setLikeList(likes);
    }

    public int getLength(Film film) {
        return film.getLikeList().size();
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
