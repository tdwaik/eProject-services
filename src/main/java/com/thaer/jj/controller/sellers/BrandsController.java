package com.thaer.jj.controller.sellers;

import com.thaer.jj.entities.Brand;
import com.thaer.jj.model.BrandsModel;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since Apr 08, 2016.
 */
@Path("brands")
public class BrandsController extends SellersBaseController {

    @GET
    @Path("getBrandByAuthSeller")
    public Response getBrandByAuthSeller() {
        try {
            BrandsModel brandsModel = new BrandsModel();
            ArrayList<Brand> brands = brandsModel.getBrandsBySellerId(getAuthSeller().getId());
            return Response.ok().entity(toJson(brands)).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }
    }
}
