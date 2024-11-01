package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserStorage {

    Collection<User> findAll();

    Optional<User> userById(Long userId);

    User create(User user);

    User update(User user);

    void checkId(Long user);
}
