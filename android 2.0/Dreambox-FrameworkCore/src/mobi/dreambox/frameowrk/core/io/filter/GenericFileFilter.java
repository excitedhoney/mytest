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
public class GenericFileFilter implements FileFilter{
    /**
     * File name pattern, such as "file?name*".
     */
    private String fileNamePattern = null;

    /**
     * File suffix, such as ".java".
     */
    private String fileSuffixs[] = null;

    /**
     * Constructor.
     */
    public GenericFileFilter() {
    }

    /**
     * Constructor.
     *
     * @param fileNamePattern String
     */
    public GenericFileFilter(String fileNamePattern) {
        this.setFileNamePattern(fileNamePattern);
    }

    /**
     * Constructor.
     *
     * @param fileNamePattern String
     * @param fileSuffixs String
     */
    public GenericFileFilter(String fileNamePattern, String fileSuffix) {
        this.setFileNamePattern(fileNamePattern);
        this.setFileSuffix(new String[]{fileSuffix});
    }

    /**
     * Constructor.
     *
     * @param fileNamePattern String
     * @param fileSuffixs String[]
     */
    public GenericFileFilter(String fileNamePattern, String fileSuffixs[]) {
        this.setFileNamePattern(fileNamePattern);
        this.setFileSuffix(fileSuffixs);
    }

    //setters and getters
    public void setFileNamePattern(String fileNamePattern) {
        this.fileNamePattern = fileNamePattern;
    }

    public String getFileNamePattern() {
        return this.fileNamePattern;
    }

    public void setFileSuffix(String fileSuffixs[]) {
        this.fileSuffixs = fileSuffixs;
    }

    public String[] getFileSuffix() {
        return this.fileSuffixs;
    }

    /**
     * Tests whether or not the specified abstract pathname should be included in a pathname list.
     *
     * @param pathname The abstract pathname to be tested
     * @return <code>true</code> if and only if <code>pathname</code> should be included
     * @todo Implement this java.io.FileFilter method
     */
    public boolean accept(File pathname) {
    	String fileFullName = pathname.getName();
    	String fileName = FilenameUtils.getBaseName(fileFullName);
    	
        if(StringUtil.isNotNull(this.fileNamePattern)
        		&& !FilenameUtils.wildcardMatchOnSystem(fileName, this.fileNamePattern))
            return false;

        if(this.fileSuffixs == null || this.fileSuffixs.length == 0)
            return true;

        for(int i = 0; i < this.fileSuffixs.length; i++)
            if(fileFullName.toLowerCase().endsWith(this.fileSuffixs[i].toLowerCase()))
                return true;

        return false;
    }

    //overrides
    public String toString() {
        String returnString = "";
        if(StringUtil.isNotNull(this.fileNamePattern))
            returnString += this.fileNamePattern;
        if(this.fileSuffixs != null)
            returnString += this.fileSuffixs;

        return returnString;
    }
}
