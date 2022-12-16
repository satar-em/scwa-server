package com.emami.scwa.server.ctrl;

import com.emami.scwa.model.Message;
import com.emami.scwa.model.User;
import com.emami.scwa.repo.UserRepository;

import javax.websocket.Session;

public class OnCloseCtrlUserConnector {
    private static OnCloseCtrlUserConnector onCloseCtrlUserConnector = null;
    private static final UserRepository userRepository = UserRepository.getInstance();

    private OnCloseCtrlUserConnector() {

    }

    public static OnCloseCtrlUserConnector getInstance() {
        if (onCloseCtrlUserConnector == null)
            onCloseCtrlUserConnector = new OnCloseCtrlUserConnector();
        return onCloseCtrlUserConnector;
    }

    public void handleLeftToServer(Session session) {
        Message message = new Message("Goodbye","","server",session.getId(),"goodbye bro :)","");
        try {
            session.getBasicRemote().sendObject(message);
        } catch (Exception e) {
            System.out.print("");
        }
        userRepository.removeUser(userRepository.find(session.getId()));
    }
}
