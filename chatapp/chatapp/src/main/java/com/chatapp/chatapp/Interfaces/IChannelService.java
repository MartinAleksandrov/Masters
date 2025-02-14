package com.chatapp.chatapp.Interfaces;

import com.chatapp.chatapp.models.Channel;
import com.chatapp.chatapp.models.User;

import java.util.List;
import java.util.Optional;

public interface IChannelService {
    Channel createChannel(String name, User owner);
    Optional<Channel> findChannelById(Long id);
    List<Channel> getAllChannels();
    boolean addUserToChannel(Long channelId, Long userId, User owner);
    boolean deleteChannel(Long channelId, User owner);
}
