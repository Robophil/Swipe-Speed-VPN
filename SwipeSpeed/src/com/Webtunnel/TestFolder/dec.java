package com.Webtunnel.TestFolder;

import com.Webtunnel.Constants.ProxyConstants;

public class dec {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s="GET http://www.ictu.uniben.edu/login HTTP/1.1\r\nUser-Agent: Mozilla/5.0 (Windows NT 6.2; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0\r\n"
				+ "Connection: Close\r\nCache-Control: no-cache\r\neferer: mobile.etisalat.com.ng/";
		s=retEncStr(s.getBytes());
		System.out.println(s);
		
		String g=retDecStr(s.getBytes());
		
		System.out.println(g);
		
	}
	
	private static byte[] encrypt(byte[] b){
		byte[] bb=b;
		for (int i = 0; i < bb.length; i++) {
			bb[i]=(byte) (bb[i]+ProxyConstants.enckey);
		}
		return bb;
	}
	
	private static String retEncStr(byte[] b){
		byte[] bb=encrypt(b);
		StringBuilder encryToStr=new StringBuilder(99999);
		
		for (int i = 0; i < bb.length; i++) {
			encryToStr.append((char)bb[i]);
		}
		
		return encryToStr.toString();
	}
	
	
	private static byte[] DEencrypt(byte[] b){
		byte[] bb=b;
		for (int i = 0; i < bb.length; i++) {
			bb[i]=(byte) (bb[i]-ProxyConstants.enckey);
		}
		return bb;
	}
	
	private static String retDecStr(byte[] b){
		byte[] bb=DEencrypt(b);
		StringBuilder encryToStr=new StringBuilder(99999);
		
		for (int i = 0; i < bb.length; i++) {
			encryToStr.append((char)bb[i]);
		}
		
		return encryToStr.toString();
	}

}
