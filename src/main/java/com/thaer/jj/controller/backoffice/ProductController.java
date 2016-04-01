package com.thaer.jj.controller.backoffice;

import javax.ws.rs.FormParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 28, 2016.
 */

@Path("backoffice/product")
public class ProductController extends BackOfficeController {

    @PUT
    @Path("/addProduct")
    public Response addProduct(
            @FormParam("title") String title,
            @FormParam("description") String description,
            @FormParam("price") int price,
            @FormParam("amount") int amount
    ) {

        return Response.ok().build();

    }

}
