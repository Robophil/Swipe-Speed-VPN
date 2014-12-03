package com.Webtunnel.Constants;

import java.io.File;

public class ProxyConstants {

	/**
	 * address for server to start on
	 */
	public static String LOCAL_HOST="127.0.0.1";
	
	/**
	 * port on which server listens on
	 */
	public static int LOCAL_PORT=8081;
	/**
	 * server script address
	 */
	public static String ScriptAddress="154.127.61.100";
	/**
	 * opera server proxy address
	 */
	public static String ProxyScriptAddress="global-4-lvs-turing-1.opera-mini.net";
	/**
	 * port for receiving ssl connection
	 */
	public static int ssl_443_serverPORT=55555;
	
	public static int ssl_8443_serverPORT=66666;
	/**
	 * set trust store path
	 */
	public static String trustPath=new File("").getAbsolutePath()+"/swipespeed.jks";
	
	public static String password="iamaboy"; 
	/**
	 * data usage
	 */
	public static double upload=0.00;
	
	public static double download=0.00;
	/**
	 * address of network provider i want to connect to
	 */
	public static String networkprovider="10.71.170.5";
	
	public static int network_provider_port=8080;
	/**
	 * the key used in encrypting info sent over the network
	 */
	public static int enckey=5;
	/**
	 * int value that tells the method to be used for tweaking 
	 */
	public static int tweak_method=0;
}
