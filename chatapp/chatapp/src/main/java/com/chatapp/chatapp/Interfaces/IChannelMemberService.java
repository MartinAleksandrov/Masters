package com.chatapp.chatapp.Interfaces;

import com.chatapp.chatapp.models.Channel;
import com.chatapp.chatapp.models.ChannelMember;
import com.chatapp.chatapp.models.User;
import com.chatapp.chatapp.roles.Role;

import java.util.List;
import java.util.Optional;

public interface IChannelMemberService {
    ChannelMember addMember(Channel channel, User user, Role role);

    void removeMember(Channel channel, User user);

    Optional<ChannelMember> getMember(Channel channel, User user);

    List<ChannelMember> getAllMembers(Channel channel);

    boolean isUserInChannel(Channel channel, User user);

    boolean isUserAdmin(Channel channel, User user);
}
