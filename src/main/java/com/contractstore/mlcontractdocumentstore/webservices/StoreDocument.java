/**
 * 
 */
package com.contractstore.mlcontractdocumentstore.webservices;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.joda.time.DateTime;

import com.contractstore.contractcomponents.Configuration;
import com.contractstore.contractstoreclient.storeservices.IDocumentStoreCriterion;
import com.contractstore.mlcontractdocumentstore.Config;
import com.contractstore.mlcontractdocumentstore.DocumentEnvelop;
import com.contractstore.mlcontractdocumentstore.StringDocumentEnvelop;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.FailedRequestException;
import com.marklogic.client.document.DocumentPage;
import com.marklogic.client.document.DocumentRecord;
import com.marklogic.client.document.JSONDocumentManager;
import com.marklogic.client.io.Format;
import com.marklogic.client.io.StringHandle;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StructuredQueryBuilder;
import com.marklogic.client.query.StructuredQueryBuilder.TemporalOperator;
import com.marklogic.client.query.StructuredQueryDefinition;

/**
 * @author jason
 *
 */
public abstract class StoreDocument <T> {	
	protected String temporalCollectionName;
	protected Class<T> documentClass;
	
	protected Gson _gson = new GsonBuilder().setDateFormat(Configuration.dateFormat).create();

		
	/**
	 * 
	 */
	public StoreDocument(Class<T> documentClass,
			String temporalCollectionName) {
		this.documentClass = documentClass;
		this.temporalCollectionName = temporalCollectionName;
	}
	
	/**
	 * @param document
	 * @param documentId
	 * @param dateString
	 * @return
	 */
	public Response createDocument(String document,
			String documentId,
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

    	String docEnvelop = StringDocumentEnvelop.createJson(document, asOfDate);

		// Give the handle some content
		handle.set(docEnvelop);

		try {
			// Write the document to the database with URI from docId
			// and content from handle into the temporal collection 
			docMgr.write(documentId, null, handle, null, null, this.temporalCollectionName);
			
			
		}
		catch (FailedRequestException fre) {
			createdResource = false;
		}
		finally {
		}


		// release the client
		client.release();			

		if (createdResource)
			return Response.status(200).entity(document).build();
		else
			return Response.status(200).entity("failed").build();
	}
	
	/**
	 * @param document
	 * @param documentId
	 * @param dateString
	 * @return
	 */
	public Response updateDocument(String document,
			String documentId,
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

    	String docEnvelop = StringDocumentEnvelop.createJson(document, asOfDate);

		// Give the handle some content
		handle.set(docEnvelop);

		
		try {
			// Write the document to the database with URI from docId
			// and content from handle
			// docMgr.delete(documentId, null, this.temporalCollectionName);
			docMgr.write(documentId, null, handle, null, null, this.temporalCollectionName);
		}
		catch (FailedRequestException fre) {
			createdResource = false;
		}
		finally {
		}

		// release the client
		client.release();			

		if (createdResource)
			return Response.status(200).entity(document).build();
		else
			return Response.status(200).entity("failed").build();
	}
	
	/**
	 * @param document
	 * @param documentId
	 * @param dateString
	 * @return
	 */
	public Response deleteDocument(String document,
			String documentId,
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

    	String docEnvelop = StringDocumentEnvelop.createJson(document, asOfDate);

		// Give the handle some content
		handle.set(docEnvelop);

		
		try {
			// Write the document to the database with URI from docId
			// and content from handle
			//docMgr.write(documentId, null, handle, null, null, this.temporalCollectionName);
			docMgr.delete(documentId, null, this.temporalCollectionName);
		}
		catch (FailedRequestException fre) {
			createdResource = false;
		}
		finally {
		}

		// release the client
		client.release();			

		if (createdResource)
			return Response.status(200).entity(document).build();
		else
			return Response.status(200).entity("failed").build();
	}
	
	
	/**
	 * @param documentCriteria
	 * @param asofDate
	 * @return
	 */
	public List<T> getDocuments(IDocumentStoreCriterion storeCriterion, Date date) {
		long start = 1;
		
		// create the client
		DatabaseClient client = DatabaseClientFactory.newClient(Config.host, Config.port, Config.user, Config.password, Config.authType);

		// create a manager for JSON documents
		JSONDocumentManager docMgr = client.newJSONDocumentManager();
		
		// create a manager for searching
		QueryManager queryMgr = client.newQueryManager();

		StructuredQueryBuilder sqb = queryMgr.newStructuredQueryBuilder();
		
		// create a time axis to query the versions against
		Calendar start1 = Calendar.getInstance();
		start1.setTime(date);
		DateTime date0 = new DateTime(date);
    	DateTime date1 = date0.plusMillis(1);
		Calendar end1   = Calendar.getInstance();
		end1.setTime(date1.toDate());
		
		StructuredQueryBuilder.Axis validAxis = sqb.axis("systemAxis");
		
		StructuredQueryBuilder.Period period1 = sqb.period(start1, end1);

		// TODO: need to convert the storecrtierion into a correct StructuredQueryDefinition!!!!
		StructuredQueryDefinition termsQuery = sqb.valueConstraint("industry", "Real Estate");
		
		// find all documents contained in the time range of our query axis
		StructuredQueryDefinition periodQuery1 = sqb.and(termsQuery,
			sqb.temporalPeriodRange(validAxis, TemporalOperator.ALN_CONTAINS, period1));
		
		DocumentPage periodQuery1Results = docMgr.search(periodQuery1, start);
		
		List<T> result = new ArrayList<T>();
		for (DocumentRecord record : periodQuery1Results) {
			T doc = record.getContentAs(this.documentClass);
			result.add(doc);
		}
		
		return result;
	}
	
	/**
	 * @param documentCriteria
	 * @return
	 */
	public List<T> getDocuments(IDocumentStoreCriterion storeCriterion) {
		return this.getDocuments(storeCriterion, new Date());
	}
}
