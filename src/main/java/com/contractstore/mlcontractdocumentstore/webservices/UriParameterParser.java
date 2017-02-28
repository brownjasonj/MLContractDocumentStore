/**
 * 
 */
package com.contractstore.mlcontractdocumentstore.webservices;

import java.util.HashMap;
import java.util.Map;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import com.contractstore.contractstoreclient.storeservices.IDocumentStoreCriterion;
import com.contractstore.mlcontractdocumentstore.Config;

/**
 * @author jason
 *
 */
public class UriParameterParser {
	public static final String Date = "date";
	
	/**
	 * 
	 */
	public static Date parseStoreCriterion(IDocumentStoreCriterion storeCriterion, UriInfo uri) {
		MultivaluedMap<String, String> queryParams = uri.getQueryParameters();
		Date asOfDate= new Date();
	   
	    for(String paramName : queryParams.keySet()) {
	    	switch (paramName) {
	    	case UriParameterParser.Date:
	    		try {
	    			asOfDate = new SimpleDateFormat(Config.dateFormat).parse(queryParams.getFirst(paramName));
	    		} catch (ParseException pe) {
	    			// if date parse fails, then use the pre-set date as above
	    		}
	    		break;
	    	default:
	    		storeCriterion.addCriterion(paramName, queryParams.getFirst(paramName));
	    		break;
	    	}
	    }
	    
	    return asOfDate;
	}	
}
