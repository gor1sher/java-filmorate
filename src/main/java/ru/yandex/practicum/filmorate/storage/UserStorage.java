package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {

    Collection<User> findAll();

    User userByIdentifier(Long userId);

    User create(User user);

    User update(User user);

    void checkIdentifier(Long userId);

    void checkingUserInStorage(User user);
}
