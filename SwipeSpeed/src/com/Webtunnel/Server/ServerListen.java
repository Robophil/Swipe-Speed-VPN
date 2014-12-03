package com.Webtunnel.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.Webtunnel.StreamsHandler.ProccessSocket;

public class ServerListen implements Runnable{
	
	
	private int localport;
	
	private ServerSocket serversocket;
	
	private Socket socket;
	
	/**
	 * Accepts a socket.
	 * any socket connetion received first comes here first
	 * @param localport
	 */
	public ServerListen(int localport) {
		
		//Initialize all variables used		
		this.localport=localport;
		
		
		try {
			//try to create a server socket to listen on a particular port
			this.serversocket=new ServerSocket(this.localport);
			
			System.out.println("Server started on specified port");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error listing on this port, port probably in use..........");
		}
	}

	
	@Override
	public void run() {
		// keep on accepting connections
		int i=0;
		ExecutorService exe=Executors.newCachedThreadPool();
		while(true){
			try {
				socket=serversocket.accept();
				System.out.println("New clent accepted on specified port : "+(++i));
				
				//forward socket newly accepted socket for processing
				
				exe.execute(new ProccessSocket(socket));
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("error accepting new socket : "+e.getMessage());
			}
		}
	}
	
	
	

}
