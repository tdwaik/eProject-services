package com.thaer.jj.controller.eproject;

import com.thaer.jj.entities.Cart;
import com.thaer.jj.exceptions.UnAuthorizedException;
import com.thaer.jj.model.CartModel;
import com.thaer.jj.model.responseData.CartOfferResponse;
import com.thaer.jj.model.responseData.CartPriceSummary;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since June 3, 2016.
 */

@Path("cart")
public class CartController extends MainController {

    @GET
    @Path("/")
    public Response getCart() {
        try {

            if(getAuthBuyer() == null) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            CartModel cartModel = new CartModel();
            ArrayList<CartOfferResponse> cartOffers = cartModel.getCartOffersByBuyerId(getAuthBuyer().getId());
            return Response.ok().entity(toJson(cartOffers)).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("/")
    public Response addToCart(@FormParam("offerStockId") int offerStockId, @FormParam("quantity") int quantity) {
        try {
            if(getAuthBuyer() == null) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            CartModel cartModel = new CartModel();
            cartModel.addToCart(getAuthBuyer().getId(), offerStockId, quantity);
            return Response.status(Response.Status.CREATED).build();

        }catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("/remove/{cartId}")
    public Response deleteFromCart(@PathParam("cartId") int cartId) {
        try {
            if(getAuthBuyer() == null) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            CartModel cartModel = new CartModel();
            cartModel.deleteFromCartByCartId(getAuthBuyer().getId(), cartId);
            return Response.ok().build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (UnAuthorizedException e) {
            e.printStackTrace();
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/orderSummary")
    public Response getOrderSummary() {
        try {
            if(getAuthBuyer() == null) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            CartModel cartModel = new CartModel();
            CartPriceSummary cartPriceSummary = cartModel.getOrderSummary(getAuthBuyer().getId());
            System.out.println(cartPriceSummary.itemsTotalPrice);
            return Response.ok().entity(toJson(cartPriceSummary)).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/updateQuantity")
    public Response updateQuantity(@FormParam("cartId") int cartId, @FormParam("quantity") int quantity) {
        try {
            if(getAuthBuyer() == null) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            CartModel cartModel = new CartModel();
            Cart cart = cartModel.getCartById(cartId);

            if(cart.getBuyerId() != getAuthBuyer().getId()) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            int result = cartModel.updateQuantity(cartId, quantity);
            if(result > 0) {
                return Response.ok().build();
            }else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
