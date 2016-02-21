package com.thaer.jj.controller;

import com.thaer.jj.entities.User;
import com.thaer.jj.model.UserModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.net.HttpCookie;
import java.sql.SQLException;

/**
 * Created by Thaer AlDwaik on February 11, 2016.
 */

@Path("userAuth")
public class UserAuthController extends AbstractController {

    private HttpSession session;
    private HttpCookie cookies;

    public UserAuthController(@Context HttpServletRequest request) {
        session = request.getSession();
        request.getCookies();
    }

    @POST @Path("/login")
    public Response login(@FormParam("email") String email, @FormParam("password") String password) throws SQLException, ClassNotFoundException {

//        // logout if user is login
//        if(session.getAttribute("is_in") == "in") {
//            logout();
//        }

        try {
            UserModel userModel = new UserModel();

            //if(userModel.checkUserPasswordByUserEmail(email.toLowerCase(), password)) {
                session.setAttribute("is_in", "in");



                //NewCookie cookie = new NewCookie("is_in","in");

                return Response.ok().build();

//            }else {
//                return Response.status(403).build();
//            }

        } catch (Exception e) {
            return Response.status(500).build();
        }

    }

    @GET @Path("/isLogin")
    public Response isLogin(@CookieParam("is_in") Cookie cookie) {
        if(cookie.toString() == "in") {
            return Response.ok().build();
        }

        return Response.status(403).build();
    }

//    @GET @Path("/logout")
//    public Response logout() {
//        session.removeAttribute("user_id");
//        session.removeAttribute("is_in");
//
//        return Response.ok().build();
//    }

}
