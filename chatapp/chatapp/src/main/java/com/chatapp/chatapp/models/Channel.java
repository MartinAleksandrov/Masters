package com.chatapp.chatapp.models;

import com.chatapp.chatapp.roles.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "channels")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id" , nullable = false)
    private User owner;

    @ManyToMany
    @JoinTable(
            name = "channel_users",
            joinColumns = @JoinColumn(name = "channel_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> members = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "channel_roles", joinColumns = @JoinColumn(name = "channel_id"))
    @MapKeyJoinColumn(name = "user_id")
    @Column(name = "role")
    private java.util.Map<User, Role> roles = new java.util.HashMap<>();

    public Channel(String name, User owner) {
        this.name = name;
        this.owner = owner;
        this.members = new HashSet<>();
        this.roles = new HashMap<>();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
    }

    public Set<User> getMembers() {
        return members;
    }

    public Map<User, Role> getRoles() {
        return roles;
    }
}
