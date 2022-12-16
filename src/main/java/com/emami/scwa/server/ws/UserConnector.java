package com.emami.scwa.server.ws;

import com.emami.scwa.coder.MessageDecoder;
import com.emami.scwa.coder.MessageEncoder;
import com.emami.scwa.model.Message;
import com.emami.scwa.repo.UserRepository;
import com.emami.scwa.server.auth.AuthenticationService;
import com.emami.scwa.server.ctrl.OnCloseCtrlUserConnector;
import com.emami.scwa.server.ctrl.OnMessageCtrlUserConnector;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/chat-user", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class UserConnector {
    private static final OnMessageCtrlUserConnector onMessageCtrl = OnMessageCtrlUserConnector.getInstance();
    private static final OnCloseCtrlUserConnector onCloseCtrl = OnCloseCtrlUserConnector.getInstance();
    private static final UserRepository userRepository = UserRepository.getInstance();

    @OnOpen
    public void onOpen(Session session) throws Exception {
        System.out.print("");
        Message message=new Message("SetClientId","","server",session.getId(),"","");
        session.getBasicRemote().sendObject(message);
    }

    @OnMessage
    public void onMessage(Session session,Message message) throws Exception {
        if (message.getUserType().equals("admin"))
            if (!AuthenticationService.getInstance().checkForAdmin(message.getAuthentication())) return;
        switch (message.getMessageType()) {
            case "JoinToServer":
                onMessageCtrl.handleJoinToServer(session, message);
                broadcastServerStatusUpdate();
                break;
            case "RequestToChatWithMe":
                onMessageCtrl.handleRequestToChatWithMe(session, message);
                broadcastServerStatusUpdate();
                break;
            case "ConnectToChatWithMe":
                if (message.getUserType().equals("admin"))
                    onMessageCtrl.handleConnectToChatWithMe(session, message);
                broadcastServerStatusUpdate();
                break;
            case "ChatWithEach":
                onMessageCtrl.handleChatWithEach(session, message);
                break;
            case "DisconnectUserFromChat":
                if (message.getUserType().equals("admin"))
                    onMessageCtrl.handleDisconnectUserFromChat(session, message);
                broadcastServerStatusUpdate();
                break;
        }
    }

    @OnClose
    public void onClose(Session session) throws Exception {
        try {
            onCloseCtrl.handleLeftToServer(session);
        }catch (Exception e){
            System.out.print("");
        }
    }
    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.print("");
    }

    public void broadcastServerStatusUpdate() {
        userRepository.findByType("admin").forEach(user -> {
            try {
                user.getSession().getBasicRemote().sendObject(new Message("serverUpdate", user.getType(), "server", user.getId(), "server has bean update", ""));
            } catch (Exception e) {
                System.out.print("");
            }
        });
    }
}
