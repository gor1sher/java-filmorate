package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();

    public void addFriend(Long userId, Long friendId) {
        List<User> userList = checkAndGetUserById(userId, friendId);
        userList.get(0).addFriend(userList.get(1));
    }

    public void removeFriend(Long userId, Long friendId) {
        List<User> userList = checkAndGetUserById(userId, friendId);
        userList.get(0).removeFriend(userList.get(1));
    }

    public List<User> listОfAllUserFriends(Long id) {
        User user = inMemoryUserStorage.userByIdentifier(id);
        return user.getFriends();
    }

    public List<User> commonOfFriends(Long userId, Long otherId) {
        List<User> userList = checkAndGetUserById(userId, otherId);
        return userList.get(0).getFriends().stream()
                .filter(friend -> userList.get(1).getFriends().stream()
                        .anyMatch(otherFriend -> otherFriend.equals(friend)))
                .collect(Collectors.toList());
    }

    //проверка на то, что User обьект может быть пустым - есть, в классе InMemoryUserStorage
    private List<User> checkAndGetUserById(Long id, Long friendId) {
        User user = inMemoryUserStorage.userByIdentifier(id);
        User userFriend = inMemoryUserStorage.userByIdentifier(friendId);

        return List.of(user, userFriend);
    }
}
