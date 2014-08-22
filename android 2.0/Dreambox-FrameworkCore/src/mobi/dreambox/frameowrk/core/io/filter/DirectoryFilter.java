package mobi.dreambox.frameowrk.core.io.filter;

import java.io.File;
import java.io.FileFilter;

import mobi.dreambox.frameowrk.core.util.StringUtil;

import org.apache.commons.io.FilenameUtils;




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
public class DirectoryFilter implements FileFilter {
    /**
     * File name pattern, such as "file?name*".
     */
    private String directoryNamePattern = null;
    
    public DirectoryFilter() {
    }

    /**
	 * @param directoryNamePattern
	 */
	public DirectoryFilter(String directoryNamePattern) {
		super();
		// TODO Auto-generated constructor stub
		this.directoryNamePattern = directoryNamePattern;
	}

	/**
	 * @return Returns the directoryNamePattern.
	 */
	public String getDirectoryNamePattern() {
		return this.directoryNamePattern;
	}

	/**
	 * @param directoryNamePattern The directoryNamePattern to set.
	 */
	public void setDirectoryNamePattern(String directoryNamePattern) {
		this.directoryNamePattern = directoryNamePattern;
	}

	/**
     * Tests whether or not the specified abstract pathname should be included in a pathname list.
     *
     * @param pathname The abstract pathname to be tested
     * @return <code>true</code> if and only if <code>pathname</code> should be included
     * @todo Implement this java.io.FileFilter method
     */
    public boolean accept(File pathname) {
    	if(!pathname.isDirectory())
    		return false;
    	
        if(StringUtil.isNotNull(this.directoryNamePattern)
        		&& !FilenameUtils.wildcardMatchOnSystem(pathname.getName(), this.directoryNamePattern))
            return false;
        else
        	return true;
    }
}
