package com.thaer.jj.controller;

import com.thaer.jj.model.OfferModel;
import com.thaer.jj.model.sets.ProductDetails;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since March 02, 2016.
 */
@Path("products")
public class ProductController extends MainController {

    private OfferModel offerModel;

    public ProductController() throws SQLException, IOException, ClassNotFoundException {
        offerModel = new OfferModel();
    }

    @GET
    @Path("/list/{order}/{from}/{to}")
    public Response getProductsList(@PathParam("order") String order, @PathParam("from") int from, @PathParam("to") int to) {
        try {
            ArrayList<ProductDetails> productDetailsList = offerModel.getProductsList(order, from , to);

            return Response.ok().entity(toJson(productDetailsList)).build();

        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }

    }

    @GET
    @Path("/{offerId}")
    public Response getProductDetails(@PathParam("offerId") int offerId) {
        try {

            if(offerId > 0) {
                ProductDetails productDetailsDetails = offerModel.getProductDetails(offerId);
                return Response.ok().entity(toJson(productDetailsDetails)).build();

            }else {
                return Response.status(400).build();
            }

        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }

    }

}
