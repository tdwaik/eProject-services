package com.thaer.jj.controller.eproject;

import com.thaer.jj.model.BuyerModel;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 10, 2016.
 */

@Path("buyers")
public class BuyerController extends MainController {

    @GET @Path("/authBuyer")
    public Response getUser() {
        if(getAuthBuyer() == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        return Response.ok().type(MediaType.APPLICATION_JSON).entity(toJson(getAuthBuyer())).build();
    }

    @PUT @Path("/addBuyer")
    public Response addUser(
            @FormParam("email") String email,
            @FormParam("password") String password,
            @FormParam("firstname") String firstname,
            @FormParam("lastname") String lastname) {

        try {
            BuyerModel buyerModel = new BuyerModel();
            int addResult = buyerModel.addUser(email, password, firstname, lastname);
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
