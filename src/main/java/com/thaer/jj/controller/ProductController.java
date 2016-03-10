package com.thaer.jj.controller;

import com.thaer.jj.model.sets.ProductDetails;
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
@Path("products")
public class ProductController extends MainController {

    private OfferModel offerModel;

    public ProductController() throws SQLException {
        offerModel = new OfferModel();
    }

    @GET
    @Path("/list/{order}/{from}/{to}")
    public Response getProductsList(@PathParam("order") String order, @PathParam("from") int from, @PathParam("to") int to) {
        try {
            ArrayList<ProductDetails> productDetailsList = offerModel.getProductsList(order, from , to);

            return Response.ok().entity(toJson(productDetailsList)).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }

    }

    @GET
    @Path("/{offerId}")
    @Produces(MediaType.APPLICATION_JSON)
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
        }

    }

    @POST @Path("/add")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response addProduct(
            @FormDataParam("offerPrice") int offerPrice,
            @FormDataParam("offerAmount") int offerAmount,
            @FormDataParam("itemTitle") String itemTitle,
            @FormDataParam("itemDescription") String itemDescription,
            @FormDataParam("itemCategoryId") int itemCategoryId,
            @FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition fileMetaData
    ) {

        try {
            ProductModel productModel = new ProductModel();
            ProductDetails productDetails = new ProductDetails();

            Random random = new Random();
            String pictureFileName = random.nextInt() + "_" + fileMetaData.getFileName();

            productDetails.offerOption.setPrice(offerPrice);
            productDetails.offerOption.setAmount(offerAmount);
            productDetails.offerOption.setCondition("new");

            productDetails.offer.setTitle(itemTitle);
            productDetails.offer.setDescription(itemDescription);
            productDetails.offer.setCategoryId(itemCategoryId);
            productDetails.offer.setPicture(pictureFileName);

            ItemAttributesDetails itemAttributesDetails = new ItemAttributesDetails();
            itemAttributesDetails.itemAttributeValue.setAttributeId(1);
            itemAttributesDetails.itemAttributeValue.setValue("test");
            productDetails.itemAttributesDetails.add(itemAttributesDetails);

            if(getAuthUser() == null) {
                return Response.status(401).build();
            }

            if(productModel.addProduct(getAuthUser(), productDetails)) {
                return Response.status(201).build();
            }else {
                return Response.ok().build();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }

    }

}
