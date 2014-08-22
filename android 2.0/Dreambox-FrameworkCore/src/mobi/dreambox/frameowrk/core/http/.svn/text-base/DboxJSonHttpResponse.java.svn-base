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

import mobi.dreambox.frameowrk.core.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;


/**<p>
 * Title: w3studio_[子系统统名]_[模块名]
 * </p>
 * <p>
 * Description: [描述该类概要功能介绍]
 * </p>
 * 
 * @author caven
 * @version $Revision$ May 21, 2012
 * @author (lastest modification by $Author$)
 * @since 20100620
 */
public class DboxJSonHttpResponse extends DboxHttpResponse{
	private JSONObject respObj;
	public DboxJSonHttpResponse(InputStream in) throws DboxHTTPClientException{
		super(in);
		try {
			respObj = new JSONObject(StringUtil.toString(in));
		}
		catch (JSONException e) {
			throw new DboxHTTPClientException("", "");
		}
	}
	
	public DboxJSonHttpResponse(){
		super();
	}

	/** 
	 * @throws DboxHTTPClientException 
	 * @see mobi.dreambox.frameowrk.core.http.DboxHttpResponse#getResponseData()
	 */
	@Override
	public JSONObject getResponseData() {
		return respObj;
	}

	



}
