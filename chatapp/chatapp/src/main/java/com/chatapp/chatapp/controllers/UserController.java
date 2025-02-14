package com.chatapp.chatapp.controllers;

import com.chatapp.chatapp.models.Channel;
import com.chatapp.chatapp.models.User;
import com.chatapp.chatapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestParam String username, @RequestParam String password) {
        User user = userService.registerUser(username, password);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.findUserByUsername(username);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/{userId}/add-friend/{friendId}")
    public ResponseEntity<String> addFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        userService.addFriend(userId, friendId);
        return ResponseEntity.ok("Friend added successfully");
    }

    // Нови методи

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String query) {
        List<User> users = userService.searchUsers(query);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/{userId}/remove-friend/{friendId}")
    public ResponseEntity<String> removeFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        userService.removeFriend(userId, friendId);
        return ResponseEntity.ok("Friend removed successfully");
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> softDeleteUser(@PathVariable Long id) {
        userService.softDeleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestParam String newPassword) {
        User updatedUser = userService.updateUserPassword(id, newPassword);
        return ResponseEntity.ok(updatedUser);
    }
    @GetMapping("/{userId}/friends")
    public ResponseEntity<List<User>> getUserFriends(@PathVariable Long userId) {
        List<User> friends = userService.getUserFriends(userId);
        return ResponseEntity.ok(friends);
    }

    @GetMapping("/{userId}/channels")
    public ResponseEntity<List<Channel>> getUserChannels(@PathVariable Long userId) {
        List<Channel> channels = userService.getUserChannels(userId);
        return ResponseEntity.ok(channels);
    }

}
