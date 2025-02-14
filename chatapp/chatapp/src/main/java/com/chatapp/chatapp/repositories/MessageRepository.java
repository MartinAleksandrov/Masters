package com.chatapp.chatapp.repositories;

import com.chatapp.chatapp.models.Channel;
import com.chatapp.chatapp.models.Message;
import com.chatapp.chatapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    // Намери всички съобщения между двама потребители
    List<Message> findBySenderAndReceiver(User sender, User receiver);

    // Намери всички съобщения в даден канал
    List<Message> findByChannel(Channel channel);
}