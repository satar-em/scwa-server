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
        Message messageForUserJoinServer = new Message("AcceptJoin", message.getUserType(), "server", session.getId(), "welcome to server , dear " + message.getContent() + " <3", message.getAuthentication());
        try {
            session.getBasicRemote().sendObject(messageForUserJoinServer);
        } catch (Exception e) {
            System.out.print("");
        }
    }

    public void handleRequestToChatWithMe(Session session, Message message) {
        User userWaiter = userRepository.find(session.getId());
        if (userWaiter == null) return;
        if (userWaiter.getStatusType().equals(User.StatusType.Waiting))
            return;
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
        userRepository.connectToEach(userAccept, userWaiter);
        Message messageForUserAccept = new Message("SuccessConnectToEach", userAccept.getType(), "server", userAccept.getId(), userWaiter.getId(), "");
        Message messageForUserWaiter = new Message("SuccessConnectToEach", userWaiter.getType(), "server", userWaiter.getId(), userAccept.getId(), "");
        try {
            userAccept.getSession().getBasicRemote().sendObject(messageForUserAccept);
            userWaiter.getSession().getBasicRemote().sendObject(messageForUserWaiter);
        } catch (Exception e) {
            System.out.print("");
        }
    }

    public void handleChatWithEach(Session session, Message message) {
        User userSender = userRepository.find(session.getId());
        User userReceiver = userRepository.find(message.getTo());
        Message ChatToEach = new Message("ChatWithEach", message.getUserType(), message.getFrom(), message.getTo(), message.getContent(), "");
        try {
            userSender.getSession().getBasicRemote().sendObject(ChatToEach);
            userReceiver.getSession().getBasicRemote().sendObject(ChatToEach);
        } catch (Exception e) {
            System.out.print("");
        }
    }

    public void handleDisconnectUserFromChat(Session session, Message message) {
        User userForDisconnect = userRepository.find(message.getContent());
        User userConnectWithUserDisconnect = userForDisconnect.getConnectedUser();
        Message messageForUserForDisconnect = new Message("DisconnectFromChat", userForDisconnect.getType(), "server", userForDisconnect.getId(), "you disconnect from chat", "");
        Message messageForUserConnectWithUserDisconnect = new Message("DisconnectFromChat", userConnectWithUserDisconnect.getType(), "server", userConnectWithUserDisconnect.getId(), "you disconnect from chat", "");
        Message messageForUserSetAction = new Message("AcceptDisconnectFromChat", message.getUserType(), "server", session.getId(), message.getContent(), "");
        userRepository.disconnectToEach(userForDisconnect);
        try {
            userForDisconnect.getSession().getBasicRemote().sendObject(messageForUserForDisconnect);
            userConnectWithUserDisconnect.getSession().getBasicRemote().sendObject(messageForUserConnectWithUserDisconnect);
            session.getBasicRemote().sendObject(messageForUserSetAction);
            userForDisconnect.getSession().close();
        } catch (Exception e) {
            System.out.print("");
        }
    }
}
