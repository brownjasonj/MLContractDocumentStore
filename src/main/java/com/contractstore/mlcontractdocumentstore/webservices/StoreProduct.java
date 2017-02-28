/**
 * 
 */
package com.contractstore.mlcontractdocumentstore.webservices;

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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.contractstore.contractstoreclient.storeservices.DocumentStoreCriterion;

/**
 * @author jason
 *
 */
@Path("product")
public class StoreProduct extends StoreDocument<String> {
	private static final String _productTemporalCollectionName = "products";
	
	/**
	 * @param documentClass
	 * @param index
	 * @param documentType
	 * @param documentHistoryType
	 * @param documentQueryCacheType
	 */
	public StoreProduct() {
		super(String.class,
				StoreProduct._productTemporalCollectionName);
	}

	@POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/plain")
	public Response createDocument(String document,
									@QueryParam("documentId") String documentId,
									@QueryParam("date") String dateString) {
		return super.createDocument(document, documentId, dateString);
	}
	
	@POST
	@Path("update")
	@Consumes(MediaType.APPLICATION_JSON)
	//@Produces("text/plain")
	public Response updateDocument(String document,
									@QueryParam("documentId") String documentId,
									@QueryParam("date") String dateString) {
		return super.updateDocument(document, documentId, dateString);
	}
	
	@POST
	@Path("delete")
	@Consumes(MediaType.APPLICATION_JSON)
	//@Produces("text/plain")
	public Response deleteDocument(String document,
			@QueryParam("documentId") String documentId,
			@QueryParam("date") String dateString) {
		return super.deleteDocument(document, documentId, dateString);
	}
	
	@GET
	@Path("read")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProducts(@Context UriInfo url, @Context HttpHeaders hh) {
		StringWriter soutput = new StringWriter();
		PrintWriter output = new PrintWriter(soutput);
		DocumentStoreCriterion storeCriterion = new DocumentStoreCriterion();
		Date asOfDate = UriParameterParser.parseStoreCriterion(storeCriterion, url);
		List<String> contracts =  this.getDocuments(storeCriterion, asOfDate);
		output.println(contracts);
		output.flush();
		return Response.status(200).entity(soutput.toString()).build();	
	}

}
