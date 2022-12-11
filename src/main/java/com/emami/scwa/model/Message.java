package com.emami.scwa.model;

import javax.websocket.Session;

public class Message {
    private String messageType;
    private String userType;
    private String from;
    private String to;
    private String content;
    private String authentication;

    public Message() {
    }

    public Message(String messageType, String userType, String from, String to, String content, String authentication) {
        this.messageType = messageType;
        this.userType = userType;
        this.from = from;
        this.to = to;
        this.content = content;
        this.authentication = authentication;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }
}
