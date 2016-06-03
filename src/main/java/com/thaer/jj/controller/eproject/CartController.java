package com.thaer.jj.controller.eproject;

import com.thaer.jj.entities.Cart;
import com.thaer.jj.model.CartModel;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
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
            ArrayList<Cart> cartOffers = cartModel.getCartByBuyerId(getAuthBuyer().getId());
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
}
