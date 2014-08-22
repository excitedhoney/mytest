package mobi.dreambox.frameowrk.core.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import mobi.dreambox.frameowrk.core.util.StringUtil;





/**
 * <p>Project: CuteInfo3.2</p>
 * <p>Title: ObjectIOUtil.java</p>
 * <p>Company: Wonders Group</p>
 *
 * @author Ryan
 * @date 2006-12-13 13:55:43
 * @version 1.0
 *
 */
public class ObjectIOUtil {
    protected static void persisteObjectToFile(Object object, File file)throws IOException {
    	FileOutputStream fileOutputStream = null;
    	ObjectOutputStream objectOutputStream = null;
    	try {
			fileOutputStream = new FileOutputStream(file);
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(object);
			objectOutputStream.flush();
			fileOutputStream.flush();
		}
		finally {
			// TODO: handle exception
			IOUtil.closeOutputStream(objectOutputStream);
			IOUtil.closeOutputStream(fileOutputStream);
		}
    }
    
    public synchronized static Object readToObject(String fileName) throws IOException, ClassNotFoundException {
    	if(StringUtil.isNull(fileName))
    		return null;
    	
    	return ObjectIOUtil.readToObject(new File(fileName));
    }
    
    protected static Object readToObject(File file) throws IOException, ClassNotFoundException {
    	Object obj = null;
    	
    	obj = readToObjectFromFile(file);
 
    	
    	return obj;
    }
    
    protected static Object readToObjectFromFile(File file) throws IOException, ClassNotFoundException {
    	if(file == null || !file.exists())
    		return null;
    	
    	FileInputStream fileInputStream = null;
    	ObjectInputStream objectInputStream = null;
    	try {
    		fileInputStream = new FileInputStream(file);
    		objectInputStream = new ObjectInputStream(fileInputStream);
    		return objectInputStream.readObject();
		}
		finally {
			// TODO: handle exception
			IOUtil.closeInputStream(objectInputStream);
			IOUtil.closeInputStream(fileInputStream);
		}
    }
    public static long sizeOfObject(Serializable serializable){
		ByteArrayOutputStream bos = null;
		ObjectOutputStream oos = null;
		long objSize = 0;
		try {
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(serializable);
			objSize = bos.size();
			return objSize;
		} catch (IOException e) {
			return 0;
		}
		finally{
			try {
				if(bos != null)
					bos.close();
				if(oos != null){
					oos.close();
				}
			} catch (IOException e) {
			}
		}
    }
}
