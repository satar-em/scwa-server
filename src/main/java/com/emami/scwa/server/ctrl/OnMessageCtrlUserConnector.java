package com.emami.scwa.server.ctrl;

import com.emami.scwa.model.Message;
import com.emami.scwa.model.User;
import com.emami.scwa.repo.UserRepository;

import javax.websocket.Session;

public class OnMessageCtrlUserConnector {
    private static OnMessageCtrlUserConnector onMessageCtrlUserConnector = null;
    private static final UserRepository userRepository = UserRepository.getInstance();

    private OnMessageCtrlUserConnector() {

    }

    public static OnMessageCtrlUserConnector getInstance() {
        if (onMessageCtrlUserConnector == null)
            onMessageCtrlUserConnector = new OnMessageCtrlUserConnector();
        return onMessageCtrlUserConnector;
    }

    public void handleJoinToServer(Session session, Message message) {
        User user = new User(session.getId(), message.getContent(), message.getUserType(), session, null, User.StatusType.NewJoin);
        userRepository.save(user);
        Message messageForUserJoinServer = new Message("acceptJoin", message.getUserType(), "server", session.getId(), "welcome to server , dear " + message.getContent() + " <3", message.getAuthentication());
        try {
            session.getBasicRemote().sendObject(messageForUserJoinServer);
        } catch (Exception e) {
            System.out.print("");
        }
    }

    public void handleRequestToChatWithMe(Session session, Message message) {
        User userWaiter = userRepository.find(session.getId());
        if (userWaiter == null) return;
        userWaiter.setStatusType(User.StatusType.Waiting);
        userRepository.save(userWaiter);
        Message messageForUserJoinServer = new Message("acceptRequestToChatWithMe", message.getUserType(), "server", session.getId(), "we are looking for admin for you", message.getAuthentication());
        try {
            session.getBasicRemote().sendObject(messageForUserJoinServer);
        } catch (Exception e) {
            System.out.print("");
        }
    }

    public void handleConnectToChatWithMe(Session session, Message message) {
        User userAccept = userRepository.find(session.getId());
        User userWaiter = userRepository.find(message.getContent());
        Message messageForUserAccept = new Message("SuccessConnectToEach", userAccept.getType(), "server", userAccept.getId(), userWaiter.getId(), null);
        Message messageForUserWaiter = new Message("SuccessConnectToEach", userWaiter.getType(), "server", userWaiter.getId(), userAccept.getId(), null);
        try {
            userAccept.getSession().getBasicRemote().sendObject(messageForUserAccept);
            userWaiter.getSession().getBasicRemote().sendObject(messageForUserWaiter);
        } catch (Exception e) {
            System.out.print("");
        }
    }
}
