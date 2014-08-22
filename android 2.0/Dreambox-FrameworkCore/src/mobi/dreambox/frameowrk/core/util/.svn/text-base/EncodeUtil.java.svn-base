package mobi.dreambox.frameowrk.core.util;

/***
 * <p>
 * Title: 字符串编解码
 * </p>
 * <p>
 * Description: 提供字符串编解码工具方法
 * </p>
 * 
 * @author caven
 * @version $Revision$ Sep 3, 2012
 * @author (lastest modification by $Author$)
 * @since 20100620
 */
public class EncodeUtil {
	/***
	 * <p>
	 * Description:utf8转城unicode
	 * </p>
	 * 
	 * @param str
	 * @return
	 */
	public static String utf8ToUnicode(String str){
		return StringUtil.utf8ToUnicode(str);
	}
	/***
	 * <p>
	 * Description:unicode转成utf8
	 * </p>
	 * 
	 * @param str
	 * @return
	 */
	public static String unicodeToUtf8(String str){
		return StringUtil.unicodeToUtf8(str);
	}
	
	/***
	 * <p>
	 * Description:dbox自定义编码
	 * </p>
	 * 
	 * @param str
	 * @return
	 */
	public static String dboxEncode(String str){
		
		
		return CodecUtils.EncodeBytes(CodecUtils.byteToBase64(str.getBytes()).getBytes());
	}
	/***
	 * <p>
	 * Description:dbox自定义解码
	 * </p>
	 * 
	 * @param str
	 * @return
	 */
	public static String dboxDecode(String str){
		return new String(CodecUtils.base64ToByte(CodecUtils.DecodeBytes(str)));
	}
	/***
	 * <p>
	 * Description:base64编码
	 * </p>
	 * 
	 * @param str
	 * @return
	 */
	public static String base64Encode(String str){
		return CodecUtils.byteToBase64(str.getBytes());
	}
	/***
	 * <p>
	 * Description:base64解码
	 * </p>
	 * 
	 * @param str
	 * @return
	 */
	public static String base64Decode(String str){
		return new String(CodecUtils.base64ToByte(str));
	}
	
	
}
