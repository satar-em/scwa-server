package com.emami.scwa.server.ws;

import com.emami.scwa.coder.MessageDecoder;
import com.emami.scwa.coder.MessageEncoder;
import com.emami.scwa.model.Message;
import com.emami.scwa.server.auth.AuthenticationService;
import com.emami.scwa.server.ctrl.OnCloseCtrlUserConnector;
import com.emami.scwa.server.ctrl.OnMessageCtrlUserConnector;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/chat-user", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class UserConnector {
    private static final OnMessageCtrlUserConnector onMessageCtrl = OnMessageCtrlUserConnector.getInstance();
    private static final OnCloseCtrlUserConnector onCloseCtrl = OnCloseCtrlUserConnector.getInstance();

    @OnOpen
    public void onOpen(Session session) throws Exception {
    }

    @OnMessage
    public void onMessage(Session session, Message message) throws Exception {
        if (message.getUserType().equals("admin"))
            if (!AuthenticationService.getInstance().checkForAdmin(message.getAuthentication())) return;
        switch (message.getMessageType()) {
            case "JoinToServer":
                onMessageCtrl.handleJoinToServer(session, message);
                break;
            case "RequestToChatWithMe":
                onMessageCtrl.handleRequestToChatWithMe(session, message);
                break;
            case "ConnectToChatWithMe":
                if (message.getUserType().equals("admin"))
                    onMessageCtrl.handleConnectToChatWithMe(session, message);
                break;
            case "ChatWithEach":
                onMessageCtrl.handleChatWithEach(session, message);
                break;
            case "DisconnectUserFromChat":
                if (message.getUserType().equals("admin"))
                    onMessageCtrl.handleDisconnectUserFromChat(session, message);
                break;
        }
    }

    @OnClose
    public void onClose(Session session) throws Exception {
        onCloseCtrl.handleLeftToServer(session);
    }
}
