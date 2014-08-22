package mobi.dreambox.frameowrk.core.util;

import java.net.InetAddress;
/**
 * <p>
 * Title: cuteinfo_[子系统统名]_[模块名]
 * </p>
 * <p>
 * Description: [描述该类概要功能介绍]
 * </p>
 * 
 * @author luaoun
 * @version $Revision$ 2011-3-23
 * @author (lastest modification by $Author$)
 * @since 20100620
 */
public class UUID {

	private static final int IP;
	static {
		int ipadd;
		try {
			int result = 0;
			for (int i = 0; i < 4; i++) {
				result = (result << 8) - Byte.MIN_VALUE
						+ (int) InetAddress.getLocalHost().getAddress()[i];
			}
			ipadd = result;
		} catch (Exception e) {
			ipadd = 0;
		}
		IP = ipadd;
	}

	private static short counter = (short) 0;

	private static final int JVM = (int) (System.currentTimeMillis() >>> 8);

	private static short getCount() {
		synchronized (UUID.class) {
			if (counter < 0)
				counter = 0;
			return counter++;
		}
	}

	private static short getHiTime() {
		return (short) (System.currentTimeMillis() >>> 32);
	}

	private static int getLoTime() {
		return (int) System.currentTimeMillis();
	}

	private static String format(int intval) {
		String formatted = Integer.toHexString(intval);
		StringBuffer buf = new StringBuffer("00000000");
		buf.replace(8 - formatted.length(), 8, formatted);
		return buf.toString();
	}

	private static String format(short shortval) {
		String formatted = Integer.toHexString(shortval);
		StringBuffer buf = new StringBuffer("0000");
		buf.replace(4 - formatted.length(), 4, formatted);
		return buf.toString();
	}

	static final synchronized String createID() {
		return new StringBuffer(40).append(format(IP)).append(format(JVM))
				.append(format(getHiTime())).append(format(getLoTime()))
				.append(format(getCount())).toString();
	}

}
