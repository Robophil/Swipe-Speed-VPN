package com.Webtunnel.StreamsHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.Webtunnel.Constants.ProxyConstants;

public class ProccessSocket implements Runnable{
	
	private Socket Browser_socket,proxysocket;
	
	private ExecutorService ex;

	private InputStream proxy_inputstream, in;

	private OutputStream proxy_outstream, out;
	
	
	
	/**
	 * get streams from browser and proxy 
	 * and pass them to each other
	 * @param socket
	 */
	public ProccessSocket(final Socket socket){
		this.Browser_socket=socket;
		
		
		
		ex=Executors.newCachedThreadPool();
		
		//establishes a connection to the proxy server where the all headers are being sent eg... MTN 10.199.212.2:8080
		create_proxy_connetion();
	}
	
	
	/**
	 * create connection to proxy address
	 */
	private void create_proxy_connetion(){
		try {
			//creates a socket connection to desired proxy
			//proxysocket=new Socket(InetAddress.getByName(ProxyConstants.ScriptAddress), 80);
			proxysocket=new Socket(InetAddress.getByName(ProxyConstants.ProxyScriptAddress),80 );
			
			//get streams to proxy
			proxy_inputstream=proxysocket.getInputStream();
			
			proxy_outstream=proxysocket.getOutputStream();
			
			//get steams from browser
			in=this.Browser_socket.getInputStream();
		
			out=this.Browser_socket.getOutputStream();
			
			System.out.println("Connection to proxy successful :"+proxysocket.getInetAddress()+"\nStreams created");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Proxy connection not extablished : "+e.getMessage());
		}
	}

	@Override
	public void run() {
		//for forwarding request from browser to server
		ex.execute(new ReadHeader(in,proxy_outstream,out,proxy_inputstream,proxysocket,Browser_socket));
		
		
	}
	
}
