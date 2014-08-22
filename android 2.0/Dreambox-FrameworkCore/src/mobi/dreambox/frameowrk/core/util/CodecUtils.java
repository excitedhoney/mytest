package mobi.dreambox.frameowrk.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.codec.binary.Base64;

public class CodecUtils {
	private static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	  private static int BUFFER_SIZE = 32768;
	  private static int BUFFER_L_SIZE = 2;

	  private static String byteToHexString(byte b)
	  {
	    int n = b;
	    if (n < 0) {
	      n = 256 + n;
	    }
	    int d1 = n / 16;
	    int d2 = n % 16;
	    return hexDigits[d1] + hexDigits[d2];
	  }

	  private static String toBase64(byte b1, byte b2, byte b3) {
	    int[] digit = new int[4];
	    digit[0] = ((b1 & 0xFC) >>> 2);
	    digit[1] = ((b1 & 0x3) << 4);
	    digit[1] |= (b2 & 0xF0) >> 4;
	    digit[2] = ((b2 & 0xF) << 2);
	    digit[2] |= (b3 & 0xC0) >> 6;
	    digit[3] = (b3 & 0x3F);
	    String result = "";
	    for (int i = 0; i < digit.length; i++) {
	      result = result + base64Digit(digit[i]);
	    }
	    return result;
	  }

	  private static String toBase64(byte b1, byte b2) {
	    int[] digit = new int[3];
	    digit[0] = ((b1 & 0xFC) >>> 2);
	    digit[1] = ((b1 & 0x3) << 4);
	    digit[1] |= (b2 & 0xF0) >> 4;
	    digit[2] = ((b2 & 0xF) << 2);
	    String result = "";
	    for (int i = 0; i < digit.length; i++) {
	      result = result + base64Digit(digit[i]);
	    }
	    result = result + "=";
	    return result;
	  }

	  private static String toBase64(byte b1) {
	    int[] digit = new int[2];
	    digit[0] = ((b1 & 0xFC) >>> 2);
	    digit[1] = ((b1 & 0x3) << 4);
	    String result = "";
	    for (int i = 0; i < digit.length; i++) {
	      result = result + base64Digit(digit[i]);
	    }
	    result = result + "==";
	    return result;
	  }

	  private static char base64Digit(int i) {
	    if (i < 26) {
	      return (char)(65 + i);
	    }
	    if (i < 52) {
	      return (char)(97 + (i - 26));
	    }
	    if (i < 62) {
	      return (char)(48 + (i - 52));
	    }
	    if (i == 62) {
	      return '+';
	    }

	    return '/';
	  }

	  private static String byteArrayToBase64String(byte[] b, int len)
	  {
	    String s = "";
	    int n = len / 3;
	    int m = len % 3;
	    for (int i = 0; i < n; i++) {
	      int j = i * 3;
	      s = s + toBase64(b[j], b[(j + 1)], b[(j + 2)]);
	    }
	    if (m == 1) {
	      s = s + toBase64(b[(len - 1)]);
	    }
	    else if (m == 2) {
	      s = s + toBase64(b[(len - 2)], b[(len - 1)]);
	    }
	    String result = "";
	    len = s.length();
	    n = len / 64;
	    m = len % 64;
	    for (int i = 0; i < n; i++) {
	      result = result + s.substring(i * 64, (i + 1) * 64);
	    }
	    if (m > 0) {
	      result = result + s.substring(n * 64, len);
	    }
	    return result;
	  }

	  private static byte[] base64ToBytes(String s) {
	    int len = 0;
	    for (int i = 0; i < s.length(); i++) {
	      if (s.charAt(i) != '=') {
	        len++;
	      }
	    }
	    int[] digit = new int[len];
	    for (int i = 0; i < len; i++) {
	      char c = s.charAt(i);
	      if ((c >= 'A') && (c <= 'Z')) {
	        digit[i] = (c - 'A');
	      }
	      else if ((c >= 'a') && (c <= 'z')) {
	        digit[i] = (c - 'a' + 26);
	      }
	      else if ((c >= '0') && (c <= '9')) {
	        digit[i] = (c - '0' + 52);
	      }
	      else if (c == '+') {
	        digit[i] = 62;
	      }
	      else if (c == '/') {
	        digit[i] = 63;
	      }
	    }
	    byte[] b = new byte[len - 1];
	    switch (len) {
	    case 4:
	      b[2] = (byte)((digit[2] & 0x3) << 6 | digit[3]);
	    case 3:
	      b[1] = (byte)((digit[1] & 0xF) << 4 | (digit[2] & 0x3C) >>> 2);
	    case 2:
	      b[0] = (byte)(digit[0] << 2 | (digit[1] & 0x30) >>> 4);
	    }
	    return b;
	  }

