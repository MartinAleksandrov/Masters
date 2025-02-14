package com.chatapp.chatapp.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "messages")
@AllArgsConstructor
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String content;


    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;


    @ManyToOne
    @JoinColumn(name = "channel_Id")
    private Channel channel;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    private LocalDateTime dateTime;

    // Конструктор по подразбиране
    public Message() {}

    // Конструктор за лични съобщения
    public Message(User sender, User receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.dateTime = LocalDateTime.now();
    }

    // Конструктор за съобщения в канал
    public Message(User sender, Channel channel, String content) {
        this.sender = sender;
        this.channel = channel;
        this.content = content;
        this.dateTime = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public Channel getChannel() {
        return channel;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return dateTime;
    }
}
