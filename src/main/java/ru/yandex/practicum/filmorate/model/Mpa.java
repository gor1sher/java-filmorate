package ru.yandex.practicum.filmorate.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Mpa {
    private int id;
    private String mpaRating;

    public Mpa(int id) {
        this.id = id;
    }
}
