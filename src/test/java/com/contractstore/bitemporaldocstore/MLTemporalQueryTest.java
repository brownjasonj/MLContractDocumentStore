/**
 * 
 */
package com.contractstore.bitemporaldocstore;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.contractstore.contractcomponents.Configuration;
import com.contractstore.mlcontractdocumentstore.Config;
import com.contractstore.mlcontractdocumentstore.webservices.StoreDocument;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.DocumentPage;
import com.marklogic.client.document.DocumentRecord;
import com.marklogic.client.document.JSONDocumentManager;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StructuredQueryBuilder;
import com.marklogic.client.query.StructuredQueryDefinition;
import com.marklogic.client.query.StructuredQueryBuilder.TemporalOperator;

/**
 * @author jason
 *
 */
public class MLTemporalQueryTest extends StoreDocument<String> {
	final static Logger logger = Logger.getLogger(MLTemporalQueryTest.class);
	
	private static final String _contractTemporalCollectionName = "contracts";
	protected Gson _gson = new GsonBuilder().setDateFormat(Configuration.dateFormat).create();
	
	DatabaseClient client;
	JSONDocumentManager docMgr;
	QueryManager queryMgr;
	
	public MLTemporalQueryTest() {
		super(String.class,
				MLTemporalQueryTest._contractTemporalCollectionName);
	}
	
	@Before
    public void setUp() throws Exception {
		this.client = DatabaseClientFactory.newClient(
				Config.host,
				Config.port,
				Config.user,
				Config.password,
				Config.authType);
		
		

		// create a manager for JSON documents
		this.docMgr = client.newJSONDocumentManager();
		this.queryMgr = client.newQueryManager();
	}
	
	/**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testStoreNewTransaction() {
    	long start = 1;
		StructuredQueryBuilder sqb = queryMgr.newStructuredQueryBuilder();
    	StructuredQueryBuilder.Axis validAxis = sqb.axis("systemAxis");
    	SimpleDateFormat formatDate = new SimpleDateFormat(Config.dateFormat);
		
		// create a time axis to query the versions against
//		String date0Start = formatDate.format("2016-11-09T16:56:55.866Z");
//		String date0End = formatDate.format("2016-11-09T16:56:55.966Z");
//		Calendar start1 = DatatypeConverter.parseDateTime(date0Start);
//		Calendar end1   = DatatypeConverter.parseDateTime(date0End);
    	Calendar start1 = DatatypeConverter.parseDateTime("2016-11-16T05:37:10.600Z");
		Calendar end1   = DatatypeConverter.parseDateTime("2016-11-16T05:37:11.000Z");
		StructuredQueryBuilder.Period period1 = sqb.period(start1, end1);

		StructuredQueryDefinition criteria = 
				sqb.containerQuery(sqb.jsonProperty("id"), sqb.term("4d1f5119-9cb0-4e95-9f74-2f2b68b5d61e"));
				
		// find all documents contained in the time range of our query axis
//		StructuredQueryDefinition periodQuery1 = sqb.and(termsQuery,
//			sqb.temporalPeriodRange(validAxis, TemporalOperator.ALN_CONTAINED_BY, period1));

		StructuredQueryDefinition periodQuery1 = sqb.and(criteria,
				sqb.temporalPeriodRange(validAxis, TemporalOperator.ALN_CONTAINED_BY, period1));
		DocumentPage periodQuery1Results = docMgr.search(periodQuery1, start);
		logger.info("TemporalOperator.ALN_CONTAINED_BY doc count = " + periodQuery1Results.size());

		periodQuery1 = sqb.and(criteria,
				sqb.temporalPeriodRange(validAxis, TemporalOperator.ALN_CONTAINS, period1));
		periodQuery1Results = docMgr.search(periodQuery1, start);
		logger.info("TemporalOperator.ALN_CONTAINS doc count = " + periodQuery1Results.size());

		
		periodQuery1 = sqb.temporalPeriodRange(validAxis, TemporalOperator.ALN_FINISHED_BY, period1);
		periodQuery1Results = docMgr.search(periodQuery1, start);
		logger.info("TemporalOperator.ALN_FINISHED_BY doc count = " + periodQuery1Results.size());

		periodQuery1 = sqb.temporalPeriodRange(validAxis, TemporalOperator.ALN_OVERLAPS, period1);
		periodQuery1Results = docMgr.search(periodQuery1, start);
		logger.info("TemporalOperator.ALN_OVERLAPS doc count = " + periodQuery1Results.size());
		
		periodQuery1 = sqb.temporalPeriodRange(validAxis, TemporalOperator.ALN_OVERLAPPED_BY, period1);
		periodQuery1Results = docMgr.search(periodQuery1, start);
		logger.info("TemporalOperator.ALN_OVERLAPPED_BY doc count = " + periodQuery1Results.size());
		
		DocumentPage apage = docMgr.read("id", "b470a04b-61b9-4269-a51e-517d01387649", null);
		
		for(DocumentRecord record : apage) {
			String recordString = record.toString();
			
			logger.info(recordString);
		}
		
		
		
//		StructuredQueryDefinition criteria = 
//				sqb.containerQuery(sqb.jsonProperty("id"), sqb.term("b470a04b-61b9-4269-a51e-517d01387649"));
//		//StructuredQueryDefinition tempCriteria = sqb.and(periodQuery1, criteria);
//		
//		//StringQueryDefinition newquery = queryMgr.newStringDefinition().withCriteria(contractId.getId());
//		DocumentPage newpage = docMgr.search(criteria, 0);
//		
//		for(DocumentRecord record : newpage) {
//			String recordString = record.toString();
//			
//			logger.info(recordString);
//		}
    }
    
    @After
    public void tearDown() throws Exception {
    	this.client.release();
    }
				
}
