package com.emami.scwa.server.ws;

import com.emami.scwa.coder.MessageDecoder;
import com.emami.scwa.coder.MessageEncoder;
import com.emami.scwa.model.Message;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/chat-user", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class UserConnector {
    @OnOpen
    public void onOpen(Session session) throws Exception {
    }

    @OnMessage
    public void onMessage(Session session, Message message)throws Exception{

    }

    @OnClose
    public void onClose(Session session) throws Exception {
    }
}
