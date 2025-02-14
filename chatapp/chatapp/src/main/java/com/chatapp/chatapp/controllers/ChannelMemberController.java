package com.chatapp.chatapp.controllers;

import com.chatapp.chatapp.models.Channel;
import com.chatapp.chatapp.models.ChannelMember;
import com.chatapp.chatapp.models.User;
import com.chatapp.chatapp.roles.Role;
import com.chatapp.chatapp.services.ChannelMemberService;
import com.chatapp.chatapp.services.ChannelService;
import com.chatapp.chatapp.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/channel-members")
public class ChannelMemberController {

    private  ChannelMemberService channelMemberService;
    private  UserService userService;
    private  ChannelService channelService;

    public ChannelMemberController(UserService userService, ChannelService channelService,ChannelMemberService service) {
        this.channelMemberService = channelMemberService;
        this.userService = userService;
        this.channelService = channelService;
    }

    @PostMapping("/add")
    public ResponseEntity<ChannelMember> addMember(@RequestParam Long channelId, @RequestParam Long userId, @RequestParam Role role) {

        Optional<Channel> currChannel = channelService.findChannelById(channelId);
        Optional<User> currUser = userService.findUserById(userId);

        if (currChannel.isPresent() && currUser.isPresent()) {
            User user = currUser.get();
            Channel channel = currChannel.get();

            ChannelMember channelMember = channelMemberService.addMember(channel, user, role);

            return ResponseEntity.ok(channelMember);
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeMember(@RequestParam Long channelId, @RequestParam Long userId) {

        Optional<Channel> currChannel = channelService.findChannelById(channelId);
        Optional<User> currUser = userService.findUserById(userId);

        if (currChannel.isPresent() && currUser.isPresent()) {
            User user = currUser.get();
            Channel channel = currChannel.get();

            if (channel.getMembers().contains(user) && channel.getOwner().getId().equals(userId)) {
                channelMemberService.removeMember(channel, user);
                return ResponseEntity.ok().build();
            }

        }
        return ResponseEntity.noContent().build();
    }
}
