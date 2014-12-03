package com.Webtunnel.TestFolder;

import java.io.IOException;
import java.net.ServerSocket;

public class VpsTest {

	public VpsTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			ServerSocket s=new ServerSocket(8087);
			while(true){
			s.accept();
			System.out.println("new client accepted ");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
