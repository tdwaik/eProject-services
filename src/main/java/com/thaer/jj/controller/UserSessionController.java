package com.thaer.jj.controller;

import com.thaer.jj.model.UserModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.HashMap;

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

        if(session.getAttribute("is_in") == "in") {
            return toJson(false);
        }

        UserModel userModel = new UserModel();

        try {
            userModel.getUserPasswordByEmail(email);
        } catch (Exception e) {
            return toJson(true, e);
        }

        session.setAttribute("user_id", "1");
        session.setAttribute("is_in", "in");

        return toJson(false);
    }

    @GET @Path("/isLogin")
    @Produces(MediaType.APPLICATION_JSON)
    public String isLogin() {
        Object a = session.getAttribute("is_in");
        if(session.getAttribute("is_in") == "in") {
            return toJson(false, true);
        }

        return toJson(false, false);
    }

    @GET @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    public String logout() {
        Object a = session.getAttribute("is_in");
        session.removeAttribute("user_id");
        session.removeAttribute("is_in");

        return toJson(false);
    }

}
