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

package mobi.dreambox.frameowrk.core.lifecycle;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import mobi.dreambox.frameowrk.core.constant.DboxConstants;
import mobi.dreambox.frameowrk.core.context.SystemContext;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;


/**<p>
 * Title: w3studio_core_组件管理类
 * </p>
 * <p>
 * Description: 管理加载的所有组件
 * </p>
 * 
 * @author caven
 * @version $Revision$ May 22, 2012
 * @author (lastest modification by $Author$)
 * @since 20100620
 */
public class DboxFrameworkComponentManager {
	public List<String> getTableConfigFileList(){
		List<String> respList = null;
		InputStream systemInputStream;
		try {
			systemInputStream = SystemContext.getInstance().getContext().getAssets().open(DboxConstants.COMPONENT_CONFIG);
			XmlPullParser parser = Xml.newPullParser();
			try {
				parser.setInput(systemInputStream, "UTF-8");
				int eventType = parser.getEventType();
				while (eventType != XmlPullParser.END_DOCUMENT) {
					switch (eventType) {
						case XmlPullParser.START_DOCUMENT://文档开始事件,可以进行数据初始化处理
							break;
						case XmlPullParser.START_TAG://开始元素事件
							String name = parser.getName();
							if (name.equalsIgnoreCase("config")) {
								if(respList==null)
									respList = new ArrayList<String>();
								respList.add(parser.nextText());
							}
							break;
						case XmlPullParser.END_TAG://结束元素事件
							break;
					}
					eventType = parser.next();
				}
			} catch (XmlPullParserException e) {
				
			} catch (IOException e) {

			} finally{
				
			}
		} catch (IOException e) {
			
		}
		return respList;
	}
	
	public List<String> getComponentLifeCycleClass(){
		List<String> respList = null;
		InputStream systemInputStream;
		try {
			systemInputStream = SystemContext.getInstance().getContext().getAssets().open(DboxConstants.COMPONENT_CONFIG);
			XmlPullParser parser = Xml.newPullParser();
			try {
				parser.setInput(systemInputStream, "UTF-8");
				int eventType = parser.getEventType();
				while (eventType != XmlPullParser.END_DOCUMENT) {
					switch (eventType) {
						case XmlPullParser.START_DOCUMENT://文档开始事件,可以进行数据初始化处理
							break;
						case XmlPullParser.START_TAG://开始元素事件
							String name = parser.getName();
							if (name.equalsIgnoreCase("lifecycleclass")) {
								if(respList==null)
									respList = new ArrayList<String>();
								respList.add(parser.nextText());
							}
							break;
						case XmlPullParser.END_TAG://结束元素事件
							break;
					}
					eventType = parser.next();
				}
			} catch (XmlPullParserException e) {
				
			} catch (IOException e) {

			} finally{
				
			}
		} catch (IOException e) {
			
		}
		return respList;
	}
}
