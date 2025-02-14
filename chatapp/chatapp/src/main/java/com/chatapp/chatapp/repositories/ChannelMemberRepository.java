package com.chatapp.chatapp.repositories;

import com.chatapp.chatapp.models.Channel;
import com.chatapp.chatapp.models.ChannelMember;
import com.chatapp.chatapp.models.User;
import com.chatapp.chatapp.roles.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChannelMemberRepository extends JpaRepository<ChannelMember, Long> {

    List<ChannelMember> findByChannel(Channel channel);

    Optional<ChannelMember> findByChannelAndUser(Channel channel, User user);

    boolean existsByChannelAndUserAndRole(Channel channel, User user, Role role);
}
