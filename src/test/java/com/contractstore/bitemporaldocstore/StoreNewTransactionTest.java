/**
 * 
 */
package com.contractstore.bitemporaldocstore;

import static org.junit.Assert.assertEquals;

import java.net.HttpURLConnection;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.junit.Test;

import com.contractstore.contractcomponents.Contract;
import com.contractstore.contractcomponents.ID;
import com.contractstore.mlcontractdocumentstore.webservices.StoreTransaction;



/**
 * @author jason
 *
 */
public class StoreNewTransactionTest extends BaseHttpTest {
	final static Logger logger = Logger.getLogger(StoreNewTransactionTest.class);


    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testStoreNewTransaction() {
    	/*
    	try {    			    	    	
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
	    	
	    	ID contractId = contract0.getId();
	    	
	    	DateTime date0 = new DateTime();
	    	DateTime date1 = date0.plusMinutes(integerGenerator.nextInt(30));
	    	DateTime date2 = date1.plusMinutes(integerGenerator.nextInt(30));
	    	DateTime date3 = date2.plusMinutes(integerGenerator.nextInt(30));
	    	
	    	Contract contract1 = new Contract(contract0, statusOpen);
	    	Contract contract2 = new Contract(contract1, statusOpen);
	    	Contract contract3 = new Contract(contract2, statusOpen);
	    	
	    	logger.info("create contract id=" + contract0.getId().getId() + " version=" + contract0.getId().getVersion() + " validStart = " + date0);
	    	int HttpResult = this.postContract("create", contract0, contractId.getId().toString(), date0.toDate());	    	
	    	assertEquals(HttpResult, HttpURLConnection.HTTP_OK);
	    	TimeUnit.SECONDS.sleep(1);

	    	logger.info("update contract id=" + contract1.getId().getId() + " version=" + contract1.getId().getVersion() + " validStart = " + date1);
	    	HttpResult = this.postContract("update", contract1, contractId.getId().toString(), date1.toDate());	    	
	    	assertEquals(HttpResult, HttpURLConnection.HTTP_OK);
	    	TimeUnit.SECONDS.sleep(1);

	    	logger.info("update contract id=" + contract2.getId().getId() + " version=" + contract2.getId().getVersion()  + " validStart = " + date2);
	    	HttpResult = this.postContract("update", contract2, contractId.getId().toString(), date2.toDate());	    	
	    	assertEquals(HttpResult, HttpURLConnection.HTTP_OK);
	    	TimeUnit.SECONDS.sleep(1);

	    	logger.info("update contract id=" + contract3.getId().getId() + " version=" + contract3.getId().getVersion() + " validStart = " + date3);
	    	HttpResult = this.postContract("update", contract3, contractId.getId().toString(), date3.toDate());	    	
	    	assertEquals(HttpResult, HttpURLConnection.HTTP_OK);
	    	TimeUnit.SECONDS.sleep(1);

	    	
    	}
    	catch (Exception e) {
    		
    	}
    	*/
    }
 
}
