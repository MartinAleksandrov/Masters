package com.chatapp.chatapp.dtos;

import org.antlr.v4.runtime.misc.NotNull;

public class ChannelRequest {

    @NotNull
    private String name;
    private Long ownerId;

    @NotNull
    public String getName() { return name; }
    public Long getOwnerId() { return ownerId; }
}
