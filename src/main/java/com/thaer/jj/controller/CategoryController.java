package com.thaer.jj.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.HashMap;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since March 07, 2016.
 */
@Path("categories")
public class CategoryController extends MainController {

    @GET
    @Path("/all")
    public Response getAllCategories() {

        HashMap<String, Object> cat = new HashMap<>();
        cat.put("Dresses", 3);
        cat.put("Jackets & Coats", 4);
        return Response.ok().entity(toJson(cat)).build();
    }

}
