package com.thaer.jj.controller;

import com.thaer.jj.model.OfferModel;
import com.thaer.jj.model.helpers.ProductDetails;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 28, 2016
 */

@Path("offer")
public class OfferController extends MainController {

    private OfferModel offerModel;

    public OfferController() throws SQLException, IOException, ClassNotFoundException {
        offerModel = new OfferModel();
    }

    @GET
    @Path("/getLastProducts")
    public Response getLastProducts() {
        try {
            ArrayList<ProductDetails> productDetailsList = offerModel.getLastProducts();

            return Response.ok().entity(toJson(productDetailsList)).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).build();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return Response.status(500).build();
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }

    }

    @GET
    @Path("/getProductDetails/{offerId}")
    public Response getProductDetails(@PathParam("offerId") int offerId) {
        try {

            if(offerId > 0) {
                ProductDetails productDetailsDetails = offerModel.getProductDetails(offerId);
                return Response.ok().entity(toJson(productDetailsDetails)).build();

            }else {
                return Response.status(400).build();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).build();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return Response.status(500).build();
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }

    }

}
