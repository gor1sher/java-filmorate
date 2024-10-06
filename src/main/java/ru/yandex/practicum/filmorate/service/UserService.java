package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    InMemoryUserStorage inMemoryUserStorage;

    public void addFriend(Long userId, Long friendId) {
        List<User> userList = checkAndGetUserById(userId, friendId);
        userList.get(0).addFriend(userList.get(1));
        userList.get(1).addFriend(userList.get(0));
    }

    public void removeFriend(Long userId, Long friendId) {
        List<User> userList = checkAndGetUserById(userId, friendId);
        userList.get(0).removeFriend(userList.get(1));
        userList.get(1).removeFriend(userList.get(0));
    }

    public List<User> listОfAllUserFriends(Long id) {
        User user = inMemoryUserStorage.userByIdentifier(id);
        return user.getFriends().stream().map(friendId -> inMemoryUserStorage.userByIdentifier(friendId)).toList();
    }

    public List<User> commonOfFriends(Long userId, Long otherId) {
        List<User> userList = checkAndGetUserById(userId, otherId);
        return userList.get(0).getFriends().stream().map(long1 -> inMemoryUserStorage.userByIdentifier(long1)).toList().stream()
                .filter(friend -> userList.get(1).getFriends().stream().map(long1 -> inMemoryUserStorage.userByIdentifier(long1)).toList().stream()
                        .anyMatch(otherFriend -> otherFriend.equals(friend)))
                .collect(Collectors.toList());
    }

    //проверка на то, что User обьект может быть пустым - есть, в классе InMemoryUserStorage
    private List<User> checkAndGetUserById(Long id, Long friendId) {
        User user = inMemoryUserStorage.userByIdentifier(id);
        User userFriend = inMemoryUserStorage.userByIdentifier(friendId);

        return Arrays.asList(user, userFriend);
    }
}
