/**
 * 
 */
package com.contractstore.mlcontractdocumentstore.webservices;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

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

import com.contractstore.contractcomponents.Lifecycles;
import com.contractstore.contractstoreclient.storeservices.DocumentStoreCriterion;

/**
 * @author jason
 *
 */
@Path("lifecycle")
public class StoreLifecycle extends StoreDocument<Lifecycles> {
	private static final String _lifecycleTemporalCollectionName = "lifecycles";
	
	/**
	 * @param documentClass
	 * @param index
	 * @param documentType
	 * @param documentHistoryType
	 * @param documentQueryCacheType
	 */
	public StoreLifecycle() {
		super(Lifecycles.class,
				StoreLifecycle._lifecycleTemporalCollectionName);
	}
	
	/* (non-Javadoc)
	 * @see com.contractstore.contractdocumentstore.webservices.StoreDocument#createDocument(java.lang.String, java.lang.String, java.lang.String)
	 */
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

	/* (non-Javadoc)
	 * @see com.contractstore.contractdocumentstore.webservices.StoreDocument#updateDocument(java.lang.String, java.lang.String, java.lang.String)
	 */
	@POST
	@Path("update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/plain")
	@Override
	public Response updateDocument(String document,
			@QueryParam("documentId") String documentId,
			@QueryParam("date") String dateString) {
		return super.updateDocument(document, documentId, dateString);
	}

	/* (non-Javadoc)
	 * @see com.contractstore.contractdocumentstore.webservices.StoreDocument#deleteDocument(java.lang.String, java.lang.String, java.lang.String)
	 */
	@POST
	@Path("delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/plain")
	@Override
	public Response deleteDocument(String document,
			@QueryParam("documentId") String documentId,
			@QueryParam("date") String dateString) {
		return super.deleteDocument(document, documentId, dateString);
	}

	@GET
	@Path("read")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDocuments(@Context UriInfo uri, @Context HttpHeaders hh) {
		StringWriter soutput = new StringWriter();
		PrintWriter output = new PrintWriter(soutput);
		DocumentStoreCriterion storeCriterion = new DocumentStoreCriterion();
		Date asOfDate = UriParameterParser.parseStoreCriterion(storeCriterion, uri);
		List<Lifecycles> lifecycles = this.getDocuments(storeCriterion, asOfDate);
		output.println(lifecycles);
		output.flush();
		return Response.status(200).entity(soutput.toString()).build();	
	}
}
