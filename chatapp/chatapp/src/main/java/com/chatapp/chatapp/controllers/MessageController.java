package com.chatapp.chatapp.controllers;

import com.chatapp.chatapp.models.Channel;
import com.chatapp.chatapp.models.Message;
import com.chatapp.chatapp.models.User;
import com.chatapp.chatapp.services.ChannelService;
import com.chatapp.chatapp.services.MessageService;
import com.chatapp.chatapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;
    private final UserService userService;
    private final ChannelService channelService;

    @Autowired
    public MessageController(MessageService messageService, UserService userService, ChannelService channelService) {
        this.messageService = messageService;
        this.userService = userService;
        this.channelService = channelService;
    }

    @PostMapping("/private")
    public ResponseEntity<Message> sendPrivateMess(@RequestParam Long senderid,@RequestParam Long receiverid,@RequestParam String content ) {

        Optional<User> currSender = userService.findUserById(senderid);
        Optional<User> currReceiver = userService.findUserById(receiverid);

        if (currReceiver.isPresent() && currSender.isPresent()) {
            User receiver = currReceiver.get();
            User sender = currSender.get();

            Message message = messageService.sendPrivateMessage(sender,receiver,content);

            return ResponseEntity.ok(message);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/messages/private")
    public ResponseEntity<Message> sendChannelMess (@RequestParam Long senderid,@RequestParam Long channelid,@RequestParam String content ) {

        Optional<User> currSender = userService.findUserById(senderid);
        Optional<Channel> currChannel = channelService.findChannelById(channelid);


        if (currChannel.isPresent() && currSender.isPresent()) {
        Channel channel = currChannel.get();
        User sender = currSender.get();

        Message message = messageService.sendChannelMessage(sender,channel,content);

        return ResponseEntity.ok(message);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/channel/{channelId}")
    public ResponseEntity<List<Message>> getChannelMess(@PathVariable Long channelId) {

        Optional<Channel> currChannel = channelService.findChannelById(channelId);

        if (currChannel.isPresent()) {
            Channel channel = currChannel.get();
            return ResponseEntity.ok(messageService.getChannelMessages(channel));
        }

        return ResponseEntity.notFound().build();
    }

}
