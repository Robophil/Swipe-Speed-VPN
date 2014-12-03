package com.Webtunnel.TestFolder;


public class byteint {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String finalHeader="";
		//finalHeader=finalHeader+"Referer: http://mobile.etisalat.com.ng/WAP/MobileDynamicImage/176x208/images_header_H72_W158_weyak_xtag=.gif\r\n";
		finalHeader=finalHeader+"Connection: Close\r\n";
		finalHeader=finalHeader+"Pragma: no-cache\r\n";
		finalHeader=finalHeader+"Cache-Control: no-cache\r\n\r\n";
		
		System.out.println(finalHeader.length());
	}

}
