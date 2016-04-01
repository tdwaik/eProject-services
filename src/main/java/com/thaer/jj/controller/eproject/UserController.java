package com.thaer.jj.controller.eproject;

import com.thaer.jj.entities.User;
import com.thaer.jj.model.UserModel;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 10, 2016.
 */

@Path("users")
public class UserController extends MainController {

    @GET @Path("/authUser")
    public Response getUser() {
        User authUser = getAuthUser();
        if(authUser == null) {
            return Response.status(401).build();
        }

        return Response.ok().type(MediaType.APPLICATION_JSON).entity(toJson(getAuthUser())).build();
    }

    @PUT @Path("/addUser")
    public Response addUser(
            @FormParam("email") String email,
            @FormParam("password") String password,
            @FormParam("firstname") String firstname,
            @FormParam("lastname") String lastname) {

        try {
            UserModel userModel = new UserModel();
            int addResult = userModel.addUser(email, password, firstname, lastname);
            if(addResult == 1) {
                return Response.status(201).build();
            }else {
                return Response.status(400).build();
            }
        }catch (IllegalArgumentException e) {
            return Response.status(400).build();
        }catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }

    }
}
