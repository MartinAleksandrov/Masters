package com.chatapp.chatapp.controllers;

import com.chatapp.chatapp.dtos.ChannelRequest;
import com.chatapp.chatapp.models.Channel;
import com.chatapp.chatapp.models.User;
import com.chatapp.chatapp.services.ChannelService;
import com.chatapp.chatapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/channels")
public class ChannelController {

    private ChannelService channelService;
    private UserService userService;

    public ChannelController(ChannelService channelService, UserService userService) {
        this.channelService = channelService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Channel> getChannelById(@PathVariable long id) {
        Optional<Channel> channel =  channelService.findChannelById(id);

        if (channel.isPresent()) {
            return ResponseEntity.ok(channel.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{channelId}/addUser")
    public ResponseEntity<String> addUserToChannel(@PathVariable Long channelId, @RequestParam Long userId,@RequestParam Long ownerId) {

        Optional<User> owner = userService.findUserById(ownerId);

        if (owner.isPresent()) {
            var result = channelService.addUserToChannel(channelId, userId, owner.get());

            if (result){
                return ResponseEntity.ok("User added to channel");
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/create")
    public ResponseEntity<String> createChannel(@Validated @RequestBody ChannelRequest request) {
        Optional<User> owner = userService.findUserById(request.getOwnerId());

        if (owner.isPresent()) {
            Channel channel = channelService.createChannel(request.getName(), owner.get());
            if (channel != null) {
                return ResponseEntity.ok("Channel created");
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Channel>> getAllChannels() {
        return ResponseEntity.ok(channelService.getAllChannels());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteChannel(@RequestParam Long channelId, @RequestParam Long ownerId) {
        Optional<Channel> channel = channelService.findChannelById(channelId);
        Optional<User> owner = userService.findUserById(ownerId);

        if (channel.isPresent()) {
            var result = channelService.deleteChannel(channelId,owner.get());

            if (result) {
                return ResponseEntity.ok("Channel deleted");
            }
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/{channelId}/removeUser")
    public ResponseEntity<String> removeUserFromChannel(@PathVariable Long channelId, @RequestParam Long userId, @RequestParam Long ownerId) {
        boolean result = channelService.removeUserFromChannel(channelId, userId, ownerId);
        return result ? ResponseEntity.ok("User removed from channel") : ResponseEntity.badRequest().build();
    }

    @PostMapping("/{channelId}/makeAdmin")
    public ResponseEntity<String> makeAdmin(@PathVariable Long channelId, @RequestParam Long userId, @RequestParam Long ownerId) {
        boolean result = channelService.assignAdminRole(channelId, userId, ownerId);
        return result ? ResponseEntity.ok("User promoted to Admin") : ResponseEntity.badRequest().build();
    }
    @GetMapping("/{channelId}/admins")
    public ResponseEntity<List<User>> getChannelAdmins(@PathVariable Long channelId) {
        List<User> admins = channelService.getChannelAdmins(channelId);
        return ResponseEntity.ok(admins);
    }
}