	  private static String byteArrayToHexString(byte[] b) {
	    String result = "";
	    for (int i = 0; i < b.length; i++) {
	      result = result + byteToHexString(b[i]);
	    }
	    return result;
	  }

	  public static byte[] DecodeBytes(byte[] inBytes) {
	    try {
	      return gunzip(base64ToByte(inBytes)); } catch (Exception e) {
	    }
	    return null;
	  }

	  public static byte[] DecodeBytes(String inStr) {
	    try {
	      return gunzip(base64ToByte(inStr)); } catch (Exception e) {
	    }
	    return null;
	  }

	  public static String EncodeBytes(byte[] inBytes) {
	    try {
	      return byteToBase64(gzip(inBytes));
	    } catch (Exception e) {
	    }
	    return null;
	  }

	  public static String generateVerifyCode(byte[] inBytes) {
	    byte[] b = new byte[inBytes.length + 7];
	    int len = inBytes.length;
	    System.arraycopy(inBytes, 0, b, 3, len);
	    b[0] = 119; b[1] = 111; b[2] = 110;
	    b[(len++)] = 100; b[(len++)] = 101; b[(len++)] = 114; b[(len++)] = 115;
	    return md5Byte(b).toLowerCase();
	  }
	  public static String generateVerifyCode(String inStr) {
	    return md5String("won" + inStr + "ders").toLowerCase();
	  }
	  public static boolean verifyCode(String inStr, String inCode) {
	    return inCode.equals(generateVerifyCode(inStr));
	  }
	  public static boolean verifyCode(byte[] inBytes, String inCode) {
	    return inCode.equals(generateVerifyCode(inBytes));
	  }

	  public static String byteToBase64(byte[] buf)
	  {
	    if (buf == null) return "";
	    if (buf.length == 0) return "";
	    return new String(Base64.encodeBase64(buf));
	  }

	  public static byte[] base64ToByte(String s) {
	    if (s == null) return new byte[0];
	    if (s.length() == 0) return new byte[0];
	    return Base64.decodeBase64(s.getBytes());
	  }
	  public static byte[] base64ToByte(byte[] buf) {
	    if (buf == null) return new byte[0];
	    if (buf.length == 0) return new byte[0];
	    return Base64.decodeBase64(buf);
	  }

	  public static byte[] gzip(byte[] buf)
	  {
	    try
	    {
	      ByteArrayOutputStream byteOs = new ByteArrayOutputStream();
	      BufferedOutputStream bos = new BufferedOutputStream(new GZIPOutputStream(byteOs));

	      bos.write(buf);
	      bos.flush();
	      bos.close();
	      byte[] b = byteOs.toByteArray();
	      byteOs.close();
	      return b;
	    }
	    catch (IOException e) {
	      e.printStackTrace();
	    }return null;
	  }

	  public static int gzipFile(String inFilename, String outFilename) {
	    try {
	      RandomAccessFile brIn = new RandomAccessFile(inFilename, "r");
	      BufferedOutputStream bosOut = new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(outFilename)));

