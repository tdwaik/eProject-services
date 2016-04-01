package com.thaer.jj.controller.backoffice;

import com.thaer.jj.entities.User;
import com.thaer.jj.model.UserModel;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 28, 2016.
 */
@Path("users")
public class UserController extends BackOfficeController {

    @GET
    @Path("/{userId:\\d+}")
    public Response getUser(@PathParam("userId") int userId) {
        try {
            UserModel userModel = new UserModel();
            User user = userModel.getUserById(userId);
            return Response.ok().type(MediaType.APPLICATION_JSON).entity(toJson(user)).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }

    }

}
