package com.thaer.jj.controller.eproject;

import com.thaer.jj.entities.Country;
import com.thaer.jj.model.CountryModel;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by stig on 4/30/16.
 */
@Path("countries")
public class CountryController extends MainController {

    @GET
    @Path("/")
    public Response getCountriesList() {
        try {
            CountryModel countryModel = new CountryModel();
            ArrayList<Country> countries = countryModel.getCountriesList();

            return Response.ok().entity(toJson(countries)).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }
    }
}
