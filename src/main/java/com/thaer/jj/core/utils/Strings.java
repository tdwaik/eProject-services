package com.thaer.jj.core.utils;

import java.util.List;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since Apr 03, 2016.
 */
public class Strings {

    public static String implode(String glue, List pieces) {

        if(pieces.size() == 0) {
            return null;
        }

        int piecesSize = pieces.size();
        String str = "";

        for(int i = 0; i < pieces.size();) {
            str += pieces.get(i++);

            if(i < piecesSize) {
                str += glue;
            }
        }

        return str;

    }

}
