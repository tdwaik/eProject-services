package com.thaer.jj.controller;

import com.thaer.jj.entities.User;
import com.thaer.jj.model.UserModel;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Thaer AlDwaik on February 10, 2016.
 */

@Path("user")
public class UserController extends AbstractController {

    @GET @Path("/getUser/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getUser(@PathParam("userId") int id) {
        User user = new User();
        try {
            UserModel userModel = new UserModel();
            user = userModel.getUserById(id);
            return response().error(false).result(user).toJson();
        } catch (Exception e) {
            e.printStackTrace();
            return response().error(true).toJson();
        }

    }

    @POST @Path("/addUser")
    @Produces(MediaType.APPLICATION_JSON)
    public String addUser(
            @FormParam("username") String username,
            @FormParam("email") String email,
            @FormParam("password") String password,
            @FormParam("firstname") String firstname,
            @FormParam("lastname") String lastname,
            @FormParam("phone_number") String phone_number) {

        try {
            UserModel userModel = new UserModel();
            userModel.addUser(username, email, password, firstname, lastname, phone_number);
            return response().error(false).toJson();
        } catch (Exception e) {
            e.printStackTrace();
            return response().error(true).toJson();
        }

    }
}
