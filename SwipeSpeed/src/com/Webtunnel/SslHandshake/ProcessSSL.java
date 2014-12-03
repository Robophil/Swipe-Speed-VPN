package com.Webtunnel.SslHandshake;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.SSLSocket;

import com.Webtunnel.Constants.ProxyConstants;

public class ProcessSSL implements Runnable{
	
	private SSLSocket socket;
	
	private String port;

	public ProcessSSL(final SSLSocket socket, final String port) {
		// TODO Auto-generated constructor stub
		
		this.socket=socket;
		this.port=port;
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			
			//get streams to and from sslSecured socket
			InputStream inSSL=socket.getInputStream();
			OutputStream outSSL=socket.getOutputStream();
			
			//read decrypted header from sslSocket
			int L=0;
			StringBuilder header=new StringBuilder(999999);
			while((L=inSSL.read()) !=-1){
				
				header.append((char)L);
				
				if(header.indexOf("\r\n\r\n")!=-1){
					break;
				}
				
			}
			
			//print SSL decryted header
			//System.out.println("DECRYPTED HEADER : \n"+header.toString());
			
			//try and append ssl port to the host infomation
			int i=header.toString().indexOf("Host: ");
			int j=header.toString().indexOf("\r\n", i);
			
			String host=header.toString().substring(i+6,j);
			//System.out.println(host);
			
			String finalheader=header.toString();
			finalheader=finalheader.replaceAll("Host: "+host, "Host: "+host+":"+port);
			
			//System.out.println("customised header with appended details :*****************\n"+finalheader);
			
			//connect to http port on localhost
			final Socket httpSocket = new Socket(InetAddress.getByName(ProxyConstants.LOCAL_HOST), ProxyConstants.LOCAL_PORT);
			final InputStream in_http=httpSocket.getInputStream();
			final OutputStream out_http=httpSocket.getOutputStream();
			
			//pass info to process your decrypted ssl request
			ExecutorService e=Executors.newCachedThreadPool();
			e.execute(new HttpPortStream(inSSL, out_http, finalheader));
			e.execute(new SSLPortStream(in_http, outSSL, socket, httpSocket));
			
			e.shutdown();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("process ssl error : "+e.getMessage());
		}
		
	}
	
	

}
