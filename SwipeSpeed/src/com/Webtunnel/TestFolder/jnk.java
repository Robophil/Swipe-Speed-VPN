package com.Webtunnel.TestFolder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class jnk extends Thread{

	public static void main(String[] args) {
		new jnk().start();
		
	}
	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			ServerSocket server=new ServerSocket(55555);
			
			Socket socket=server.accept();
			
			InputStream inbro=socket.getInputStream();
			//OutputStream outbro=socket.getOutputStream();
			
			
			
			//read input from browser
			StringBuilder build1=new StringBuilder(55555);
			int i=0;
			while((i=inbro.read()) !=-1){
				build1.append((char)i);
				
				if(build1.indexOf("\r\n\r\n") != -1){
					break;
				}
			}
			
			System.out.println(build1.toString());
			
			//socket connection to the ssl decrypting port
			Socket ss=new Socket(InetAddress.getByName("127.0.0.1"), 443);
			InputStream insss=ss.getInputStream();
			OutputStream outsss=ss.getOutputStream();
			
			i=0;
			while((i=inbro.read()) !=-1){
				outsss.write(i);
				outsss.flush();
			}
				System.out.println("done");
			
			
			
			
			
			
			
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
