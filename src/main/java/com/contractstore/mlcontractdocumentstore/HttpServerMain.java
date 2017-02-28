package com.contractstore.mlcontractdocumentstore;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Main class.
 *
 */
public class HttpServerMain {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:9001/store";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.cs.tradeservice package
        final ResourceConfig rc = new ResourceConfig().packages("com.contractstore.mlcontractdocumentstore");

        final Map<String, Object> initParams = new HashMap<String, Object>();
        //initParams.put("com.sun.jersey.config.property.packages", "rest");
        initParams.put("com.sun.jersey.api.json.POJOMappingFeature", "true");

        rc.addProperties(initParams);
        
        rc.register(HttpCORSFilter.class);
        
        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
        
        return httpServer;
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app available at "
                + "%s\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.shutdown();
    }
}

