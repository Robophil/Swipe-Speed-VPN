package com.Webtunnel.StreamsHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class FromServer implements Runnable{

	
	
	private InputStream input_server;
	
	private OutputStream output_browser;

	private Socket browser;

	private Socket server;


	public FromServer(InputStream input_server, OutputStream output_browser, Socket browser, Socket server) {
		//collect initial values from constructor
		
		this.input_server=input_server;
		
		this.output_browser=output_browser;

		this.browser=browser;
		
		this.server=server;
	}
	

	@Override
	public void run() {
		//pass response back to browser

		int val=0;
		byte[] b=new byte[1024*3];
		//StringBuilder bb=new StringBuilder(9999);
		
		try {
			
			//keep writing back to browser
			while((val=input_server.read(b))!=-1){
				output_browser.write(b, 0, val);
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	
		finally{
			//close all socket connection
			try {
				input_server.close();
				output_browser.close();
				
				if(browser !=null){
					browser.close();
				}
				if(server !=null){
					server.close();
				}
			}catch(Exception e){	
			}
		}
	

	}
	
	

}
