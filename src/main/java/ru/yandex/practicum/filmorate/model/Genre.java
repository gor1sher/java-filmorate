package ru.yandex.practicum.filmorate.model;

import lombok.Getter;

@Getter
public enum Genre {
    COMEDY(1),
    DRAMA(2),
    ANIMATION(3),
    THRILLER(4),
    DOCUMENTARY(5),
    ACTION(6);

    private int id;

    Genre(int id) {
        this.id = id;
    }

    public static Genre getById(int id) {
        for (Genre e : values()) {
            if (e.id == id) return e;
        }
        return null;
    }
}
