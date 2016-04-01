package com.thaer.jj.controller.eproject;

import com.thaer.jj.entities.Size;
import com.thaer.jj.model.SizeModel;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since March 23, 2016.
 */

@Path("sizes")
public class SizesController extends MainController {

    @GET @Path("/getCategorySizes/{categoryId}")
    public Response getSizesByCategoryId(@PathParam("categoryId") int categoryId) {
        try {
            SizeModel sizeModel = new SizeModel();
            ArrayList<Size> sizes = sizeModel.getSizesByCategoryId(categoryId);

            if(sizes.size() > 0) {
                return Response.ok().entity(toJson(sizes)).build();
            }else {
                return Response.status(204).build();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }
    }
}
