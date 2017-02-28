/**
 * 
 */
package com.contractstore.mlcontractdocumentstore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.contractstore.contractcomponents.Configuration;


/**
 * @author jason
 *
 */
public class DocumentEnvelop {
	public Map<String,Object> document;
	public Date validStart;
	public Date validEnd;
	public Date systemStart;
	public Date systemEnd;
	
	public DocumentEnvelop(Map<String,Object> document, Date validStart) {
		Date endOfTime = new Date();
		try {
			endOfTime = new SimpleDateFormat(Configuration.dateFormat).parse("9999-12-31T22:59:59.999Z");
		}
		catch (ParseException pe) {
		}

		this.document = document;
		this.validStart = validStart;
		this.validEnd = endOfTime;
		this.systemStart = new Date();
		this.systemEnd = endOfTime;
	}


}
