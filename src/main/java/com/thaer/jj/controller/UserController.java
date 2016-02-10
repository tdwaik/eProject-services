package com.thaer.jj.controller;

import com.thaer.jj.entities.User;
import com.thaer.jj.model.UserModel;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("user")
public class UserController extends AbstractController {

    @GET @Path("/getUser/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getUser(@PathParam("userId") int id) {
        UserModel userModel = new UserModel();
        User user = new User();
        try {
            userModel.addUser("tdwaik", "t_dwaik@hotmail.com", "Thaer", "AlDwaik", "+962791305948");
            user = userModel.getUserById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return toJson(user);
    }
}
