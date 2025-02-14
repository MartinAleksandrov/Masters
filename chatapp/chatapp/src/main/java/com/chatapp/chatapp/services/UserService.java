package com.chatapp.chatapp.services;

import com.chatapp.chatapp.Interfaces.IUserService;
import com.chatapp.chatapp.models.Channel;
import com.chatapp.chatapp.models.User;
import com.chatapp.chatapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    // Конструктор с инжектиране на UserRepository
    public UserService(UserRepository userRepo) {
        this.userRepository = userRepo;
    }

    @Override
    public User registerUser(String username, String password) {
        // Създаваме нов потребител с обикновен конструктор
        User user = new User(username, password, true);

        // Запазваме в базата
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        Optional<User> currentUser = userRepository.findById(userId);
        Optional<User> currentFriend = userRepository.findById(friendId);

        if (currentUser.isPresent() && currentFriend.isPresent()) {
            User user = currentUser.get();
            User friend = currentFriend.get();

            // Добавяме двупосочно приятелство
            user.addFriend(friend);
            friend.addFriend(user);

            // Запазваме в базата
            userRepository.save(user);
            userRepository.save(friend);
        }
    }

    public List<User> searchUsers(String query) {
        return userRepository.findByUsernameContainingIgnoreCase(query);
    }

    public void removeFriend(Long userId, Long friendId) {
        Optional<User> currentUser = userRepository.findById(userId);
        Optional<User> currentFriend = userRepository.findById(friendId);

        if (currentUser.isPresent() && currentFriend.isPresent()) {
            User user = currentUser.get();
            User friend = currentFriend.get();

            user.getFriends().remove(friend);
            friend.getFriends().remove(user);

            userRepository.save(user);
            userRepository.save(friend);
        }
    }

    public void softDeleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(u -> {
            u.setActive(false);
            userRepository.save(u);
        });
    }

    public User updateUserPassword(Long id, String newPassword) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User u = user.get();
            u.setPassword(newPassword);
            return userRepository.save(u);
        }
        return null;
    }

    public List<User> getUserFriends(Long userId) {
        return userRepository.findFriendsByUserId(userId);
    }

    public List<Channel> getUserChannels(Long userId) {
        return userRepository.findChannelsByUserId(userId);
    }
}
