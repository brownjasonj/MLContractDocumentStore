/**
 * 
 */
package com.contractstore.bitemporaldocstore;

import static org.junit.Assert.assertEquals;

import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.junit.Test;

import com.contractstore.mlcontractdocumentstore.Config;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.FailedRequestException;
import com.marklogic.client.document.DocumentPage;
import com.marklogic.client.document.JSONDocumentManager;
import com.marklogic.client.io.Format;
import com.marklogic.client.io.StringHandle;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StringQueryDefinition;

/**
 * @author jason
 *
 */
public class MLStandaloneTest {
//	final static Logger logger = Logger.getLogger(MLStandaloneTest.class);
	private final String dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

	private final String _contractTemporalCollectionName = "risks";
	private Gson _gson = new GsonBuilder().setDateFormat(dateFormat).create();
	
	Random randomNumberGenerator = new Random();
	
	private int waitTime = 10;
	
	public MLStandaloneTest() {
	}
	
	
	
	/**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testStoreNewTransaction() {
    	Integer tcn = 7000000;
    	Integer version = 0;
    	
    	try {    			    	    	
    		DatabaseClient client = DatabaseClientFactory.newClient(
    				Config.host,
    				Config.port,
    				Config.user,
    				Config.password,
    				Config.authType);
    		
    		

    		// create a manager for JSON documents
    		JSONDocumentManager docMgr = client.newJSONDocumentManager();
    		QueryManager queryMgr = client.newQueryManager();

    		docMgr.stopLogging();
	    	
	    	DateTime date = new DateTime();
	    	SimpleDateFormat formatDate = new SimpleDateFormat(this.dateFormat);
	    	String dateString = formatDate.format(date.toDate());
	    	String response;

			
//	    	logger.info("create contract id=" + tcn + " version=" + version + " validStart = " + dateString);

	    	this.writeRiskForTrade(docMgr, tcn.toString(), 0, 70000000, dateString);
	    	
//	    	TimeUnit.SECONDS.sleep(this.waitTime);	    	


//	    	StringQueryDefinition query = queryMgr.newStringDefinition().withCriteria(this.id);
//			DocumentPage page = docMgr.search(query, 0);
//			assertEquals("Wrong number of results", 7, page.size());
			
    	}
    	catch (Exception e) {
    		
    	}
    }
    
    public void writeRiskForTrade(JSONDocumentManager docMgr, String tcn, Integer version, Integer resultCount, String startDate) {

    	for(int i = 0; i < resultCount; i++) {
    		Boolean createdResource = true;
    		String documentId = UUID.randomUUID().toString();
    		String riskName = "risk" + i;
    		
    		// Create a handle to hold string content.
    		StringHandle handle = new StringHandle().withFormat(Format.JSON);
    		String document = this.createJsonDocument(tcn, version, riskName, randomNumberGenerator.nextDouble(), startDate);

    		// Give the handle some content
    		handle.set(document);

//    		logger.info("Writing document : " + handle);
    		try {
    			// Write the document to the database with URI from docId
    			// and content from handle into the temporal collection 
    			docMgr.write(documentId, null, handle, null, null, this._contractTemporalCollectionName);
    			
    			
    		}
    		catch (FailedRequestException fre) {
//    			logger.info("exception thrown : " + fre.getMessage());
    			createdResource = false;
    		}
    		finally {
    		}
    		
//    		if (createdResource)
//    			logger.info("Document written");
//    		else
//    			logger.info("Failed to write Document");
    	}
    }

   
	public String createJsonDocument(String tcn, Integer version, String riskName, double riskValue, String startDate) {
		StringWriter documentResult = new StringWriter();
		
		documentResult.write("{ \"tcn\":\"");
		documentResult.write(tcn);
		documentResult.write("\", \"version\":");
		documentResult.write(version.toString());

		documentResult.write(",\"");
		documentResult.write(riskName);
		documentResult.write("\": ");
		documentResult.write(Double.toString(riskValue));
				
		
		documentResult.write(",\"validStart\": \"");
		documentResult.write(startDate);
		documentResult.write("\",\"validEnd\": \"9999-12-31T11:59:59Z\"");
		documentResult.write(",\"systemStart\": null");
		documentResult.write(",\"systemEnd\": null}");
		
		return documentResult.toString();
	}
}

