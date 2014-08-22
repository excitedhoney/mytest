package mobi.dreambox.frameowrk.core.util;

import java.io.File;

/**
 * <p>
 * Title: Union Messaging Service
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author alain.zhang
 * @version 1.0
 */
public class ConfigFileUtil {
	private static String confFileRoot = null;
	private static ConfigFileUtil confFileUtil = null;

	private ConfigFileUtil() {
	}

	/**
	 * ��ȡʵ��
	 * 
	 * @return ConfigFileUtil
	 */
	public static ConfigFileUtil getInstance() {
		if (ConfigFileUtil.confFileUtil == null) {
			ConfigFileUtil.confFileUtil = new ConfigFileUtil();
		}
		return ConfigFileUtil.confFileUtil;
	}

	/**
	 * ��ȡ�����ļ�·��
	 * 
	 * @return String
	 */
	public static String getConfigRoot() {

		if (confFileRoot == null) {
			confFileRoot = FileLoader.getCurrentDir();
			if (confFileRoot != null && !confFileRoot.equals("")) {
				
				return confFileRoot;

			}
			String s1 = "";
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			try {
				java.net.URL url = loader.getResource("");

				if (url != null && url.getPath() != null) {
					String rootRealPath = url.getPath();
					s1 = java.net.URLDecoder.decode(rootRealPath, "UTF-8");
					s1 = s1.substring(0, s1.length() - 1);
					s1 = s1 + File.separator + "conf_root";
					File file = new File(s1);
					if (file != null && !file.exists()) {
						file.mkdir();
					}
					confFileRoot = s1;
				} else {
					confFileRoot = System.getProperty("user.dir");
				}

			} catch (Exception e) {
				e.printStackTrace(System.err);

			}

		}
		return confFileRoot;
	}

	/**
	 * ��ȡ����·��
	 * 
	 * @param dir
	 *            String
	 * @return String
	 */
	public static String getConfigDir(String dir) {

		String s1 = "";

		try {
			s1 = getConfigRoot();
			s1 = s1 + File.separator+ dir;
			File file = new File(s1);
			if (file != null && !file.exists()) {
				file.mkdir();
			}

		} catch (Exception e) {
			e.printStackTrace(System.err);
		}

		return s1;
	}

	public static void main(String[] args) {

	}
}
