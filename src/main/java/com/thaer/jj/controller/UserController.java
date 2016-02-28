package com.thaer.jj.controller;

import com.thaer.jj.entities.User;
import com.thaer.jj.model.UserModel;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 10, 2016.
 */

@Path("user")
public class UserController extends AbstractController {

    @GET @Path("/getUser/{userId}")
    public Response getUser(@PathParam("userId") int userId) {
        User user = new User();
        try {
            UserModel userModel = new UserModel();
            user = userModel.getUserById(userId);
            return Response.ok().type(MediaType.APPLICATION_JSON).entity(toJson(user)).build();
        } catch (Exception e) {
            return Response.status(500).build();
        }

    }

    @PUT @Path("/addUser")
    public Response addUser(
            @FormParam("username") String username,
            @FormParam("email") String email,
            @FormParam("password") String password,
            @FormParam("firstname") String firstname,
            @FormParam("lastname") String lastname,
            @FormParam("phone_number") String phone_number) {

        try {
            UserModel userModel = new UserModel();
            if(userModel.addUser(username, email, password, firstname, lastname, phone_number) == 1) {
                return Response.status(201).build();
            }else {
                return Response.status(400).build();
            }
        } catch (Exception e) {
            return Response.status(500).build();
        }

    }
}
