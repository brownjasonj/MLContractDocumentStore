/**
 * 
 */
package com.contractstore.mlcontractdocumentstore.webservices;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import com.contractstore.contractstoreclient.storeservices.DocumentStoreCriterion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author jason
 *
 */
@Path("transaction")
public class StoreTransaction extends StoreDocument<String> {
	private static final String _transactionTemporalCollectionName = "transactions";
	
	final static Logger logger = Logger.getLogger(StoreTransaction.class);

	public StoreTransaction() {
		super(String.class,
				StoreTransaction._transactionTemporalCollectionName);
		
	}
	
	@POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/plain")
	@Override
	public Response createDocument(String document,
			@QueryParam("documentId") String documentId,
			@QueryParam("date") String dateString) {
		return super.createDocument(document, documentId, dateString);
	}

	@POST
	@Path("update")
	@Consumes(MediaType.APPLICATION_JSON)
	//@Produces("text/plain")
	@Override
	public Response updateDocument(String document,
			@QueryParam("documentId") String documentId,
			@QueryParam("date") String dateString) {
		return super.updateDocument(document, documentId, dateString);
	}

	@POST
	@Path("delete")
	@Consumes(MediaType.APPLICATION_JSON)
	//@Produces("text/plain")
	@Override
	public Response deleteDocument(String document,
			@QueryParam("documentId") String documentId,
			@QueryParam("date") String dateString) {
		return super.deleteDocument(document, documentId, dateString);
	}
	
	
	@GET
	@Path("read")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTransactionsAsOfDate(@Context UriInfo uri, @Context HttpHeaders hh) {
		StringWriter soutput = new StringWriter();
		PrintWriter output = new PrintWriter(soutput);
		DocumentStoreCriterion storeCriterion = new DocumentStoreCriterion();
		Date asOfDate = UriParameterParser.parseStoreCriterion(storeCriterion, uri);
		
		logger.info("read date = " + asOfDate);
		logger.info("searching for transactions");
		List<String> transactions = this.getDocuments(storeCriterion, asOfDate);
		logger.info("transactions received :" + transactions);
		output.println(transactions);
		output.flush();
		
		logger.info("return transaction list");				
		return Response.status(200).entity(soutput.toString()).build();
	}
}
