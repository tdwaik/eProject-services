package com.thaer.jj.controller.backoffice;

import com.thaer.jj.core.AbstractController;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 28, 2016.
 */
public abstract class BackOfficeController extends AbstractController {

    protected String getENV() {
        return "backoffice";
    }

}
