/**
 * 
 */
package com.contractstore.mlcontractdocumentstore.webservices;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author jason
 *
 */
@Path("event")
public class StoreEvent extends StoreDocument<String> {
	private static final String _eventTemporalCollectionName = "events";

	public StoreEvent() {
		super(String.class,
				StoreEvent._eventTemporalCollectionName);
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

}
