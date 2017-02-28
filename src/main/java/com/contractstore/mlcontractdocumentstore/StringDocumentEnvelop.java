/**
 * 
 */
package com.contractstore.mlcontractdocumentstore;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author jason
 *
 */
public class StringDocumentEnvelop {
	
	public static String createJson(String document, Date startDate) {
		SimpleDateFormat formatDate = new SimpleDateFormat(Config.dateFormat);
		
		String documentResult = "{ \"document\":"
			+ document
			+ ","
			+ "\"validStart\": \"" + formatDate.format(startDate) + "\","
			+ "\"validEnd\": \"9999-12-31T11:59:59Z\","
			+ "\"systemStart\": null,"
			+ "\"systemEnd\": null}";
		
		return documentResult;
	}
}
