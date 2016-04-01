package com.thaer.jj.controller.sellers;

import com.thaer.jj.core.AbstractController;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since Mar 31, 2016.
 */
public class SellersController extends AbstractController {

    protected String getENV() {
        return "sellers";
    }

    public SellersController() {
        super();

//        if(getAuthUser() == null) {
//            System.exit(1);
//        }
    }
}
