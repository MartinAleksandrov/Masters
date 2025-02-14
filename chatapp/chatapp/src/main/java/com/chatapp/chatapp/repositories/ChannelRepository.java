package com.chatapp.chatapp.repositories;
import com.chatapp.chatapp.models.Channel;
import com.chatapp.chatapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
    Optional<Channel> findByName(String name);
    @Query("SELECT cm.user FROM ChannelMember cm WHERE cm.channel.id = :channelId AND cm.role = 'ADMIN'")
    List<User> findChannelAdmins(@Param("channelId") Long channelId);
}
