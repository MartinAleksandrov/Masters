package com.chatapp.chatapp.Interfaces;

import com.chatapp.chatapp.models.User;
import java.util.List;
import java.util.Optional;

public interface IUserService {

    User registerUser(String username, String password);
    Optional<User> findUserById(Long id);
    Optional<User> findUserByUsername(String username);
    List<User> getAllUsers();
    void addFriend(Long userId, Long friendId);
}
