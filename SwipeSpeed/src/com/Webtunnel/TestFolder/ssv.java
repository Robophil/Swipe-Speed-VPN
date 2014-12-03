package com.Webtunnel.TestFolder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import com.Webtunnel.Constants.ProxyConstants;

public class ssv extends Thread{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//keystore properties
				System.setProperty("javax.net.ssl.keyStore",ProxyConstants.trustPath);
				System.setProperty("javax.net.ssl.keyStorePassword", ProxyConstants.password);
				new ssv().start();
				
		
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			//ssl 
			//create  ssl  socket
			final SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			
			//created a port for ssl connection
			final SSLServerSocket sslserversocket =(SSLServerSocket) sslserversocketfactory.createServerSocket(443);
			
			final SSLSocket NewSslSocket = (SSLSocket) sslserversocket.accept();//accept new client
			
			InputStream inssl=NewSslSocket.getInputStream();
			OutputStream outssl=NewSslSocket.getOutputStream();
			
			
			System.out.println("ssl accepted");
			int i=0;
			//read input from browser
			StringBuilder build1=new StringBuilder(55555);
			while((i=inssl.read()) !=-1){
				build1.append((char)i);
				System.out.println("decrypting");
			}
			
			System.out.println(build1.toString());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
