package mobi.dreambox.frameowrk.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

import org.apache.commons.logging.Log;

/**
 * <p>Title: Data Grid</p>
 *
 * <p>Description: A general data persistence layer.</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Wonders Group</p>
 *
 * @author Ryan
 * @version 1.0
 */
public class IOUtil {

    /**
     * Close a InputStream.
     *
     * @param inputStream InputStream
     */
    public static void closeInputStream(InputStream inputStream) {
        if(inputStream == null)
            return;

        try{
            inputStream.close();
        }
        catch(IOException e){
        }
    }

    /**
     * Close a OutputStream.
     *
     * @param outputStream OutputStream
     */
    public static void closeOutputStream(OutputStream outputStream) {
        if(outputStream == null)
            return;

        try{
        	outputStream.flush();
            outputStream.close();
        }
        catch(IOException e){
        }
    }

    public static void closeStream(InputStream inputStream, OutputStream outputStream) {
        IOUtil.closeInputStream(inputStream);
        IOUtil.closeOutputStream(outputStream);
    }

    /**
     * Close InputStreams and OutputStreams.
     *
     * @param inputStreams List
     * @param outputStreams List
     */
    public static void closeStreams(List inputStreams, List outputStreams) {
        if(inputStreams != null){
            InputStream inputStream = null;
            for(int i = 0;i < inputStreams.size();i++) {
                inputStream = (InputStream)inputStreams.get(i);
                IOUtil.closeInputStream(inputStream);
            }
        }

        if(outputStreams != null){
            OutputStream outputStream = null;
            for(int i = 0;i < outputStreams.size();i++) {
                outputStream = (OutputStream)outputStreams.get(i);
                IOUtil.closeOutputStream(outputStream);
            }
        }
    }
    
    public static void closeReader(Reader reader) {
    	if(reader == null)
    		return;
    	
    	try {
			reader.close();
		}
		catch(Exception e) {
		}
    }
    
    public static void closeWriter(Writer writer) {
    	if(writer == null)
    		return;
    	
    	try {
    		writer.close();
		}
		catch(Exception e) {
			// TODO: handle exception
		}
    }
    
    public static void closeReaderAndWriter(Reader reader, Writer writer) {
    	IOUtil.closeReader(reader);
    	IOUtil.closeWriter(writer);
    }
    
    public static void closeReadersAndWriters(List readers, List writers) {
        if(readers != null){
            Reader reader = null;
            for(int i = 0;i < readers.size();i++) {
            	reader = (Reader)readers.get(i);
                IOUtil.closeReader(reader);
            }
        }

        if(writers != null){
            Writer writer = null;
            for(int i = 0;i < writers.size();i++) {
            	writer = (Writer)writers.get(i);
                IOUtil.closeWriter(writer);
            }
        }
    }
}
