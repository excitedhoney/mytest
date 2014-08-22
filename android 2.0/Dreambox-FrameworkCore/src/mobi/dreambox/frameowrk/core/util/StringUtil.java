package mobi.dreambox.frameowrk.core.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.Character.UnicodeBlock;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import mobi.dreambox.frameowrk.core.io.FileUtil;

/**
 * <p>
 * Title: String Util
 * </p>
 * 
 * <p>
 * Description: Some coomon string util methods.
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Wonders Group
 * </p>
 * 
 * @author Ryan
 * @version 1.0
 */
public class StringUtil {
	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss.SSS");

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	public static String toString(Object object) {
		if (object == null)
			return null;
		else if (object instanceof Calendar)
			return StringUtil.simpleDateFormat.format(new Timestamp(
					((Calendar) object).getTimeInMillis()));
		else if (object instanceof Date)
			return StringUtil.simpleDateFormat.format((Date) object);
		else if (object instanceof InputStream){
			ByteArrayOutputStream bos = new ByteArrayOutputStream();  
			InputStream in = (InputStream)object;
			//读取缓存  
			byte[] buffer = new byte[2048];  
			int length = 0;  
			try {
				while((length = in.read(buffer)) != -1) {  
				    bos.write(buffer, 0, length);//写入输出流  
				}
				
			} catch (IOException e) {
				
			}  finally{
//				try {
//					in.close();
//				} catch (IOException e) {
//					
//				}
			}
			return bos.toString();
		}
		else
			return String.valueOf(object);
	}

	/**
	 * If a string is null or an empty string, it's a null string.
	 * 
	 * @param str
	 *            String
	 * @return boolean
	 */
	public static boolean isNull(String str) {
		return str == null || str.trim().length() == 0;
	}

	/**
	 * If a string is not null and an empty string, it's not a null string.
	 * 
	 * @param str
	 *            String
	 * @return boolean
	 */
	public static boolean isNotNull(String str) {
		return str != null && str.trim().length() > 0;
	}

	/**
	 * Compare strings.
	 * 
	 * @param str1
	 *            String
	 * @param str2
	 *            String
	 * @return boolean
	 */
	public static boolean compare(String str1, String str2) {
		if (str1 != null)
			return str1.equals(str2);
		if (str2 != null)
			return str2.equals(str1);

		return true;
	}

	/**
	 * Compare strings.
	 * 
	 * @param str1
	 *            String
	 * @param str2
	 *            String
	 * @return boolean
	 */
	public static boolean compareIgnoreCase(String str1, String str2) {
		if (str1 != null)
			return str1.equalsIgnoreCase(str2);
		if (str2 != null)
			return str2.equalsIgnoreCase(str1);

		return true;
	}

	/**
	 * Return the not null string.
	 * 
	 * @param str1
	 *            String
	 * @param str2
	 *            String
	 * @return String
	 */
	public static String returnNotNull(String str1, String str2) {
		return StringUtil.isNotNull(str1) ? str1 : str2;
	}

	/**
	 * If a string is not null, then convert it to upper case.
	 * 
	 * @param str
	 *            String
	 * @return String
	 */
	public static String toUpperCaseOnStrategy(String str) {
		return str;
	}
	public static String toUpperCase(String str){
		return str == null ? str : str.toUpperCase();
	}
	/**
	 * If a string is not null, then convert it to lower case.
	 * 
	 * @param str
	 *            String
	 * @return String
	 */
	public static String toLowerCaseIfNotNull(String str) {
		return str == null ? str : str.toLowerCase();
	}

	/**
	 * Concatenate strings with dot.
	 * 
	 * @param str1
	 *            String
	 * @param str2
	 *            String
	 * @return String
	 */
	public static String concatWithDot(String str1, String str2) {
		return str1 + "." + str2;
	}

	/**
	 * Concatenate strings with dot.
	 * 
	 * @param str1
	 *            String
	 * @param str2
	 *            String
	 * @param str3
	 *            String
	 * @return String
	 */
	public static String concatWithDot(String str1, String str2, String str3) {
		return str1 + "." + str2 + "." + str3;
	}

	/**
	 * Concatenate strings with separator.
	 * 
	 * @param strings
	 *            List
	 * @param separator
	 *            String
	 * @return String
	 */
	public static String concatWithSeparator(List<String> strings, String separator) {
		return StringUtil.concatWithSeparator(strings, separator, "");
	}

