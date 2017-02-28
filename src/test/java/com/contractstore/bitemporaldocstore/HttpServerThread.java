/**
 * 
 */
package com.contractstore.bitemporaldocstore;

import java.io.IOException;

import org.glassfish.grizzly.http.server.HttpServer;

import com.contractstore.mlcontractdocumentstore.HttpServerMain;

/**
 * @author jason
 *
 */
public class HttpServerThread extends Thread {
	private HttpServer _server = null;
	private boolean _started = false;
	
	/**
	 * 
	 */
	public HttpServerThread() {
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		if (!this._started) {
			this._server = HttpServerMain.startServer();
		}

	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#destroy()
	 */
	@Override
	public void destroy() {
		if (this._started && (this._server != null)) {
			this._server.shutdown();
		}
			
	}
	

}
