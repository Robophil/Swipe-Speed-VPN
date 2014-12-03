package com.Webtunnel.SslHandshake;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import com.Webtunnel.Constants.ProxyConstants;

public class Ssl_443_Server implements Runnable{

	
	public Ssl_443_Server() {
		main(null);
	}

	
	public static void main(final String[ ] args){
				//keystore properties
				System.setProperty("javax.net.ssl.keyStore",ProxyConstants.trustPath);
				System.setProperty("javax.net.ssl.keyStorePassword", ProxyConstants.password);
				
	}
	
	
	@Override
	public void run() {
		try {
			//create  ssl  socket
			final SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			
			//created a port for ssl connection
			final SSLServerSocket sslserversocket =(SSLServerSocket) sslserversocketfactory.createServerSocket(ProxyConstants.ssl_443_serverPORT);
			
			
			//prep up to create thread pool
			ExecutorService e=Executors.newCachedThreadPool();
			
			//start accepting ssl connection
			while(true){
				
				final SSLSocket NewSslSocket = (SSLSocket) sslserversocket.accept();//accept new client
				
				System.out.println("Accepted new ssl client "+NewSslSocket.getLocalAddress());
				
				e.execute(new ProcessSSL(NewSslSocket, "443"));
				
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("ssl server starter error : "+e.getMessage());
		}
		
	}

}
