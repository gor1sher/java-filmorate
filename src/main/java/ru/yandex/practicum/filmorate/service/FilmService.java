package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.FilmRepository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilmService {

    @Autowired
    private FilmStorage inMemoryFilmStorage;

    @Autowired
    private UserStorage inMemoryUserStorage;

    @Autowired
    private FilmRepository filmRepository;

    public Collection<Film> findAll() {
        return filmRepository.findAll();
    }

    public Optional<Film> filmById(Long id) {
        return inMemoryFilmStorage.filmById(id);
    }

    public Film create(Film newFilm) {
        return inMemoryFilmStorage.create(newFilm);
    }

    public Film update(Film newFilm) {
        return inMemoryFilmStorage.update(newFilm);
    }

    public void addLike(Long filmId, Long userId) {
        Optional<Film> film = inMemoryFilmStorage.filmById(filmId);
        inMemoryUserStorage.checkId(userId);

        List<Long> likes = film.get().getLikeList();
        likes.add(userId);
        film.get().setLikeList(likes);
        filmRepository.update(film.get());
    }

    public void removeLike(Long filmId, Long userId) {
        Optional<Film> film = inMemoryFilmStorage.filmById(filmId);
        inMemoryUserStorage.checkId(userId);

        List<Long> likes = film.get().getLikeList();
        likes.remove(userId);
        film.get().setLikeList(likes);
        filmRepository.update(film.get());
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
