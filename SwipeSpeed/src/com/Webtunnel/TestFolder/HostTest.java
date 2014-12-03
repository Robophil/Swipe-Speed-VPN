package com.Webtunnel.TestFolder;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;

import com.Webtunnel.Constants.ProxyConstants;

public class HostTest {
	public static void main(String [] arg){
		try {
			System.out.println(Inet4Address.getLocalHost());
			
			Socket socks=new Socket("www.127.0.0.1.com",8087);
			
			System.out.println("connected to danikobs on port 80 ");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
