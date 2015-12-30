package de.ulfbiallas.imagemanager;

import javax.servlet.MultipartConfigElement;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class Main {

    public static void main(String[] args) throws Exception {
        new Main();
    }

    public Main() throws Exception {

        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.setConfigLocation("de.ulfbiallas.imagemanager.Config");

        DispatcherServlet dispatcherServlet = new DispatcherServlet(applicationContext);
        ServletHolder servletHolder = new ServletHolder(dispatcherServlet);

        String location = "/tmp";
        long maxFileSize = 10 * 1024 * 1024;
        long maxRequestSize = 10 * 1024 * 1024;
        int fileSizeThreshold = 1 * 1024 * 1024;
        servletHolder.getRegistration().setMultipartConfig(new MultipartConfigElement(location, maxFileSize, maxRequestSize, fileSizeThreshold));

        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.addServlet(servletHolder, "/api/*");
        servletContextHandler.addEventListener(new ContextLoaderListener(applicationContext));

        int port = 8080;
        Server server = new Server(port);
        server.setHandler(servletContextHandler);
        server.start();
    }

}
