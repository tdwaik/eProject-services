package com.thaer.jj.controller.eproject;

import com.thaer.jj.core.JWTAuth;
import com.thaer.jj.entities.Buyer;
import com.thaer.jj.exceptions.UnAuthorizedException;
import com.thaer.jj.model.BuyerModel;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
            Buyer buyer = new Buyer();
            buyer.setEmail(email);
            buyer.setFirstname(firstname);
            buyer.setLastname(lastname);
            buyer.setStatus("unconfirmed_email");
            int addResult = buyerModel.addBuyer(buyer, password);
            if(addResult > 0) {

                try {
                    // login Buyer
                    JWTAuth jwtAuth = new JWTAuth();
                    String jwtAuthorization = jwtAuth.generateBuyerAuth(email, password, request.getRemoteAddr(), true);
                    return Response.status(Response.Status.CREATED).header("Authorization", jwtAuthorization).build();
                } catch (UnAuthorizedException e) {
                    e.printStackTrace();
                    return Response.status(Response.Status.CREATED).build();
                }

            }else if(addResult == -1) {
                return Response.status(Response.Status.CONFLICT).build();
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
