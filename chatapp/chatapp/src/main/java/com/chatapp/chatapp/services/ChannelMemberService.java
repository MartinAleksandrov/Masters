package com.chatapp.chatapp.services;

import com.chatapp.chatapp.Interfaces.IChannelMemberService;
import com.chatapp.chatapp.models.Channel;
import com.chatapp.chatapp.models.ChannelMember;
import com.chatapp.chatapp.models.User;
import com.chatapp.chatapp.repositories.ChannelMemberRepository;
import com.chatapp.chatapp.roles.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChannelMemberService implements IChannelMemberService {

    private ChannelMemberRepository channelMemberRepository;

    public ChannelMemberService(ChannelMemberRepository repo) {
        this.channelMemberRepository = repo;
    }


    @Override
    public ChannelMember addMember(Channel channel, User user, Role role) {
        ChannelMember channelMember = new ChannelMember(channel, user, role);

        return channelMemberRepository.save(channelMember);
    }

    @Override
    public void removeMember(Channel channel, User user) {

       Optional<ChannelMember> channelMember = channelMemberRepository.findByChannelAndUser(channel, user);

       if (channelMember.isPresent()) {
           channelMemberRepository.delete(channelMember.get());
       }

    }

    @Override
    public Optional<ChannelMember> getMember(Channel channel, User user) {

        Optional<ChannelMember> channelMember = channelMemberRepository.findByChannelAndUser(channel, user);

        if (channelMember.isPresent()) {
            return channelMember;
        }
        return Optional.empty();

    }

    @Override
    public List<ChannelMember> getAllMembers(Channel channel) {
        return channelMemberRepository.findAll();
    }

    @Override
    public boolean isUserInChannel(Channel channel, User user) {
        if(channel.getMembers().contains(user)){
            return true;
        }
        return false;
    }

    @Override
    public boolean isUserAdmin(Channel channel, User user) {

        if(channel.getMembers().contains(user) && channel.getOwner().getId().equals(user.getId())){
            return true;
        }
        return false;
    }
}
