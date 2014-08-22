/** 
 * 
 * Copyright (c) 1995-2012 Wonders Information Co.,Ltd. 
 * 1518 Lianhang Rd,Shanghai 201112.P.R.C.
 * All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Wonders Group.
 * (Social Security Department). You shall not disclose such
 * Confidential Information and shall use it only in accordance with 
 * the terms of the license agreement you entered into with Wonders Group. 
 *
 * Distributable under GNU LGPL license by gnu.org
 */

package mobi.dreambox.frameowrk.core.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.logging.Log;

/**
 * <p>
 * Title: cuteinfo_[子系统统名]_[模块名]
 * </p>
 * <p>
 * Description: [描述该类概要功能介绍]
 * </p>
 * 
 * @author Administrator
 * @version $Revision$ 2010-10-9
 * @author (lastest modification by $Author$)
 * @since 20100620
 */
public abstract class IPUtils {

	private static final String LOCAL_HOST_ADDRESS = "localhost";


	public static List<InetAddress> getAllHostAddress() {
		try {
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			List<InetAddress> addresses = new ArrayList<InetAddress>();

			while (networkInterfaces.hasMoreElements()) {
				NetworkInterface networkInterface = networkInterfaces.nextElement();
				Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
				while (inetAddresses.hasMoreElements()) {
					InetAddress inetAddress = inetAddresses.nextElement();
					addresses.add(inetAddress);
				}
			}
			return addresses;
		}
		catch (SocketException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static List<String> getAllHostIPAddress() {
		List<String> ipAddresses = new ArrayList<String>();
		List<InetAddress> allInetAddresses = getAllHostAddress();
		for (InetAddress address : allInetAddresses) {
			ipAddresses.add(address.getHostAddress());
		}
		ipAddresses.add(LOCAL_HOST_ADDRESS);
		return ipAddresses;
	}

	public static List<String> getAllNoLoopbackAddresses() {
		List<String> noLoopbackAddresses = new ArrayList<String>();
		List<InetAddress> allInetAddresses = getAllHostAddress();
		for (InetAddress address : allInetAddresses) {
			if (!address.isLoopbackAddress()) {
				noLoopbackAddresses.add(address.getHostAddress());
			}
		}
		return noLoopbackAddresses;
	}
}
