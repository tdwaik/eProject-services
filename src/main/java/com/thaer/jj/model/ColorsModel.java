package com.thaer.jj.model;

import com.thaer.jj.core.utils.Strings;
import com.thaer.jj.entities.Color;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since Apr 08, 2016.
 */
public class ColorsModel extends AbstractModel {
    public ColorsModel() throws SQLException {
    }

    public ArrayList<Color> getAllColors() throws SQLException {
        ResultSet resultSet = executeQuery("SELECT * FROM colors");
        return fillData(resultSet);
    }

    public HashMap<Integer, Color> getColorsIn(ArrayList<Integer> ids) throws SQLException {
        String idsStr = Strings.implode(", ", ids);
        ResultSet resultSet = executeQuery("SELECT * FROM colors WHERE id IN (" + idsStr + ")");

        HashMap<Integer, Color> colorsMap = new HashMap<>();
        for(Color color :  fillData(resultSet)) {
            colorsMap.put(color.getId(), color);
        }

        return colorsMap;
    }

    public Color getColorById(int color_id) throws SQLException, IllegalArgumentException {
        if(color_id < 1) {
            throw new IllegalArgumentException();
        }

        ResultSet resultSet = executeQuery("SELECT * FROM colors WHERE id = " + color_id);
        ArrayList<Color> colors = fillData(resultSet);
        return colors.size() == 1? colors.get(0) : null;
    }

    public ArrayList<Color> fillData(ResultSet resultSet) throws SQLException {
        ArrayList<Color> colors = new ArrayList<>();
        Color color;

        while(resultSet.next()) {
            color = new Color();
            color.setId(resultSet.getInt("id"));
            color.setName(resultSet.getString("name"));
            color.setHex(resultSet.getString("hex"));
            colors.add(color);
        }

        return colors;
    }
}
