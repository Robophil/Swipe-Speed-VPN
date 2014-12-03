package com.Webtunnel.StreamsHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.Webtunnel.Constants.ProxyConstants;


public class ToServer implements Runnable{

	private final InputStream input_browser;
	
	private OutputStream output_server;
	
	private StringBuffer body;
	
	private String build;
	
	private String contentLength, finalPort;
	
	/**
	 * receives the header and streams for sending values to the server
	 * Also receives the port no for ssl connection
	 * 
	 * @param input_browser
	 * @param output_server
	 * @param build
	 * @param finalPort
	 */
	public ToServer(InputStream input_browser, final OutputStream output_server, final String build, final Socket socket) {
		// TODO Auto-generated constructor stub
		
		this.input_browser=input_browser;
		
		this.output_server=output_server;
		
		this.build=build;
		
		body=new StringBuffer(9999);
		
		//set the initial default port to 80
		this.finalPort="80";
	}

	@Override
	public void run() {
		try {
			
			//extract detailed information in bits from the header
			String buildString=build.toString();
			String host="";
			
			//if host exists get host
			int index=buildString.toLowerCase().indexOf("host: ");
			if(index!=-1){
				int index1=buildString.toString().indexOf("\r\n", index);
				host=buildString.substring(index+6, index1);
			}
			
			//if there is a port no attached to it.........get it, its the custom ssl port attached to the decrypted header
			if(host.contains(":")){
				int index11=host.toString().indexOf(":");
				//final port for ssl
				finalPort=host.toString().substring(index11+1).trim();
				System.out.println("its ssl and it has a port "+finalPort);
				//update host
				host=host.replaceFirst(":"+finalPort, "");
				buildString=buildString.replaceFirst("Host: "+host+":"+finalPort, "Host: "+host);
				
			}
			
			
			//replace keep-alive with close in the header
			if(buildString.contains("Connection: keep-alive")){
				buildString=buildString.replaceAll("Connection: keep-alive", "Connection: Close");
				
				System.out.println("keep alive replaced ====>\n");
			}
			
			
			//check if it has content length
			int index2=buildString.indexOf("Content-Length: ");
			int Len=0;
			if(index2!=-1){
				int end=buildString.indexOf("\r\n", index2);
				contentLength=buildString.substring(index2+16, end);
				Len=Integer.parseInt(contentLength.trim());
				
				//keep reading for that length
				for (int j = 0; j < Len; j++) {
					int v=this.input_browser.read();
					if(v != -1){
						System.out.println(v);
						body.append((char)v);
					}
				}
				System.out.println(Len+"\n"+"this is the body => "+body.toString());
			}
			
			
			
			/**
			 * final processing before header is sent to proxy network
			 */
			
			
			//trim if an excess space in the host
			host=host.trim();
			
			/**
			 * temp fix based on say time no just de
			 * not one of my best work
			 * POST REQUEST NOT YET IMPLEMENTED
			 */
			
			if(-1==buildString.indexOf("GET http://"+host)){
				buildString=buildString.replaceFirst("GET /", "GET http://server4-1.operamini.com/");
			}else{
			buildString=buildString.replaceFirst("http://"+host, "http://server4-1.operamini.com");}
			
			System.out.println(buildString);
			
			/**
			 * end of temp shit
			 */
			
			
			//Construct my post request to my script
			String finalHeader="";
			String user="freetrial1";
			
			//encrypt data
			user=retEncStr(user.getBytes());
			host=retEncStr(host.getBytes());
			finalPort=retEncStr(finalPort.getBytes());
			String head_body=buildString+body.toString();
			head_body=retEncStr_Head_Body(head_body.getBytes());
			
			final String info2path=user+"*****"+host+"*****"+finalPort+"*****"+head_body;
			
			if (0==ProxyConstants.tweak_method) {
				System.out.println("method 1");
				finalHeader=finalHeader+"GET http://server4-1.operamini.com/ HTTP/1.1\r\n";
				finalHeader=finalHeader+"Host: "+ProxyConstants.ScriptAddress+"\r\n";
				finalHeader=finalHeader+"User-Agent: Mozilla/5.0 (Windows NT 6.2; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0\r\n";
				//finalHeader=finalHeader+"User-Agent: Opera/9.80 (J2ME/MIDP; Opera Mini/9 (Compatible; MSIE:9.0; iPhone; BlackBerry9700; AppleWebKit/24.746; U; en) Presto/2.5.25 Version/10.54\r\n";
				finalHeader=finalHeader+"Accept: */*\r\n";
				finalHeader=finalHeader+"Accept-Language: en-US,en;q=0.5\r\n";
				finalHeader=finalHeader+"X-Cook-id: "+info2path+"\r\n";
				finalHeader=finalHeader+"Connection: Keep-alive\r\n";
				finalHeader=finalHeader+"Connection: Close\r\n\r\n";
			}else{
				System.out.println("method 2");
				finalHeader=finalHeader+"GET http://server4-1.operamini.com/ HTTP/1.0\r\n";
				finalHeader=finalHeader+"Host: "+"www.nairaland.com:80"+"\r\n";
				finalHeader=finalHeader+"X-Cook-id: "+info2path+"\r\n";
				finalHeader=finalHeader+"User-Agent: Opera/9.80 (J2ME/MIDP; Opera Mini/9 (Compatible; MSIE:9.0; iPhone; BlackBerry9700; AppleWebKit/24.746; U; en) Presto/2.5.25 Version/10.54\r\n";
				finalHeader=finalHeader+"Accept: */*\r\n";
				finalHeader=finalHeader+"Accept-Language: en-US,en;q=0.5\r\n";
				finalHeader=finalHeader+"Accept-Encoding: gzip, deflate\r\n";
				finalHeader=finalHeader+"Connection: Keep-alive\r\n";
				finalHeader=finalHeader+"Connection: Close\r\n\r\n";
				
			}
			
			System.out.println(info2path);
			
			//now send post request to network server
			//output_server.write((buildString+body.toString()).getBytes());
			output_server.write(buildString.getBytes());
			
			output_server.flush();
			
			//save the amount of data used
			ProxyConstants.upload=ProxyConstants.upload+finalHeader.length();
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Toserver error : "+e.getMessage());
		}
		
	}
	
	
	//for erncrypting the byte using the specified key
	private byte[] encrypt(byte[] b){
		byte[] bb=b;
		for (int i = 0; i < bb.length; i++) {
			bb[i]=(byte) (bb[i]+ProxyConstants.enckey);
		}
		return bb;
	}
	
	
	//returns an encrypted int representation of the byte in string form
	private String retEncStr(byte[] b){
		byte[] bb=encrypt(b);
		StringBuilder encryToStr=new StringBuilder(99999);
		
		for (int i = 0; i < bb.length; i++) {
			if(i==bb.length-1){
				encryToStr.append((int)bb[i]);
				break;
			}
			encryToStr.append((int)bb[i] +"%");
		}
		
		return encryToStr.toString();
	}
	
	//just for returning the int representation of the byte in string form
	private String retEncStr_Head_Body(byte[] b){
		byte[] bb=b;
		StringBuilder encryToStr=new StringBuilder(99999);
		
		for (int i = 0; i < bb.length; i++) {
			if(i==bb.length-1){
				encryToStr.append((int)bb[i]);
				break;
			}
			encryToStr.append((int)bb[i] +"%");
		}
		
		return encryToStr.toString();
	}
	
}
