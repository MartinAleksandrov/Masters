package com.chatapp.chatapp.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean active = true;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Channel> ownedChannels;

    @ManyToMany
    @JoinTable(
            name = "user_channels",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "channel_id")
    )
    private Set<Channel> channels;

    @ManyToMany
    @JoinTable(
            name = "friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private Set<User> friends;

    // Конструктор без аргументи
    public User() {
    }

    // Пълен конструктор
    public User(String username, String password, boolean active) {
        this.username = username;
        this.password = password;
        this.active = active;
    }

    // Гетъри
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isActive() {
        return active;
    }

    public List<Channel> getOwnedChannels() {
        return ownedChannels;
    }

    public Set<Channel> getChannels() {
        return channels;
    }

    public Set<User> getFriends() {
        return friends;
    }

    // Сетъри
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    // Методи за управление на приятели
    public void addFriend(User friend) {
        this.friends.add(friend);
    }

    public void removeFriend(User friend) {
        this.friends.remove(friend);
    }
}
