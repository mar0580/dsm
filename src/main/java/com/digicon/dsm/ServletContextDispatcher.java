package com.digicon.dsm;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServletContextDispatcher implements ServletContextListener {

    private static ServletContext servletContext;

    public void contextDestroyed(ServletContextEvent event) {
        servletContext = null;
    }

    public void contextInitialized(ServletContextEvent event) {
        servletContext = event.getServletContext();
    }

    public static ServletContext getServletContext() {
        return servletContext;
    }

}