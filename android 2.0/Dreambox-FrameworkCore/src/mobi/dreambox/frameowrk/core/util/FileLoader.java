package mobi.dreambox.frameowrk.core.util;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
public class FileLoader {
	public static String file_root = null;

	  public String fullLocation(String location)
	  {
	    StringBuffer buf = new StringBuffer();

	    buf.append(getCurrentDir());
	    if ((!getCurrentDir().endsWith("\\")) && (!getCurrentDir().endsWith("\\\\")) && (!getCurrentDir().endsWith("/")))
	    {
	      buf.append(System.getProperty("file.separator"));
	    }

	    buf.append(location);

	    return buf.toString();
	  }
	  public URL fromFilename(String filename) {
	    if (filename == null) return null;
	    File file = new File(filename);
	    URL url = null;
	    try
	    {
	      if (file.exists()) url = file.toURL(); 
	    }
	    catch (MalformedURLException e) {
	      e.printStackTrace();
	      url = null;
	    }
	    return url;
	  }

	  public static String getCurrentDir()
	  {
	    if (file_root == null)
	    {
	      ClassLoader loader = Thread.currentThread().getContextClassLoader();
	      URL url = loader.getResource(".");
	      file_root = url.getPath();
	      File file = new File(file_root);
	      file_root = file.getPath() + File.separator + "conf_root";
	      Properties prop = new Properties();
	      try
	      {
	        prop.load(loader.getResourceAsStream("system_conf.properties"));
	        String tmp = prop.getProperty("APP_FILE_SYS_HOME");
	        if ((tmp != null) && (!tmp.trim().equals(""))) {
	          file_root = prop.getProperty("APP_FILE_SYS_HOME");
	        }
	      }
	      catch (Exception localException)
	      {
	      }
	      file = new File(file_root);
	      if (!file.exists()) {
	        file.mkdir();
	      }

	    }

	    return file_root;
	  }

	  public InputStream loadResource(String location) throws IOException
	  {
	    URL fileUrl = getURL(location);
	    return fileUrl.openStream();
	  }

	  public URL getURL(String location)
	  {
	    String fullLocation = fullLocation(location);
	    URL fileUrl = null;

	    fileUrl = fromFilename(fullLocation);
	    if (fileUrl == null) {
	      System.out.println("File Resource not found: " + fullLocation);
	    }

	    return fileUrl;
	  }
}
