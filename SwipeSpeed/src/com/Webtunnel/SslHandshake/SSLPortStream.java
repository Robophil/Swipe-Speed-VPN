package com.Webtunnel.SslHandshake;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SSLPortStream implements Runnable{
	
	private InputStream input_httpPort;
	
	private OutputStream output_SSLPort;
	
	private Socket httpSock;
	
	private Socket sslSock;
	
	public SSLPortStream(final InputStream input_httpPort, final OutputStream output_SSLPort, Socket httpSock, Socket sslSock){
		
		this.input_httpPort=input_httpPort;
		
		this.output_SSLPort=output_SSLPort;
		
		this.httpSock=httpSock;
		
		this.sslSock=sslSock;
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			
			int i=0;
			byte[] b=new byte[1024*3];
			
			while((i=input_httpPort.read(b)) != -1){
				
				
				output_SSLPort.write(b, 0, i);
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		
		
		finally{
			try {
				httpSock.close();
				
				sslSock.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
		}
		
	}
	
}
