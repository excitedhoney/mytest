/** 
* 
 * Copyright (c) 1995-2012 Wonders Information Co.,Ltd. 
 * 1518 Lianhang Rd,Shanghai 201112.P.R.C.
 * All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Wonders Group.
 * (Social Security Department). You shall not disclose such
 * Confidential Information and shall use it only in accordance with 
 * the terms of the license agreement you entered into with Wonders Group. 
 *
 * Distributable under GNU LGPL license by gnu.org
 */

package mobi.dreambox.frameowrk.core.http;

import java.io.InputStream;
import java.util.List;
import java.util.Map;


/**<p>
 * Title: w3studio_[子系统统名]_[模块名]
 * </p>
 * <p>
 * Description: HTTP请求中返回的数据全部转换成此类型
 * </p>
 * 
 * @author caven
 * @version $Revision$ May 18, 2012
 * @author (lastest modification by $Author$)
 * @since 20100620
 */
public abstract class  DboxHttpResponse {
	protected DboxHttpResponse(InputStream in){
	}
	protected DboxHttpResponse(){
		
	}
	
	public abstract Object getResponseData();
	private Map<String,List<String>> respHeader;
	private int httpStatus;
	public Map<String, List<String>> getRespHeader() {
		return respHeader;
	}

	public void setRespHeader(Map<String, List<String>> respHeader) {
		this.respHeader = respHeader;
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}
	
	
}
