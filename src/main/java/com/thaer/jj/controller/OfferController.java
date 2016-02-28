package com.thaer.jj.controller;

import com.thaer.jj.model.OfferModel;
import com.thaer.jj.model.helper.Product;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Thaer Aldwaik on February 28, 2016
 */

@Path("offer")
public class OfferController extends AbstractController {

    private OfferModel offerModel;

    public OfferController() throws SQLException, IOException, ClassNotFoundException {
        offerModel = new OfferModel();
    }

    @GET
    @Path("/getLastProducts")
    public Response getLastProducts() {
        try {
            ArrayList<Product> productList = offerModel.getLastProducts();

            return Response.ok().entity(toJson(productList)).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).build();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }

    }

    @GET
    @Path("/getProductDetails/{offerId}")
    public Response getProductDetails(@PathParam("offerId") int offerId) {
        try {
            Product productDetails = offerModel.getProductDetails(offerId);

            return Response.ok().entity(toJson(productDetails)).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).build();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }

    }

}
