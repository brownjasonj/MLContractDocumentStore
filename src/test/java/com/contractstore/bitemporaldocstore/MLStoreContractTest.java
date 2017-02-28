/**
 * 
 */
package com.contractstore.bitemporaldocstore;

import static org.junit.Assert.assertEquals;

import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.contractstore.contractcomponents.Configuration;
import com.contractstore.contractcomponents.Contract;
import com.contractstore.contractcomponents.ID;
import com.contractstore.mlcontractdocumentstore.Config;
import com.contractstore.mlcontractdocumentstore.DocumentEnvelop;
import com.contractstore.mlcontractdocumentstore.StringDocumentEnvelop;
import com.contractstore.mlcontractdocumentstore.webservices.StoreContract;
import com.contractstore.mlcontractdocumentstore.webservices.StoreDocument;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.DocumentPage;
import com.marklogic.client.document.DocumentRecord;
import com.marklogic.client.document.JSONDocumentManager;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StringQueryDefinition;
import com.marklogic.client.query.StructuredQueryBuilder;
import com.marklogic.client.query.StructuredQueryBuilder.TemporalOperator;
import com.marklogic.client.query.StructuredQueryDefinition;

/**
 * @author jason
 *
 */
public class MLStoreContractTest extends StoreDocument<String> {
	final static Logger logger = Logger.getLogger(MLStoreContractTest.class);
	
	
	private static final String _contractTemporalCollectionName = "contracts";
	protected Gson _gson = new GsonBuilder().setDateFormat(Configuration.dateFormat).create();
	
	public MLStoreContractTest() {
		super(String.class,
				MLStoreContractTest._contractTemporalCollectionName);
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

	    	String contractType = "FXOption";
	    	String counterParty1 = "Ctpy1";
	    	String counterParty2 = "Ctpy2";
	    	String buySell = "buy";
	    	String statusOpen = "open";
	    	Date expiryDate = new Date();
	    	
	    	Contract contract0 = new Contract(
	    			contractType,
	    			counterParty1,
	    			counterParty2,
	    			buySell,
	    			statusOpen,
	    			expiryDate);
	    	
	    	// get the GUID id of the contract, this is used as the uri 
	    	String contractId = contract0.getId().getId();
	    	
	    	DateTime date0 = new DateTime();
	    	date0 = date0.minusDays(2);
	    	DateTime date1 = date0.plusMinutes(integerGenerator.nextInt(30));
	    	DateTime date2 = date1.plusMinutes(integerGenerator.nextInt(30));
	    	DateTime date3 = date2.plusMinutes(integerGenerator.nextInt(30));
	    	
	    	SimpleDateFormat formatDate = new SimpleDateFormat(Config.dateFormat);
	    	Contract contract1 = new Contract(contract0, statusOpen);
	    	Contract contract2 = new Contract(contract1, statusOpen);
	    	Contract contract3 = new Contract(contract2, statusOpen);
	    	
	    	logger.info("create contract id=" + contractId + " version=" + contract0.getId().getVersion() + " validStart = " + formatDate.format(date0.toDate()));
	    	this.createDocument(this._gson.toJson(contract0), contractId, formatDate.format(date0.toDate()));	    	
	    	TimeUnit.SECONDS.sleep(30);

	    	logger.info("update contract id=" + contract1.getId().getId() + " version=" + contract1.getId().getVersion() + " validStart = " + formatDate.format(date1.toDate()));
	    	this.updateDocument(this._gson.toJson(contract1), contractId, formatDate.format(date1.toDate()));	    	
	    	TimeUnit.SECONDS.sleep(30);

	    	logger.info("update contract id=" + contract2.getId().getId() + " version=" + contract2.getId().getVersion()  + " validStart = " + formatDate.format(date2.toDate()));
	    	this.updateDocument(this._gson.toJson(contract2), contractId, formatDate.format(date2.toDate()));	    	
	    	TimeUnit.SECONDS.sleep(30);

	    	logger.info("update contract id=" + contract3.getId().getId() + " version=" + contract3.getId().getVersion() + " validStart = " + formatDate.format(date3.toDate()));
	    	this.updateDocument(this._gson.toJson(contract3), contractId, formatDate.format(date3.toDate()));	    	
	    	TimeUnit.SECONDS.sleep(30);

	    	StringQueryDefinition query = queryMgr.newStringDefinition().withCriteria(contractId);
			DocumentPage page = docMgr.search(query, 0);
			assertEquals("Wrong number of results", 7, page.size());
			
    	}
    	catch (Exception e) {
    		
    	}
    }
}
