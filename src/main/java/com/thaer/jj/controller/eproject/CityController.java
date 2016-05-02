package com.thaer.jj.controller.eproject;

import com.thaer.jj.entities.City;
import com.thaer.jj.model.CityModel;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by stig on 5/1/16.
 */
@Path("cities")
public class CityController extends MainController {

    @GET
    @Path("/citiesByCountry/{countryId}")
    public Response getCitiesListByCountryId(@PathParam("countryId") int countryId) {
        try {
            if(countryId < 1) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            CityModel cityModel = new CityModel();
            ArrayList<City> cities = cityModel.getCitiesListByCountryId(countryId);

            return Response.ok().entity(toJson(cities)).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
