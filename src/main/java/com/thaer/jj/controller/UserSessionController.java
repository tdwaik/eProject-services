package com.thaer.jj.controller;

import com.thaer.jj.model.UserModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

/**
 * Created by Thaer AlDwaik on February 11, 2016.
 */

@Path("userSession")
public class UserSessionController extends AbstractController {

    private HttpSession session;

    public UserSessionController(@Context HttpServletRequest request) {
        session = request.getSession();
    }

    @POST @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public String login(@FormParam("email") String email, @FormParam("password") String password) throws SQLException, ClassNotFoundException {

        // logout if user is login
        if(session.getAttribute("is_in") == "in") {
            logout();
        }

        try {
            UserModel userModel = new UserModel();

            if(userModel.checkUserPasswordByUserEmail(email.toLowerCase(), password)) {
                session.setAttribute("is_in", "in");

                return response().error(false).toJson();
            }else {
                return response().error(true).toJson();
            }

        } catch (Exception e) {
            return response().error(true).message("ERROR").toJson();
        }

    }

    @GET @Path("/isLogin")
    @Produces(MediaType.APPLICATION_JSON)
    public String isLogin() {
        if(session.getAttribute("is_in") == "in") {
            return response().error(false).result(true).toJson();
        }

        return response().error(false).result(false).toJson();
    }

    @GET @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    public String logout() {
        session.removeAttribute("user_id");
        session.removeAttribute("is_in");

        return response().error(false).toJson();
    }

}
