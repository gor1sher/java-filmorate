package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FilmService {

    InMemoryFilmStorage inMemoryFilmStorage = new InMemoryFilmStorage();
    InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();

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
