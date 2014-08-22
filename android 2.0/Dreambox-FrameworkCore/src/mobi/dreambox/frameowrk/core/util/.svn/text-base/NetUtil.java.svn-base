package mobi.dreambox.frameowrk.core.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
/**
 * <p>
 * Title: Internet相关工具类
 * </p>
 * <p>
 * Description: [描述该类概要功能介绍]
 * </p>
 * 
 * @author liuxiaojun
 * @version $Revision$ 2011-3-24
 * @author (lastest modification by $Author$)
 * @since 20100620
 */
public class NetUtil {



	private NetUtil() {
		super();
	}

	/**
	 * 取得本机IP地址
	 * 
	 * @return 本机IP地址
	 */
	public static String getLocalHostIPAddress() {
		String[] ips = getAllLocalHostIPAddress(false);
		if (ips.length > 0) {
			return ips[0];
		}

		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			try {
				return InetAddress.getByName(null).getHostAddress();
			} catch (UnknownHostException ex) {
				return "localhost";
			}
		}
	}
	
	/**
	 * 取得本机绑定的所有IP地址
	 * 
	 * @return 本机绑定的所有IP地址
	 */
	public static String[] getAllLocalHostIPAddress() {
		return getAllLocalHostIPAddress(true);
	}
	
	private static String[] getAllLocalHostIPAddress(boolean allIpFlag) {
		List<String> ips = new ArrayList<String>();
		try {
			Enumeration<?> netInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
				Enumeration<?> netAddresses = ni.getInetAddresses();
				while (netAddresses.hasMoreElements()) {
					ip = (InetAddress) netAddresses.nextElement();
					if ((ip != null) && !ip.isLoopbackAddress()		//bug 18213
							&& ip.getHostAddress().indexOf(":") == -1) {
						String address = ip.getHostAddress();
						if (allIpFlag) {
							ips.add(address);
						} else {
							return new String[] { address };
						}
					}
				}
			}
		} catch (Exception ignore) {
		}
		return ips.toArray(new String[0]);
	}

	/**
	 * 取得本机IP地址
	 * 
	 * @return 本机IP地址
	 */
	public static String getHostIPAddress(String hostName) {
		try {
			return InetAddress.getByName(hostName).getHostAddress();
		} catch (UnknownHostException e1) {
			return null;
		}
	}

	/**
	 * 取得本机主机名
	 * 
	 * @return 本机主机名
	 */
	public static String getLocalHostName() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			try {
				return InetAddress.getByName(null).getHostName();
			} catch (UnknownHostException e1) {
				return null;
			}
		}
	}

	/**
	 * 取得主机名
	 * 
	 * @param ipv4 ipv4格式地址
	 * @return 主机名
	 */
	public static String getHostName(String ipv4) {
		try {
			String[] ips = ipv4.split("[.]");
			if (ips.length == 4) {
				
				return InetAddress.getByAddress(new byte[]{
						(byte)Integer.parseInt(ips[0]), 
						(byte)Integer.parseInt(ips[1]), 
						(byte)Integer.parseInt(ips[2]), 
						(byte)Integer.parseInt(ips[3])}).getCanonicalHostName();
			}
			
		} catch (UnknownHostException e) {
		}
		return null;
	}
	
	public static void main(String[] args) throws UnknownHostException {
		System.out.println(getHostName("192.168.0.158"));
	}

	/**
	 * 关闭Socket，不抛出任何异常。<BR>
	 * 
	 * Close socket,and no exceptions are thrown.<BR>
	 * 
	 * @param socket
	 * 
	 */
	public static void closeQuietly(Socket socket) {
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				// Nothing to do
			}
		}
	}

}
