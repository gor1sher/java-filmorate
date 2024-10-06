package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
public class User {
    private Long id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
    private ArrayList<User> friends;

    public void addFriend(User user) {
        friends.add(user);
    }

    public void removeFriend(User user) {
        friends.remove(user);
    }
}