	      int blockLen = 4096;
	      int realLen = 0;
	      byte[] b = new byte[blockLen];
	      while (true) {
	        realLen = brIn.read(b, 0, blockLen);
	        if (realLen <= 0) break;
	        bosOut.write(b, 0, realLen);
	      }
	      brIn.close(); bosOut.close();
	      return 0;
	    } catch (IOException e) {
	    }
	    return -1;
	  }

	  public static byte[] gunzip(byte[] buf)
	  {
	    try
	    {
	      ByteArrayOutputStream byteOs = new ByteArrayOutputStream();
	      BufferedOutputStream out = new BufferedOutputStream(byteOs);
	      BufferedInputStream in = new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(buf)));

	      int i = 0;
	      while ((i = in.read()) != -1) {
	        out.write(i);
	      }
	      in.close();
	      out.flush();
	      out.close();
	      byte[] b = byteOs.toByteArray();
	      byteOs.close();
	      return b;
	    }
	    catch (IOException e) {
	      e.printStackTrace();
	    }return null;
	  }

	  public static int gunzipFile(String inFilename, String outFilename) {
	    try {
	      BufferedInputStream brIn = new BufferedInputStream(new GZIPInputStream(new FileInputStream(inFilename)));

	      RandomAccessFile brOut = new RandomAccessFile(outFilename, "w");

	      int blockLen = 4096;
	      byte[] b = new byte[blockLen];
	      int realLen = 0;
	      while (true) {
	        realLen = brIn.read(b, 0, blockLen);
	        if (realLen <= 0) break;
	        brOut.write(b, 0, realLen);
	      }
	      brIn.close(); brOut.close();
	      return 0;
	    } catch (IOException e) {
	    }
	    return -1;
	  }

	  public static byte[] zip(byte[] buf)
	  {
	    try
	    {
	      ByteArrayOutputStream byteOs = new ByteArrayOutputStream();
	      ZipOutputStream Zouts = new ZipOutputStream(byteOs);
	      Zouts.putNextEntry(new ZipEntry("z"));
	      BufferedOutputStream bos = new BufferedOutputStream(Zouts);
	      bos.write(buf);
	      bos.flush();
	      bos.close();
	      byte[] b = byteOs.toByteArray();
	      byteOs.close();
	      return b;
	    }
	    catch (IOException e) {
	      e.printStackTrace();
	    }return null;
	  }

	  public static byte[] unzip(byte[] buf)
	  {
	    try
	    {
	      ByteArrayOutputStream byteOs = new ByteArrayOutputStream();
	      BufferedOutputStream out = new BufferedOutputStream(byteOs);
	      ZipInputStream Zins = new ZipInputStream(new ByteArrayInputStream(buf));

	      Zins.getNextEntry();
	      BufferedInputStream in = new BufferedInputStream(Zins);

	      int i = 0;
	      while ((i = in.read()) != -1) {
	        out.write(i);
	      }
	      in.close();
	      out.flush();
	      out.close();
	      byte[] b = byteOs.toByteArray();
	      byteOs.close();
	      return b;
	    }
	    catch (IOException e) {
	      e.printStackTrace();
	    }return null;
	  }

