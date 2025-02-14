package com.chatapp.chatapp.repositories;

import com.chatapp.chatapp.models.Channel;
import com.chatapp.chatapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    List<User> findByUsernameContainingIgnoreCase(String username);
    List<User> findByActiveTrue();

    @Query("SELECT u.friends FROM User u WHERE u.id = :userId")
    List<User> findFriendsByUserId(@Param("userId") Long userId);

    @Query("SELECT c FROM Channel c JOIN c.members m WHERE m.id = :userId")
    List<Channel> findChannelsByUserId(@Param("userId") Long userId);
}
