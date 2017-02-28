package com.contractstore.mlcontractdocumentstore;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.marklogic.client.DatabaseClientFactory.Authentication;

public class Config {

	private static Properties props = loadProperties();
	public static final String dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

	public static String host = props.getProperty("contractstore.host");
	
	public static int port = Integer.parseInt(props.getProperty("contractstore.port"));
	
	public static String user = props.getProperty("contractstore.writer_user");
	
	public static String password = props.getProperty("contractstore.writer_password");
	
	public static String admin_user = props.getProperty("contractstore.admin_user");
	
	public static String admin_password = props.getProperty("contractstore.admin_password");
	
	public static Authentication authType = Authentication.valueOf(
				props.getProperty("contractstore.authentication_type").toUpperCase()
				);

	public static String transactionsTemporalCollection = props.getProperty("contractstore.transactions");
	public static String contractsTemporalCollection = props.getProperty("contractstore.contracts");
	public static String productsTemporalCollection = props.getProperty("contractstore.products");
	public static String eventsTemporalCollection = props.getProperty("contractstore.events");
	public static String lifecyclesTemporalCollection = props.getProperty("contractstore.lifecycles");
	
	// get the configuration for the example
	private static Properties loadProperties() {		
	    try {
			String propsName = "Config.properties";
			InputStream propsStream =
				Config.class.getClassLoader().getResourceAsStream(propsName);
			if (propsStream == null)
				throw new IOException("Could not read config properties");

			Properties props = new Properties();
			props.load(propsStream);

			return props;

	    } catch (final IOException exc) {
	        throw new Error(exc);
	    }  
	}
}
