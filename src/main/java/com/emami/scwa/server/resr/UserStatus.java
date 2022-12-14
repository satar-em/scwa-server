package com.emami.scwa.server.resr;

import com.emami.scwa.repo.UserRepository;
import com.emami.scwa.server.auth.AuthenticationService;
import com.google.gson.Gson;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/user-status")
public class UserStatus {
    @Path("/all")
    @GET
    @Produces("application/json")
    public Response getAllUser(@HeaderParam("Authentication") String authentication){
        if(!AuthenticationService.getInstance().checkForAdmin(authentication))
            return Response.status(401).build();
        Gson gson=new Gson();
        Response.ResponseBuilder responseBuilder=Response.ok(gson.toJson(UserRepository.getInstance().getAll()));
        return responseBuilder.build();
    }

}
