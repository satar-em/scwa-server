package com.emami.scwa.model;

import com.google.gson.annotations.SerializedName;

import javax.websocket.Session;

public class User {
    private String id;
    private String name;
    private String type;
    private StatusType statusType;
    private transient Session session;
    private transient User connectedUser;
    @SerializedName("connectedUser")
    private ConnectedUser connectedUser0;

    public User() {
    }

    public User(String id, String name, String type, Session session, User connectedUser,StatusType statusType) {
        this.statusType=statusType;
        this.id = id;
        this.name = name;
        this.type = type;
        this.session = session;
        if (connectedUser != null)
            this.connectedUser0 = new ConnectedUser(connectedUser.getId(), connectedUser.getName());
        this.connectedUser = connectedUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public StatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public User getConnectedUser() {
        return connectedUser;
    }

    public void setConnectedUser(User connectedUser) {
        if (connectedUser != null)
            this.connectedUser0 = new ConnectedUser(connectedUser.getId(), connectedUser.getName());
        this.connectedUser = connectedUser;
    }

    public ConnectedUser getConnectedUser0() {
        return connectedUser0;
    }

    public void setConnectedUser0(ConnectedUser connectedUser0) {
        this.connectedUser0 = connectedUser0;
    }

    public enum StatusType {
        Waiting, Ready, Sleeping,NewJoin,Talking
    }

    public class ConnectedUser {
        private String id;
        private String name;

        public ConnectedUser() {
        }

        public ConnectedUser(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
