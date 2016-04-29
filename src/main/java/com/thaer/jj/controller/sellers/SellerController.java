package com.thaer.jj.controller.sellers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since April 23, 2016.
 */
@Path("sellers")
public class SellerController extends SellersBaseController {

    @GET
    @Path("/authSeller")
    public Response authSeller() {
        if(getAuthSeller() != null) {
            return Response.ok().entity(toJson(getAuthSeller())).build();
        }else {
            return Response.status(401).build();
        }
    }

}
