package com.thaer.jj.controller.sellers;

import com.thaer.jj.core.AbstractController;
import com.thaer.jj.core.JWTAuth;
import com.thaer.jj.entities.Seller;
import com.thaer.jj.model.SellerModel;

import java.sql.SQLException;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since Mar 31, 2016.
 */
public class SellersBaseController extends AbstractController {

    private Seller authSeller = null;

    protected void run() {

        try {
            setSeller();
        }catch (SQLException e) {
            System.exit(-1);
        }

        if(authSeller == null) {
            System.exit(1);
        }
    }

    public Seller getAuthSeller() {
        return authSeller;
    }

    private void setSeller() throws SQLException {
        JWTAuth jwtAuth = new JWTAuth();

        if (jwtAuth.isSellerUserAuth(authorization, request.getRemoteAddr())) {
            SellerModel sellerModel = new SellerModel();
            authSeller = sellerModel.getSellerById(jwtAuth.getAuthUserId());
        }

    }
}
