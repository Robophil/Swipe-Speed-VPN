package com.Webtunnel.SslHandshake;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HttpPortStream implements Runnable{
	
	private InputStream input_ssl;
	
	private OutputStream output_httpPort;
	
	private String header;
	
	public HttpPortStream(final InputStream input_ssl, final OutputStream output_httpPort, final String header){
		
		this.input_ssl=input_ssl;
		
		this.output_httpPort=output_httpPort;
		
		this.header=header;
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			//flush header to http port
			output_httpPort.write(header.getBytes());
			
			output_httpPort.flush();
			
			//check if it has content length
			int index2=header.indexOf("Content-Length: ");
			int Len=0;
			if(index2!=-1){
				int end=header.indexOf("\r\n", index2);
				String contentLength=header.substring(index2+16, end);
				Len=Integer.parseInt(contentLength.trim());
				
				//keep reading for that length
				byte[] b=new byte[1024];
				int k=0;
				while((k=input_ssl.read(b)) != -1){
					output_httpPort.write(b, 0, k);
				}
				
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		
	}
	
}
