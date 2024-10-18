package ru.yandex.practicum.filmorate.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    @NonNull
    private InMemoryUserStorage inMemoryUserStorage;

    public Collection<User> findAll() {
        return inMemoryUserStorage.findAll();
    }

    public User userByIdentifier(Long id) {
        return inMemoryUserStorage.userByIdentifier(id);
    }

    public User create(User newUser) {
        return inMemoryUserStorage.create(newUser);
    }

    public User update(User newUser) {
        return inMemoryUserStorage.update(newUser);
    }

    public void checkingUserInStorage(User user) {
        inMemoryUserStorage.checkingUserInStorage(user);
    }

    public void addFriend(Long userId, Long friendId) {
        List<User> userList = checkAndGetUserById(userId, friendId);

        List<Long> userFriends = userList.get(0).getListFriends();
        userFriends.add(userList.get(1).getId());
        userList.get(0).setListFriends(userFriends);

        List<Long> userFriends1 = userList.get(1).getListFriends();
        userFriends1.add(userList.get(0).getId());
        userList.get(1).setListFriends(userFriends1);
    }

    public void removeFriend(Long userId, Long friendId) {
        List<User> userList = checkAndGetUserById(userId, friendId);

        List<Long> userFriends = userList.get(0).getListFriends();
        userFriends.remove(userList.get(1).getId());
        userList.get(0).setListFriends(userFriends);

        List<Long> userFriends1 = userList.get(1).getListFriends();
        userFriends1.remove(userList.get(0).getId());
        userList.get(1).setListFriends(userFriends1);
    }

    public List<User> getUserFriend(Long id) {
        User user = inMemoryUserStorage.userByIdentifier(id);
        return user.getListFriends().stream().map(friendId -> inMemoryUserStorage.userByIdentifier(friendId)).toList();
    }

    public List<User> commonOfFriends(Long userId, Long otherId) {
        List<User> userList = checkAndGetUserById(userId, otherId);
        return userList.get(0).getListFriends().stream().map(long1 -> inMemoryUserStorage.userByIdentifier(long1)).toList().stream()
                .filter(friend -> userList.get(1).getListFriends().stream().map(long1 -> inMemoryUserStorage.userByIdentifier(long1)).toList().stream()
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
