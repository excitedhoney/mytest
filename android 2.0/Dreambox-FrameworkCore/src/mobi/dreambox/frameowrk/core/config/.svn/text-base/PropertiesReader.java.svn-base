package mobi.dreambox.frameowrk.core.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import mobi.dreambox.frameowrk.core.constant.DboxConstants;
import mobi.dreambox.frameowrk.core.context.SystemContext;

public class PropertiesReader {
	private PropertiesReader() {

	}
	private static PropertiesReader instance;

	private static final String SYNC_KEY = "SYNC_KEY";

	private Properties systemProperties;

	private Properties customProperties;

	public static PropertiesReader getInstance() {
		synchronized (SYNC_KEY) {
			if (instance == null)
				instance = new PropertiesReader();
			return instance;
		}
	}

	public void init() {
		InputStream systemInputStream;
		try {
			systemInputStream =
					SystemContext.getInstance().getContext().getAssets()
							.open(DboxConstants.FILE_NAME_SYSTEM_CONFIG_PROPERTIES);
			systemProperties = new Properties();
			systemProperties.load(systemInputStream);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		InputStream customInputStream;
		try {
			customInputStream =
					SystemContext.getInstance().getContext().getAssets()
							.open(DboxConstants.FILE_NAME_CUSTOM_CONFIG_PROPERTIES);
			customProperties = new Properties();
			customProperties.load(customInputStream);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getSystemProperties(String name) {
		if (systemProperties == null)
			return null;
		else if (systemProperties.containsKey(name))
			return systemProperties.get(name).toString();
		else
			return null;
	}

	public String getCustomProperties(String name) {
		if (customProperties == null)
			return null;
		else if (customProperties.containsKey(name))
			return customProperties.get(name).toString();
		else
			return null;
	}
}