	/**
	 * Concatenate strings with separator.
	 * 
	 * @param strings
	 *            List
	 * @param separator
	 *            String
	 * @param afterLast
	 *            String
	 * @return String
	 */
	public static String concatWithSeparator(List<String> strings, String separator,
			String afterLast) {
		if (strings == null || strings.size() == 0)
			return "";

		String returnString = "";
		for (int i = 0; i < strings.size(); i++) {
			returnString += strings.get(i);

			if (i < strings.size() - 1)
				returnString += separator;
		}

		returnString += afterLast;
		return returnString;
	}

	/**
	 * Generate recursion string with gene and repeat time.
	 * 
	 * @param recursionGene
	 *            String
	 * @param repeatTime
	 *            int
	 * @param separator
	 *            String
	 * @return String
	 */
	public static String generateRecursionString(String recursionGene,
			int repeatTime, String separator) {
		return StringUtil.generateRecursionString(recursionGene, repeatTime,
				separator, "");
	}

	/**
	 * Generate recursion string with gene and repeat time.
	 * 
	 * @param recursionGene
	 *            String
	 * @param repeatTime
	 *            int
	 * @param separator
	 *            String
	 * @param afterLast
	 *            String
	 * @return String
	 */
	public static String generateRecursionString(String recursionGene,
			int repeatTime, String separator, String afterLast) {
		if (repeatTime <= 0)
			return "";

		String returnString = "";
		for (int i = 0; i < repeatTime; i++) {
			returnString += recursionGene;

			if (i < repeatTime - 1)
				returnString += separator;
		}

		returnString += afterLast;
		return returnString;
	}

	/**
	 * Generate StringTokenizer with "/".
	 * 
	 * @param str
	 *            String
	 * @return StringTokenizer
	 */
	public static StringTokenizer toStringTokenizer(String str) {
		return StringUtil.toStringTokenizer(str, "/");
	}

	/**
	 * Generate StringTokenizer with given delim.
	 * 
	 * @param str
	 *            String
	 * @param delim
	 *            String
	 * @return StringTokenizer
	 */
	public static StringTokenizer toStringTokenizer(String str, String delim) {
		return new StringTokenizer(str, delim);
	}

	/**
	 * Convert a string collection to string array.
	 * 
	 * @param collection
	 *            Collection
	 * @return String[]
	 */
	public static String[] toStringArray(Collection collection) {
		List strs = new LinkedList(collection);
		String str[] = new String[strs.size()];
		for (int i = 0; i < strs.size(); i++)
			str[i] = (String) strs.get(i);

		return str;
	}

