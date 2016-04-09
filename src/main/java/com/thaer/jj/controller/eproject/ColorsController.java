package com.thaer.jj.controller.eproject;

import com.thaer.jj.entities.Color;
import com.thaer.jj.model.ColorsModel;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since Apr 09, 2016.
 */
@Path("colors")
public class ColorsController extends MainController {

    @GET
    @Path("/")
    public Response getAllColors() {
        try {
            ColorsModel colorsModel = new ColorsModel();
            ArrayList<Color> colors = colorsModel.getAllColors();
            return Response.ok().entity(toJson(colors)).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }
    }
}
