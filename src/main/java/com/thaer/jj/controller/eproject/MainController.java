package com.thaer.jj.controller.eproject;

import com.thaer.jj.core.AbstractController;
import com.thaer.jj.core.JWTAuth;
import com.thaer.jj.entities.Buyer;
import com.thaer.jj.model.BuyerModel;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 10, 2016.
 */
public abstract class MainController extends AbstractController {

    private Buyer authBuyer = null;

    protected void run() {
        try {
            setAuthUser();
        }catch (SQLException e) {
            // do nothing
        }
    }

    public Buyer getAuthBuyer() {
        return authBuyer;
    }

    /**
     *
     * @throws IOException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private void setAuthUser() throws SQLException {
        JWTAuth jwtAuth = new JWTAuth();

        if (jwtAuth.isBuyerAuth(authorization, request.getRemoteAddr())) {
            BuyerModel buyerModel = new BuyerModel();
            authBuyer = buyerModel.getUserById(jwtAuth.getAuthUserId());
        }

    }

}
