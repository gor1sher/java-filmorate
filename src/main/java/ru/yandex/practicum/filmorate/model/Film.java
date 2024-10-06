package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
public class Film {
    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private ArrayList<User> usersLikesFilm;

    public void addLikeTheFilm(User user) {
        this.usersLikesFilm.add(user);
    }

    public void removeLikeTheFilm(User user) {
        usersLikesFilm.remove(user);
    }

    public int countLikes() {
        return usersLikesFilm.size();
    }
}
