package ru.yandex.practicum.filmorate.model;

import lombok.Getter;

@Getter
public enum MpaRating {
    G(1),
    PG(2),
    PG_13(3),
    R(4),
    NC_17(5);

    private final int id;

    MpaRating(int id) {
        this.id = id;
    }

    public static MpaRating getById(int id) {
        for(MpaRating e : values()) {
            if(e.id == id) return e;
        }
        return null;
    }
}
