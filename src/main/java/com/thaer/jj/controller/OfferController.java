package com.thaer.jj.controller;

import com.thaer.jj.model.OfferModel;
import com.thaer.jj.model.sets.OfferDetails;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

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

    @PUT
    @Path("/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response addOffer(
        @FormDataParam("offerDetailsJson") String offerDetailsJson,
        @FormDataParam("file") InputStream fileInputStream,
        @FormDataParam("file") FormDataContentDisposition fileMetaData
    ) {
        return Response.ok().build();
    }
//    @POST @Path("/add")
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    public Response addProduct(
//            @FormDataParam("offerPrice") int offerPrice,
//            @FormDataParam("offerAmount") int offerAmount,
//            @FormDataParam("itemTitle") String itemTitle,
//            @FormDataParam("itemDescription") String itemDescription,
//            @FormDataParam("itemCategoryId") int itemCategoryId,
//            @FormDataParam("file") InputStream fileInputStream,
//            @FormDataParam("file") FormDataContentDisposition fileMetaData
//    ) {
//
//        try {
//            ProductModel productModel = new ProductModel();
//            OfferDetails productDetails = new OfferDetails();
//
//            Random random = new Random();
//            String pictureFileName = random.nextInt() + "_" + fileMetaData.getFileName();
//
//            productDetails.offerOption.setPrice(offerPrice);
//            productDetails.offerOption.setAmount(offerAmount);
//            productDetails.offerOption.setCondition("new");
//
//            productDetails.offer.setTitle(itemTitle);
//            productDetails.offer.setDescription(itemDescription);
//            productDetails.offer.setCategoryId(itemCategoryId);
//            productDetails.offer.setPicture(pictureFileName);
//
//            ItemAttributesDetails itemAttributesDetails = new ItemAttributesDetails();
//            itemAttributesDetails.itemAttributeValue.setAttributeId(1);
//            itemAttributesDetails.itemAttributeValue.setValue("test");
//            productDetails.itemAttributesDetails.add(itemAttributesDetails);
//
//            if(getAuthUser() == null) {
//                return Response.status(401).build();
//            }
//
//            if(productModel.addProduct(getAuthUser(), productDetails)) {
//                return Response.status(201).build();
//            }else {
//                return Response.ok().build();
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return Response.status(500).build();
//        }
//
//    }

}
