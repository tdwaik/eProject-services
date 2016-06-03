package com.thaer.jj.controller.eproject;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.thaer.jj.entities.Address;
import com.thaer.jj.exceptions.UnAuthorizedException;
import com.thaer.jj.model.AddressModel;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by stig on 5/1/16.
 */
@Path("addresses")
public class AddressController extends MainController {

    @GET
    @Path("/")
    public Response getAddressesByAuthBuyer() {
        try {

            if(getAuthBuyer() == null) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            AddressModel addressModel = new AddressModel();
            ArrayList<Address> addresses = addressModel.getAddressesByBuyerId(getAuthBuyer().getId());

            if(addresses.size() > 0) {
                return Response.ok().entity(toJson(addresses)).build();
            }else {
                return Response.status(Response.Status.NO_CONTENT).build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/{addressId}")
    public Response getAddressById(@PathParam("addressId") int addressId) {
        try {

            if(getAuthBuyer() == null) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            AddressModel addressModel = new AddressModel();
            Address address = addressModel.getAddressById(addressId);

            if(address == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            if(address.getUserId() != getAuthBuyer().getId()) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            return Response.ok().entity(toJson(address)).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("/")
    public Response addAddress(@FormParam("addressJson") String addressJson) {
        try {

            if(getAuthBuyer() == null) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            JsonParser parser = new JsonParser();
            JsonObject addressInfo = parser.parse(addressJson).getAsJsonObject();

            Address address = new Address();

            try {
                address.setFirstname(addressInfo.get("firstname").getAsString());
                address.setLastname(addressInfo.get("lastname").getAsString());
                address.setPhone(addressInfo.get("phone").getAsLong());
                address.setCountryId(addressInfo.get("countryId").getAsInt());
                address.setCityId(addressInfo.get("cityId").getAsInt());
                address.setAddressLine1(addressInfo.get("addressLine1").getAsString());
            }catch (NullPointerException e) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            if(addressInfo.has("addressLine2") && addressInfo.get("addressLine2") != null) {
                address.setAddressLine2(addressInfo.get("addressLine2").getAsString());
            }
            if(addressInfo.has("region") && addressInfo.get("region") != null) {
                address.setRegion(addressInfo.get("region").getAsString());
            }
            if(addressInfo.has("postalCode") && addressInfo.get("postalCode") != null) {
                address.setPostalCode(addressInfo.get("postalCode").getAsString());
            }
            if(addressInfo.has("isPrimary") && addressInfo.get("isPrimary") != null) {
                address.setPrimary(addressInfo.get("isPrimary").getAsBoolean());
            }

            AddressModel addressModel = new AddressModel();

            if(!addressModel.validateAddAddress(address)) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            int newAddressId = addressModel.addAddress(getAuthBuyer(), address);

            if(newAddressId > 0) {
                return Response.status(Response.Status.CREATED).entity(newAddressId).build();
            }else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

        }catch(SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("/{addressId}")
    public Response deleteAddress(@PathParam("addressId") int addressId) {
        try {
            if(getAuthBuyer() == null) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            if(addressId < 1) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            AddressModel addressModel = new AddressModel();
            addressModel.deleteAddressById(getAuthBuyer(), addressId);
            return Response.status(Response.Status.ACCEPTED).build();
        }catch(UnAuthorizedException e) {
            e.printStackTrace();
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }catch(SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

//    @PUT
//    @Path("/")
//    public Response updateAddress(@FormParam("addressJson") String addressJson) {
//        try {
//
//            if(getAuthBuyer() == null) {
//                return Response.status(Response.Status.UNAUTHORIZED).build();
//            }
//
//            JsonParser parser = new JsonParser();
//            JsonObject addressInfo = parser.parse(addressJson).getAsJsonObject();
//
//            Address address = new Address();
//
//            try {
//                address.setFirstname(addressInfo.get("firstname").getAsString());
//                address.setLastname(addressInfo.get("lastname").getAsString());
//                address.setPhone(addressInfo.get("phone").getAsLong());
//                address.setCountryId(addressInfo.get("countryId").getAsInt());
//                address.setCityId(addressInfo.get("cityId").getAsInt());
//                address.setAddressLine1(addressInfo.get("addressLine1").getAsString());
//            }catch (NullPointerException e) {
//                return Response.status(Response.Status.BAD_REQUEST).build();
//            }
//
//            if(addressInfo.has("addressLine2") && addressInfo.get("addressLine2") != null) {
//                address.setAddressLine2(addressInfo.get("addressLine2").getAsString());
//            }
//            if(addressInfo.has("region") && addressInfo.get("region") != null) {
//                address.setRegion(addressInfo.get("region").getAsString());
//            }
//            if(addressInfo.has("postalCode") && addressInfo.get("postalCode") != null) {
//                address.setPostalCode(addressInfo.get("postalCode").getAsString());
//            }
//            if(addressInfo.has("isPrimary") && addressInfo.get("isPrimary") != null) {
//                address.setPrimary(addressInfo.get("isPrimary").getAsBoolean());
//            }
//
//            AddressModel addressModel = new AddressModel();
//
//            if(!addressModel.validateAddAddress(address)) {
//                return Response.status(Response.Status.BAD_REQUEST).build();
//            }
//
//            int newAddressId = addressModel.addAddress(getAuthBuyer(), address);
//
//            if(newAddressId > 0) {
//                return Response.status(Response.Status.CREATED).entity(newAddressId).build();
//            }else {
//                return Response.status(Response.Status.BAD_REQUEST).build();
//            }
//
//        }catch(SQLException e) {
//            e.printStackTrace();
//            return Response.status(Response.Status.BAD_REQUEST).build();
//        }
//    }
}
