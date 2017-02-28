/**
 * 
 */
package com.contractstore.bitemporaldocstore;

import static org.junit.Assert.assertEquals;

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
	final static Logger logger = Logger.getLogger(MLStandaloneTest.class);
	private final String dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

	private final String _contractTemporalCollectionName = "contracts";
	private Gson _gson = new GsonBuilder().setDateFormat(dateFormat).create();
	
	private String id = UUID.randomUUID().toString();
	private Integer version = 0;
	
	private int waitTime = 10;
	
	public MLStandaloneTest() {
	}
	
	
	
	/**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testStoreNewTransaction() {
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
	    	Random integerGenerator = new Random();
	    	
	    	DateTime date0 = new DateTime();
	    	date0 = date0.minusDays(2);
	    	DateTime date1 = date0.plusMinutes(integerGenerator.nextInt(30));
	    	DateTime date2 = date1.plusMinutes(integerGenerator.nextInt(30));
	    	DateTime date3 = date2.plusMinutes(integerGenerator.nextInt(30));
	    	
	    	SimpleDateFormat formatDate = new SimpleDateFormat(this.dateFormat);
	    	String response;
	    	
	    	logger.info("create contract id=" + this.id + " version=" + this.version + " validStart = " + formatDate.format(date0.toDate()));
	    	response = this.createDocument(this.id, this.version, formatDate.format(date0.toDate()));	    	
	    	logger.info("Response = " + response);
	    	TimeUnit.SECONDS.sleep(this.waitTime);	    	
	    	this.version++;

	    	logger.info("update contract id=" + this.id + " version=" + this.version + " validStart = " + formatDate.format(date1.toDate()));
	    	response = this.updateDocument(this.id, this.version, formatDate.format(date1.toDate()));
	    	logger.info("Response = " + response);
	    	TimeUnit.SECONDS.sleep(this.waitTime);
	    	this.version++;

	    	logger.info("update contract id=" + this.id + " version=" + this.version  + " validStart = " + formatDate.format(date2.toDate()));
	    	response = this.updateDocument(this.id, this.version, formatDate.format(date2.toDate()));	    	
	    	logger.info("Response = " + response);
	    	TimeUnit.SECONDS.sleep(this.waitTime);
	    	this.version++;

	    	logger.info("update contract id=" + this.id + " version=" + this.version + " validStart = " + formatDate.format(date3.toDate()));
	    	response = this.updateDocument(this.id, this.version, formatDate.format(date3.toDate()));	    	
	    	logger.info("Response = " + response);
	    	TimeUnit.SECONDS.sleep(this.waitTime);
	    	this.version++;


	    	StringQueryDefinition query = queryMgr.newStringDefinition().withCriteria(this.id);
			DocumentPage page = docMgr.search(query, 0);
			assertEquals("Wrong number of results", 7, page.size());
			
    	}
    	catch (Exception e) {
    		
    	}
    }

    public String createDocument(
			String documentId,
			Integer documentVersion,
			String dateString) {
		Date asOfDate = new Date();
		Boolean createdResource = true;
		try {
			asOfDate = new SimpleDateFormat(Config.dateFormat).parse(dateString);
		} catch (ParseException pe) {
			// if date parse fails, then use the pre-set date as above
		}
		
		DatabaseClient client = DatabaseClientFactory.newClient(
				Config.host,
				Config.port,
				Config.user,
				Config.password,
				Config.authType);
		
		

		// create a manager for JSON documents
		JSONDocumentManager docMgr = client.newJSONDocumentManager();
		
				
		// Create a handle to hold string content.
		StringHandle handle = new StringHandle().withFormat(Format.JSON);

    	String docEnvelop = this.createJsonDocument(documentId, documentVersion, asOfDate);

		// Give the handle some content
		handle.set(docEnvelop);

		logger.info("Writing document : " + docEnvelop);
		try {
			// Write the document to the database with URI from docId
			// and content from handle into the temporal collection 
			docMgr.write(documentId, null, handle, null, null, this._contractTemporalCollectionName);
			
			
		}
		catch (FailedRequestException fre) {
			createdResource = false;
		}
		finally {
		}


		// release the client
		client.release();			

		if (createdResource)
			return docEnvelop;
		else
			return "failed";
	}
	
	/**
	 * @param document
	 * @param documentId
	 * @param dateString
	 * @return
	 */
	public String updateDocument(
			String documentId,
			Integer documentVersion,
			String dateString) {
		Date asOfDate = new Date();
		Boolean createdResource = true;

		try {
			asOfDate = new SimpleDateFormat(Config.dateFormat).parse(dateString);
		} catch (ParseException pe) {
			// if date parse fails, then use the pre-set date as above
		}
		
		DatabaseClient client = DatabaseClientFactory.newClient(
				Config.host,
				Config.port,
				Config.user,
				Config.password,
				Config.authType);

		// create a manager for JSON documents
		JSONDocumentManager docMgr = client.newJSONDocumentManager();
		
		
		// Create a handle to hold string content.
		StringHandle handle = new StringHandle().withFormat(Format.JSON);

    	String docEnvelop = this.createJsonDocument(documentId, documentVersion, asOfDate);

		// Give the handle some content
		handle.set(docEnvelop);


		logger.info("Creating document : " + docEnvelop);
		try {
			// Write the document to the database with URI from docId
			// and content from handle
			// docMgr.delete(documentId, null, this.temporalCollectionName);
			docMgr.write(documentId, null, handle, null, null, this._contractTemporalCollectionName);
		}
		catch (FailedRequestException fre) {
			createdResource = false;
		}
		finally {
		}

		// release the client
		client.release();			

		if (createdResource)
			return docEnvelop;
		else
			return "failed";
	}
	
	
	public String createJsonDocument(String id, Integer version, Date startDate) {
		SimpleDateFormat formatDate = new SimpleDateFormat(Config.dateFormat);
		
		String documentResult = "{ \"id\":"
			+ "\"" + id + "\","
			+ "\"version\": " + version 
			+ ","
			+ "\"validStart\": \"" + formatDate.format(startDate) + "\","
			+ "\"validEnd\": \"9999-12-31T11:59:59Z\","
			+ "\"systemStart\": null,"
			+ "\"systemEnd\": null}";
		
		return documentResult;
	}
}
