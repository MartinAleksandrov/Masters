package com.chatapp.chatapp.Interfaces;

import com.chatapp.chatapp.models.Channel;
import com.chatapp.chatapp.models.Message;
import com.chatapp.chatapp.models.User;

import java.util.List;

public interface IMessageService {
    Message sendPrivateMessage(User sender, User receiver, String content);

    Message sendChannelMessage(User sender, Channel channel, String content);

    List<Message> getConversation(User user1, User user2);

    List<Message> getChannelMessages(Channel channel);
}
