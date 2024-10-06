package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

public interface FilmStorage {
    public Film create(Film film);
    public Film update(Film film);
    public void remove(Film film);
}
