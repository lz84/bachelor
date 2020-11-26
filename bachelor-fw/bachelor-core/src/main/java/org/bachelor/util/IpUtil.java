package org.bachelor.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class IpUtil {
	/**
	 *  得到IP
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static String getServerIp() throws Exception{
		Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
		InetAddress ip = null;
		String ipAddr = "";
		while(allNetInterfaces.hasMoreElements()){
			NetworkInterface netInterface = (NetworkInterface)allNetInterfaces.nextElement();
			Enumeration addresses = netInterface.getInetAddresses();
			boolean flag = false;
			while(addresses.hasMoreElements()){
				ip = (InetAddress)addresses.nextElement();
				if(ip!=null && ip instanceof Inet4Address && !"127.0.0.1".equals(ip.getHostAddress())){
					ipAddr = (ip.getHostAddress());
					flag = true;
					break;
				}
			}
			if(flag == true){
				break;
			}
		}
		/** Window 下面直接引用就行 **/
		//InetAddress.getLocalHost().getHostAddress()
		return ipAddr;
	}
}
