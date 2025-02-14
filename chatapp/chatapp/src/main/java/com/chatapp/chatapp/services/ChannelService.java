package com.chatapp.chatapp.services;

import com.chatapp.chatapp.Interfaces.IChannelService;
import com.chatapp.chatapp.models.Channel;
import com.chatapp.chatapp.models.User;
import com.chatapp.chatapp.repositories.ChannelRepository;
import com.chatapp.chatapp.repositories.UserRepository;
import com.chatapp.chatapp.roles.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChannelService implements IChannelService {

    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;

    // Конструктор
    public ChannelService(UserRepository userRepo, ChannelRepository channelRepo) {
        this.userRepository = userRepo;
        this.channelRepository = channelRepo;
    }

    @Override
    public Channel createChannel(String name, User owner) {
        // Създаване на нов канал с конструктора
        Channel newChannel = new Channel(name, owner);

        // Добавяме собственика като член
        newChannel.getMembers().add(owner);

        // Даваме му роля ADMIN
        newChannel.getRoles().put(owner, Role.ADMIN);

        // Запазваме канала в базата
        return channelRepository.save(newChannel);
    }

    @Override
    public Optional<Channel> findChannelById(Long id) {
        return channelRepository.findById(id);
    }

    @Override
    public List<Channel> getAllChannels() {
        return channelRepository.findAll();
    }

    @Override
    public boolean addUserToChannel(Long channelId, Long userId, User owner) {
        Optional<User> currUser = userRepository.findById(userId);
        Optional<Channel> currChannel = channelRepository.findById(channelId);

        if (currChannel.isPresent() && currUser.isPresent()) {
            User user = currUser.get();
            Channel channel = currChannel.get();

            // Проверяваме дали собственикът на канала съвпада с този, който иска да добави нов потребител
            if (channel.getOwner().equals(owner)) {
                channel.getMembers().add(user);

                // Новият потребител получава роля MEMBER по подразбиране
                channel.getRoles().put(user, Role.MEMBER);

                channelRepository.save(channel);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteChannel(Long channelId, User owner) {
        Optional<Channel> currChannel = channelRepository.findById(channelId);

        if (currChannel.isPresent()) {
            Channel channel = currChannel.get();

            // Проверяваме дали само собственикът може да изтрие канала
            if (channel.getOwner().equals(owner)) {
                channelRepository.delete(channel);
                return true;
            }
        }
        return false;
    }
    public boolean removeUserFromChannel(Long channelId, Long userId, Long ownerId) {
        Optional<Channel> channelOpt = channelRepository.findById(channelId);
        Optional<User> userOpt = userRepository.findById(userId);

        if (channelOpt.isPresent() && userOpt.isPresent()) {
            Channel channel = channelOpt.get();
            User user = userOpt.get();

            if (channel.getOwner().getId().equals(ownerId) || channel.getRoles().get(ownerId) == Role.ADMIN) {
                channel.getMembers().remove(user);
                channelRepository.save(channel);
                return true;
            }
        }
        return false;
    }

    public boolean assignAdminRole(Long channelId, Long userId, Long ownerId) {
        Optional<Channel> channelOpt = channelRepository.findById(channelId);
        Optional<User> userOpt = userRepository.findById(userId);

        if (channelOpt.isPresent() && userOpt.isPresent()) {
            Channel channel = channelOpt.get();
            User user = userOpt.get();

            if (channel.getOwner().getId().equals(ownerId)) {
                channel.getRoles().put(user, Role.ADMIN);
                channelRepository.save(channel);
                return true;
            }
        }
        return false;
    }
    public List<User> getChannelAdmins(Long channelId) {
        return channelRepository.findChannelAdmins(channelId);
    }
}
