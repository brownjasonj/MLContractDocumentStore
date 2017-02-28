/**
 * 
 */
package com.contractstore.bitemporaldocstore;

import static org.junit.Assert.assertEquals;

import java.net.HttpURLConnection;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.junit.Test;


/**
 * @author jason
 *
 */
public class StoreOutOfOrderTransaction extends BaseHttpTest {
	/**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testStoreOutOfOrderTransaction() {
/*       	try {    			    	    	
	    	Integer transactionId = 700000004;
	    	DateTime date = new DateTime();
	    	Random integerGenerator = new Random();

	    	// generate the set of transactions to be stored
	    	Transaction transaction0 = new Transaction(transactionId, 0, date.toDate());
	    	date = date.plusDays(integerGenerator.nextInt(30));
	    	Transaction transaction1 = new Transaction(transactionId, 1, date.toDate());
	    	date = date.plusDays(integerGenerator.nextInt(30));
	    	Transaction transaction2 = new Transaction(transactionId, 2, date.toDate());

	    	
	    	int HttpResult = this.postTransaction("create", transaction0, transactionId.toString(), transaction0.getValidFromDate());	    	
	    	assertEquals(HttpResult, HttpURLConnection.HTTP_OK);
	    	TimeUnit.SECONDS.sleep(1);

	    	HttpResult = this.postTransaction("delete", transaction2, transactionId.toString(), transaction2.getValidFromDate());	    	
	    	assertEquals(HttpResult, HttpURLConnection.HTTP_OK);
	    	TimeUnit.SECONDS.sleep(1);

	    	HttpResult = this.postTransaction("update", transaction1, transactionId.toString(), transaction1.getValidFromDate());	    	
	    	assertEquals(HttpResult, HttpURLConnection.HTTP_OK);
	    	TimeUnit.SECONDS.sleep(1);

    	}
    	catch (Exception e) {
    		
    	}
    	*/
    }
}