	/**
	 * The string "false", "NO" and "0" mean false, and true if other.
	 * 
	 * @param str
	 *            String
	 * @return boolean
	 */
	public static boolean toBoolean(String str) {
		if (str == null)
			return false;

		str = str.trim().toUpperCase();
		if ("FALSE".equals(str) || "NO".equals(str) || "0".equals(str)
				|| "NOT".equals(str))
			return false;

		return true;
	}
	public static  String replaceBackSlashWithSlash(String filePath){
		int index= -1;
		if((index=filePath.indexOf("\\"))<0)
			return filePath;
		String prefix = filePath.substring(0,index);
		String postfix = filePath.substring(index+1,filePath.length());
		filePath = prefix+"/"+postfix;
		return StringUtil.replaceBackSlashWithSlash(filePath);
	}
	public static String substring(String splitStr, int bytes) {
		int cutLength = 0;
		int byteNum = bytes;
		byte bt[] = splitStr.getBytes();
		if (bytes > 1) {
			for (int i = 0; i < byteNum; i++) {
				if (bt[i] < 0) {
					cutLength++;

				}
			}

			if (cutLength % 2 == 0) {
				cutLength /= 2;
			} else {
				cutLength = 0;
			}
		}
		int result = cutLength + --byteNum;
		if (result > bytes) {
			result = bytes;
		}
		if (bytes == 1) {
			if (bt[0] < 0) {
				result += 2;

			} else {
				result += 1;
			}
		}
		String substrx = new String(bt, 0, result);
		return substrx;
	}
	
	
	public static String utf8ToUnicode(String inStr) {
		char[] myBuffer = inStr.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < inStr.length(); i++) {
			UnicodeBlock ub = UnicodeBlock.of(myBuffer[i]);
			if(ub == UnicodeBlock.BASIC_LATIN){
				//英文及数字等
				sb.append(myBuffer[i]);
			}else if(ub == UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS){
				//全角半角字符
				int j = (int) myBuffer[i] - 65248;
				sb.append((char)j);
			}else{
				//汉字
				int s = (int) myBuffer[i];
				String hexS = Integer.toHexString(s);
				String unicode = "\\u"+hexS;
				sb.append(unicode.toLowerCase());
			}
		}
		return sb.toString();
	}
	
	public static String unicodeToUtf8(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
				if (aChar == '\\') {
					aChar = theString.charAt(x++);
					if (aChar == 'u') {
						// Read the xxxx
						int value = 0;
						for (int i = 0; i < 4; i++) {
							aChar = theString.charAt(x++);
							switch (aChar) {
							case '0':
							case '1':
							case '2':
							case '3':
							case '4':
							case '5':
							case '6':
							case '7':
							case '8':
							case '9':
								value = (value << 4) + aChar - '0';
								break;
							case 'a':
							case 'b':
							case 'c':
							case 'd':
							case 'e':
							case 'f':
								value = (value << 4) + 10 + aChar - 'a';
								break;
							case 'A':
							case 'B':
							case 'C':
							case 'D':
							case 'E':
							case 'F':
								value = (value << 4) + 10 + aChar - 'A';
								break;
							default:
								throw new IllegalArgumentException(
								"Malformed\\uxxxx encoding.");
							}
						}
						outBuffer.append((char) value);
					} else {
						if (aChar == 't')
							aChar = '\t';
						else if (aChar == 'r')
							aChar = '\r';
						else if (aChar == 'n')
							aChar = '\n';
						else if (aChar == 'f')
							aChar = '\f';
						outBuffer.append(aChar);
					}
				} else
					outBuffer.append(aChar);
			}
			return outBuffer.toString();
		}
	
	public static String getMD5String(String str){
		String md5Str = null;         
	    if (str != null && str.length() != 0) {            
	     try {                 
	      MessageDigest md = MessageDigest.getInstance("MD5");                
	      md.update(str.getBytes());                 
	      byte b[] = md.digest();                     
	     
	      StringBuffer buf = new StringBuffer("");                 
	      for (int offset = 0; offset < b.length; offset++) {                
	       int i = b[offset];                    
	       if (i < 0)                        
	        i += 256;                    
	       if (i < 16)
	        buf.append("0");
	       buf.append(Integer.toHexString(i));                
	       }              
	      //32位
	      md5Str = buf.toString();               
	      //16位   md5Str = buf.toString().substring(8, 24);            
	      } catch (NoSuchAlgorithmException e) {           
	       e.printStackTrace();             
	      }  
	    }
	 return md5Str;
	}
	public static boolean checkMailFormat(String mailAddress){
		String regex = "(\\w[\\w\\.\\-]*)@\\w[\\w\\-]*[\\.(com|cn|org|edu|hk)]+[a-z]$";
		return Pattern.matches(regex, mailAddress);
	}
	
	public static boolean validateMoblie(String phone) {
		   int l = phone.length();
		   boolean rs=false;
		   switch (l) {
		   case 7:
		    if (matchingText("^(13[0-9]|15[0-9]|18[7|8|9|6|5])\\d{4}$", phone)) {
		     rs= true;
		    }
		    break;
		   case 11:
		    if (matchingText("^(13[0-9]|15[0-9]|18[7|8|9|6|5])\\d{4,8}$", phone)) {
		     rs= true;
		    }
		    break;
		   default:
		    rs=false;
		    break;
		   }
		   return rs;
		}

		private static boolean matchingText(String expression, String text) {
		   Pattern p = Pattern.compile(expression); // 正则表达式
		   Matcher m = p.matcher(text); // 操作的字符串
		   boolean b = m.matches();
		   return b;
		}
		
	public static int strlen(String str){ 
        if   (str==null||str.length()<=0){ 
            return   0; 
        } 
        int len=0; 

        char c; 
        for   (int   i=str.length()-1;   i>=0;   i--){ 
            c   =   str.charAt(i); 
            if   ((c>='0'&&c<='9')||(c>='a'&&c<='z')||(c>='A'&&c<='Z')){ 
                len++; 
            } 
            else{ 
                if   (Character.isLetter(c)){	//中文 
                    len+=2; 
                }else{	 //符号或控制字符 
                    len++; 
                } 
            } 
        } 
        return  len; 
	} 
	
public static String changeStreamToString(InputStream stream, String encode) throws IOException {
		
		byte[] b100k=new byte[200000];
		
		int pos=0;
		while(true){
			int len = stream.read(b100k,pos,b100k.length-pos);
			if  (len<=0) break;
			else pos = pos+len;
		}
		
		if (pos >= b100k.length-1) {
			throw new IOException("ERROR:The stream size is more than "+b100k.length+" bytes");
		}
		
		return new String(b100k,0,pos,encode);
	}

public static String concat(Object... args) {
	StringBuffer buf = new StringBuffer();
	for (Object arg : args) {
		buf.append(arg);
	}
	return buf.toString();
}
}
