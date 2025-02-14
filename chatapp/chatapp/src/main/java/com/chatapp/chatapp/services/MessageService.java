package com.chatapp.chatapp.services;

import com.chatapp.chatapp.Interfaces.IMessageService;
import com.chatapp.chatapp.models.Channel;
import com.chatapp.chatapp.models.Message;
import com.chatapp.chatapp.models.User;
import com.chatapp.chatapp.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService implements IMessageService {

    private final MessageRepository messageRepository;

    // Конструктор с инжектиране на зависимостта
    public MessageService(MessageRepository messageRepo) {
        this.messageRepository = messageRepo;
    }

    @Override
    public Message sendPrivateMessage(User sender, User receiver, String content) {
        // Създаваме ново съобщение, като използваме стандартния конструктор
        Message message = new Message(sender, receiver, content);

        // Запазваме в базата
        return messageRepository.save(message);
    }

    @Override
    public Message sendChannelMessage(User sender, Channel channel, String content) {
        // Създаваме съобщение за канал
        Message message = new Message(sender ,channel, content);

        // Запазваме в базата
        return messageRepository.save(message);
    }

    @Override
    public List<Message> getConversation(User user1, User user2) {
        return messageRepository.findBySenderAndReceiver(user1, user2);
    }

    @Override
    public List<Message> getChannelMessages(Channel channel) {
        return messageRepository.findByChannel(channel);
    }
}
