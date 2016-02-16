//package com.thaer.jj.core;
//
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//import javax.servlet.SessionCookieConfig;
//
///**
// * Created by thaer on 2/16/16.
// */
//public class SessionManagement implements ServletContextListener {
//
//    public void contextInitialized(ServletContextEvent sce) {
//        String comment = "This is my special cookie configuration";
//        String domain = "eproject.com";
//        String path = "/";
//        boolean isSecure = true;
//        boolean httpOnly = false;
//        int maxAge = 30000;
//        String cookieName = "FOO_SESSION";
//
//
//        SessionCookieConfig scf = sce.getServletContext().getSessionCookieConfig();
//
//        scf.setComment(comment);
//        scf.setDomain(domain);
//        scf.setHttpOnly(httpOnly);
//        scf.setMaxAge(maxAge);
//        scf.setPath(path);
//        scf.setSecure(isSecure);
//        scf.setName(cookieName);
//    }
//
//    public void contextDestroyed(ServletContextEvent sce) {
//
//    }
//}
