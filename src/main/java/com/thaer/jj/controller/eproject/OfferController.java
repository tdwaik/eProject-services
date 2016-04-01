package com.thaer.jj.controller.eproject;

import com.thaer.jj.model.OfferModel;
import com.thaer.jj.model.sets.OfferDetails;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since March 02, 2016.
 */
@Path("offers")
public class OfferController extends MainController {

    private OfferModel offerModel;

    public OfferController() throws SQLException {
        offerModel = new OfferModel();
    }

    @GET
    @Path("/list/{order}/{from}/{to}")
    public Response getProductsList(@PathParam("order") String order, @PathParam("from") int from, @PathParam("to") int to) {
        try {
            ArrayList<OfferDetails> offerDetailsList = offerModel.getOfferDetailList();

            if(offerDetailsList.size() > 0) {
                return Response.ok().entity(toJson(offerDetailsList)).build();
            }else {
                return Response.status(204).build();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }

    }

}


