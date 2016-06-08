package com.thaer.jj.controller.eproject;

import com.thaer.jj.model.OfferModel;
import com.thaer.jj.model.responseData.OfferViewResponse;
import com.thaer.jj.model.responseData.LastOffersResponse;

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
    public Response getOfferDetailsList(@PathParam("order") String order, @PathParam("from") int from, @PathParam("to") int to) {
        try {
            ArrayList<LastOffersResponse> offerDetailsList = offerModel.getLastOffers();

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

    @GET
    @Path("/details/{offerId}/{offerOptionId}")
    public Response getOfferDetails(@PathParam("offerId") int offerId, @PathParam("offerOptionId") int offerOptionId) {

        if(offerOptionId < 1) {
            return Response.status(400).build();
        }

        try {
            OfferViewResponse offerViewResponse = offerModel.getOfferDetailsByVariationId(offerId, offerOptionId);
            return Response.ok().entity(toJson(offerViewResponse)).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }

    }

}


