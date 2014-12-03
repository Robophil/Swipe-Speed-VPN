package com.Webtunnel.Start;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.Webtunnel.Constants.ProxyConstants;
import com.Webtunnel.Server.ServerListen;
import com.Webtunnel.SslHandshake.Ssl_443_Server;


/**
 * main class that starts up the server
 * @author Dankobs
 *
 */
public class StartServer {

	public static void main(final String[] args) {
		
		//keystore properties, must be set in a static class 
		//the keystore is used for decrypting ssl connect request
		System.setProperty("javax.net.ssl.keyStore",ProxyConstants.trustPath);
		System.setProperty("javax.net.ssl.keyStorePassword", ProxyConstants.password);
		
		
	}
	
	
	public void Startmeup(){
		
				// creates a sever socket to listen for all request
				//The port the server listens on is being set here
				final ServerListen s=new ServerListen(ProxyConstants.LOCAL_PORT);
				
				final ExecutorService e=Executors.newCachedThreadPool();
				
				e.execute(s);
				
				e.execute(new Ssl_443_Server());
		
	}
	
	

}
