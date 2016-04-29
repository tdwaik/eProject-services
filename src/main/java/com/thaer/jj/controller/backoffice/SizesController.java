package com.thaer.jj.controller.backoffice;

import com.thaer.jj.entities.Size;
import com.thaer.jj.model.SizeModel;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since Apr 15, 2016.
 */
@Path("backoffice/sizes")
public class SizesController extends BackOfficeController {

    @PUT @Path("/")
    public Response addSize(@FormParam("categoryId") int categoryId, @FormParam("name") String name) {
        try {
            SizeModel sizeModel = new SizeModel();
            Size size = new Size();
            size.setCategoryId(categoryId);
            size.setName(name);

            int newSizeId = sizeModel.addSize(size);
            size.setId(newSizeId);

            return Response.status(Response.Status.CREATED).entity(toJson(size)).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }

    @POST @Path("/")
    public Response editSize(@FormParam("id") int id, @FormParam("name") String name) {
        try {
            SizeModel sizeModel = new SizeModel();
            Size size = new Size();
            size.setId(id);
            size.setName(name);

            boolean result = sizeModel.updateSize(size);
            if(result) {
                return Response.status(Response.Status.ACCEPTED).entity(toJson(size)).build();
            }else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
