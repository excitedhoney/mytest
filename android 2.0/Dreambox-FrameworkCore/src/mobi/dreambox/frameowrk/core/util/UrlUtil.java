package mobi.dreambox.frameowrk.core.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
/**
 * <p>
 * Title: cuteinfo_[子系统统名]_[模块名]
 * </p>
 * <p>
 * Description: [描述该类概要功能介绍]
 * </p>
 * 
 * @author liuxiaojun
 * @version $Revision$ 2011-3-23
 * @author (lastest modification by $Author$)
 * @since 20100620
 */
public class UrlUtil {


	/** URL prefix for loading from the file system: "file:" */
	public static final String FILE_URL_PREFIX = "file:";

	/**
	 * 因为不需要实例，所以构造函数为私有<BR>
	 * 参见Singleton模式<BR>
	 *
	 * Only one instance is needed,so the default constructor is private<BR>
	 * Please refer to singleton design pattern.
	 */
	private UrlUtil() {
		super();
	}

	/**
	 * 取得资源定位对象
	 *
	 * @param resourceLocation 资源位置
	 *
	 * @throws RuntimeException 抛出条件：文件路径不是一个URL, 文件资源不存在
	 */
	public static URL getURL(String resourceLocation) throws RuntimeException {		
		return getURL(resourceLocation, null);
	}

	/**
	 * 取得资源定位对象
	 *
	 * @param resourceLocation 资源位置
	 *
	 * @throws RuntimeException 抛出条件：文件路径不是一个URL, 文件资源不存在
	 */
	public static URL getURL(String resourceLocation, ClassLoader loader) throws RuntimeException {
		if (resourceLocation.startsWith(FILE_URL_PREFIX)) {
			resourceLocation = resourceLocation.substring(FILE_URL_PREFIX.length());
		}
		File file = new File(resourceLocation);
		if (file.exists()) {
			try {
				return new URL(resourceLocation);
			} catch (MalformedURLException ex) {
				try {
					return new URL(FILE_URL_PREFIX + resourceLocation);
				} catch (MalformedURLException ex2) {
					throw new RuntimeException("The [resourceLocation=" + resourceLocation + "] is neither a URL not a well-formed file path.", ex2);
				}
			}
		}
		URL url = null;
		if (loader != null) {
			url = loader.getResource(resourceLocation);
		}
		if (url == null) {		
			url = Thread.currentThread().getContextClassLoader().getResource(resourceLocation);
			if (url == null) {
				url = UrlUtil.class.getClassLoader().getResource(resourceLocation);
				if (url == null) {
					throw new RuntimeException("Class path resource [" + resourceLocation + "] cannot be resolved to URL because it does not exist.");
				}
			}
		}
		return url;
	}

}
