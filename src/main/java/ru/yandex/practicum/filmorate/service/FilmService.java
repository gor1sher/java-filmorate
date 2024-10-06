package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    InMemoryFilmStorage inMemoryFilmStorage;
    InMemoryUserStorage inMemoryUserStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage inMemoryFilmStorage, InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public void userLikeFilm(Long filmId, Long userId) {
        Film film = inMemoryFilmStorage.filmByIdentifier(filmId);
        User user = inMemoryUserStorage.userByIdentifier(userId);

        film.addLikeTheFilm(user);
    }

    public void removeUserLikeFilm(Long filmId, Long userId) {
        Film film = inMemoryFilmStorage.filmByIdentifier(filmId);
        User user = inMemoryUserStorage.userByIdentifier(userId);

        film.removeLikeTheFilm(user);
    }

    public List<Film> listOfPopularFilms(int count) {
        return inMemoryFilmStorage.filmList().stream()
                .sorted(Comparator.comparingInt(Film::countLikes).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }
}