//	  public static byte[] bzip(byte[] buf)
//	  {
//	    try
//	    {
//	      ByteArrayOutputStream byteOs = new ByteArrayOutputStream();
//	      CBZip2OutputStream z = new CBZip2OutputStream(byteOs);
//
//	      z.write(buf);
//	      z.flush(); z.close();
//	      byte[] b = byteOs.toByteArray();
//	      byteOs.close();
//	      byteOs = null; z = null;
//	      return b;
//	    }
//	    catch (IOException e) {
//	      e.printStackTrace();
//	    }return null;
//	  }
//
//	  public static byte[] bunzip(byte[] buf)
//	  {
//	    try
//	    {
//	      ByteArrayOutputStream byteOs = new ByteArrayOutputStream();
//
//	      ByteArrayInputStream bin = new ByteArrayInputStream(buf);
//	      CBZip2InputStream z = new CBZip2InputStream(bin);
//
//	      int i = 0;
//	      while ((i = z.read()) != -1) {
//	        byteOs.write(i);
//	      }
//	      byteOs.flush();
//	      byte[] b = byteOs.toByteArray();
//	      bin.close(); z.close();
//	      byteOs.close();
//	      bin = null; z = null;
//	      byteOs = null;
//	      return b;
//	    }
//	    catch (IOException e) {
//	      e.printStackTrace();
//	    }return null;
//	  }

	  public static String md5Byte(byte[] buf)
	  {
	    return ByteDigest(buf, "MD5");
	  }

	  public static String md5String(String s)
	  {
	    return StringDigest(s, "MD5");
	  }

	  public static String md5File(String filename)
	  {
	    return FileDigest(filename, "MD5");
	  }

	  public static String sha1Byte(byte[] buf)
	  {
	    return ByteDigest(buf, "SHA1");
	  }

	  public static String sha1String(String s)
	  {
	    return StringDigest(s, "SHA1");
	  }

	  public static String sha1File(String filename)
	  {
	    return FileDigest(filename, "SHA1");
	  }

	  public static String FileDigest(String filename, String arithmetic) { try { MessageDigest md = MessageDigest.getInstance(arithmetic);
	      DigestInputStream in = new DigestInputStream(new BufferedInputStream(new FileInputStream(filename)), md);

	      byte[] buffer = new byte[BUFFER_SIZE];
	      int i;
	      do {
	        i = in.read(buffer, 0, BUFFER_SIZE);
	      }
	      while (i == BUFFER_SIZE);
	      md = in.getMessageDigest();
	      in.close();
	      byte[] digest = md.digest();
	      return byteArrayToHexString(digest);
	    } catch (FileNotFoundException e)
	    {
	      return null;
	    }
	    catch (NoSuchAlgorithmException e) {
	      return null;
	    } catch (IOException e) {
	    }
	    return null; } 
	  public static String ByteDigest(byte[] buf, String arithmetic)
	  {
	    try {
	      MessageDigest md = MessageDigest.getInstance(arithmetic);
	      DigestInputStream in = new DigestInputStream(new BufferedInputStream(new ByteArrayInputStream(buf)), md);

	      byte[] buffer = new byte[BUFFER_SIZE];
	      int i;
	      do {
	        i = in.read(buffer, 0, BUFFER_SIZE);
	      }
	      while (i == BUFFER_SIZE);

	      md = in.getMessageDigest();
	      in.close();
	      byte[] digest = md.digest();
	      return byteArrayToHexString(digest);
	    } catch (Exception e) {
	    }
	    return null;
	  }

	  public static String StringDigest(String s, String arithmetic)
	  {
	    return ByteDigest(s.getBytes(), arithmetic);
	  }

	  private static short Chr2Hex(byte c)
	  {
	    if ((c >= 97) && (c <= 122))
	      c = (byte)(c - 97 + 65);
	    if ((c >= 48) && (c <= 57))
	      return (short)(c - 48);
	    if ((c >= 65) && (c <= 70)) {
	      return (short)(c - 65 + 10);
	    }
	    return -1;
	  }

	  public static byte[] QPDecode(byte[] buf) {
	    if (buf == null) return null;

	    byte[] tmpBuf = new byte[BUFFER_L_SIZE];
	    ByteArrayOutputStream bs = new ByteArrayOutputStream();
	    int bufLen = buf.length; int pos = -1;
	    for (int i = 0; i < bufLen; i++) {
	      if (buf[i] == 61) {
	        if (i < bufLen - 2) {
	          i += 2; pos++;
	          if (pos == BUFFER_L_SIZE) {
	            bs.write(tmpBuf, 0, pos); pos = 0;
	          }
	          tmpBuf[pos] = (byte)((byte)(Chr2Hex(buf[(i - 1)]) << 4) | Chr2Hex(buf[i]));
	        }
	      } else {
	        pos++;
	        if (pos == BUFFER_L_SIZE) {
	          bs.write(tmpBuf, 0, pos); pos = 0;
	        }
	        tmpBuf[pos] = buf[i];
	      }
	    }
	    if (pos >= 0) bs.write(tmpBuf, 0, pos + 1);
	    tmpBuf = bs.toByteArray();
	    try { bs.close(); } catch (Exception ee) {
	    }return tmpBuf;
	  }

	  public static byte[] QPDecode(String buf) {
	    return QPDecode(buf.getBytes());
	  }

	  public static String QPEncode(byte[] buf) {
	    StringBuffer s = new StringBuffer("");
	    byte b = 0;
	    int bufLen = buf.length;
	    for (int i = 0; i < bufLen; i++) {
	      b = buf[i];
	      if (b >= 0) { s.append((char)b);
	      } else {
	        s.append('='); s.append(Integer.toHexString(b).substring(6, 8).toUpperCase());
	      }
	    }
	    return s.toString();
	  }
	  public static String QPEncode(String buf) {
	    return QPEncode(buf.getBytes());
	  }
}
