/**
 * 
 */
package com.contractstore.bitemporaldocstore;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.elasticsearch.client.transport.TransportClient;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;

import com.contractstore.contractcomponents.Contract;
import com.contractstore.contractcomponents.Product;
import com.contractstore.contractcomponents.Transaction;
import com.contractstore.mlcontractdocumentstore.HttpServerMain;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author jason
 *
 */
/**
 * @author jason
 *
 */
public abstract class BaseHttpTest {
    protected HttpServer server;
    protected WebTarget target;
    protected HttpServerThread hst;
    protected Gson _gson;

    @Before
    public void setUp() throws Exception {
    	
    	this._gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
    	
        // start the server
        //server = HttpServerMain.startServer();
		hst = new HttpServerThread();
		hst.run();

		// create the client
        Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(HttpServerMain.BASE_URI);
        
    }

    @After
    public void tearDown() throws Exception {
    	this.hst.destroy();
    	//server.shutdown();
    }
    
	/**
	 * @param id
	 * @param version
	 * @return
	 */
	public String generateTransactionId(Integer id, Integer version) {
		return Integer.toString(id) + "v" + version;
	}
	
	/**
	 * @param crud
	 * @param transaction
	 * @return
	 * @throws IOException
	 */
	public int postTransaction(String crud, Transaction transaction, String rootDocumentId, Date validFrom) throws IOException {
    	String url=HttpServerMain.BASE_URI + "/transaction/" + crud + "?date=" + validFrom + "&documentId="+rootDocumentId;
    	URL object=new URL(url);
    	
    	HttpURLConnection con = (HttpURLConnection) object.openConnection();
    	con.setDoOutput(true);
    	con.setRequestProperty("Content-Type", MediaType.APPLICATION_JSON);
    	con.setRequestMethod("POST");
    	con.connect();
    	    	
    	    	
    	OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
    	wr.write(this._gson.toJson(transaction));
    	wr.flush();
    	wr.close();	    	
    	int HttpResult = con.getResponseCode(); 
    	con.disconnect();
    	
    	return HttpResult;
	}


	/**
	 * @param crud
	 * @param contract
	 * @return
	 * @throws IOException
	 */
	public int postContract(String crud, Contract contract, String rootDocumentId, Date validFrom) throws IOException {
    	String url=HttpServerMain.BASE_URI + "/product/" + crud + "?date=" + validFrom + "&documentId="+rootDocumentId;
    	URL object=new URL(url);
    	
    	HttpURLConnection con = (HttpURLConnection) object.openConnection();
    	con.setDoOutput(true);
    	con.setRequestProperty("Content-Type", MediaType.APPLICATION_JSON);
    	con.setRequestMethod("POST");
    	con.connect();
    	    	
    	    	
    	OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
    	wr.write(this._gson.toJson(contract));
    	wr.flush();
    	wr.close();	    	
    	int HttpResult = con.getResponseCode(); 
    	con.disconnect();
    	
    	return HttpResult;
	}

}
