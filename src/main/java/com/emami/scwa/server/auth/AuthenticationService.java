package com.emami.scwa.server.auth;

public class AuthenticationService {
    private static AuthenticationService authenticationService = null;

    private AuthenticationService() {
    }
    public static AuthenticationService getInstance(){
        if (authenticationService==null)
            authenticationService=new AuthenticationService();
        return authenticationService;
    }
    public boolean checkForAdmin(String Authentication){
        return "admin-Satar".equals(Authentication);
    }
}
