package com.Webtunnel.StreamsHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.Webtunnel.Constants.ProxyConstants;

public class ReadHeader implements Runnable{
	
	private InputStream input_browser,proxy_input;
	
	private OutputStream output_browser,output_proxy;
	
	private StringBuilder build;
	
	private Socket browserSocket,proxy_socket;
	

	/**
	 * reads the header and get information from the stream
	 * it dosen't read the body
	 * 
	 * @param input_browser
	 * @param output_proxy
	 * @param output_browser
	 * @param proxy_input
	 * @param proxy_socket
	 * @param socket
	 */
	public ReadHeader(final InputStream input_browser, final OutputStream output_proxy, final OutputStream output_browser,
			final InputStream proxy_input, final Socket proxy_socket, final Socket socket) {
		// TODO Auto-generated constructor stub
		
		this.input_browser=input_browser;
		
		this.output_browser=output_browser;
		
		this.output_proxy=output_proxy;
		
		this.proxy_input=proxy_input;
		
		this.proxy_socket=proxy_socket;
		
		this.browserSocket=socket;
		
		build=new StringBuilder(9999);
	}

	@Override
	public void run() {
		// get information from browser requestod stub
		try {
			int val;
			
			//keep reading from browser until break character is found
			while((val=input_browser.read())!=-1){
				
				build.append((char)val);
				
				if(build.indexOf("\r\n\r\n")!=-1){
					break;
				}
				
			}
			
			//print out header information gotten from browser
			System.out.println("\n#########################################################################\n"+build.toString());
			ExecutorService ex=Executors.newCachedThreadPool();
			
			
			/**
			 * check if its a secured ssl connection............
			 * if it is decrypt it and send back to http port
			 */
			if(build.toString().startsWith("CONNECT ")) {
				
				String buildString=build.toString();
				String finalPort="";
				String host="";
				
				
				//if host exists get host
				int index=buildString.toLowerCase().indexOf("host: ");
				if(index!=-1){
					int index1=buildString.toString().indexOf("\r\n", index);
					host=buildString.substring(index+6, index1);
				}
				
				
				
				//send connection established to browser, so browser can now send the body
				
				output_browser.write("HTTP/1.1 200 Connection established\r\n".getBytes());
				
				output_browser.write("Proxy-Agent: Mozilla\r\n".getBytes());
				
				output_browser.write("Connection: Keep-Alive\r\n".getBytes());
				
				output_browser.write("\r\n".getBytes());
				
				output_browser.flush();
				
				
				//get ssl port
				int index3=buildString.indexOf(":");
				int endl=buildString.toString().indexOf(" HTTP/1.", index3);
				finalPort=buildString.substring(index3+1, endl);
				finalPort=finalPort.trim();
				
				//Print out ssl port
				//System.out.println("SSL port is "+finalPort+" SSL Host is : "+host);
				
				
				final Socket ssLServerSocket;
				
				//establish connection to localhost based on port ssl port no
				if("443".equals(finalPort)){
					
					ssLServerSocket=new Socket(InetAddress.getByName(ProxyConstants.LOCAL_HOST),ProxyConstants.ssl_443_serverPORT);
					
					System.out.println("Connection to SSL decrypyting port successfully created");
					
				}else if("8443".equals(finalPort)){
					
					ssLServerSocket=new Socket(InetAddress.getByName(ProxyConstants.LOCAL_HOST),ProxyConstants.ssl_8443_serverPORT);
					
					System.out.println("Connection to SSL decrypyting port successfully created");
				}else{
					System.out.println("ERROR ### invalid SSL port : "+finalPort.trim());
					return;
				}
				
				
				
				//steams created to ssl port
				final InputStream in=ssLServerSocket.getInputStream();
				final OutputStream out=ssLServerSocket.getOutputStream();
				
				
				
				//expect return from port
				ExecutorService e=Executors.newCachedThreadPool();
				
				//sending to the ssl decrypting port
				e.execute(new FromServer(input_browser, out, null, null));
				
				//receiving from the decrypting port
				e.execute(new FromServer(in, output_browser, null, ssLServerSocket));
				
				e.shutdown();
				
				
				
				
				return;
			}else{//if its a normal http connection
				
				
				//====> to server class
				ex.execute(new ToServer(input_browser, output_proxy, build.toString(),browserSocket));
				
				//for sending response from server back to browser
				ex.execute(new ServerResponse(proxy_input, output_browser, browserSocket, proxy_socket));
				
				
	
			}
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("error :"+e.getMessage());
		}
		
	}

}
