package com.thaer.jj.controller.sellers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by stig on 4/23/16.
 */
@Path("sellers")
public class SellerController extends SellersBaseController {

    @GET
    @Path("/isLogin")
    public Response isLogin() {

        if(getAuthSeller() != null) {
            return Response.ok().build();
        }else {
            return Response.status(401).build();
        }

    }

}
