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

/**<p>
 * Title: w3studio_core_组件生命周期实现接口
 * </p>
 * <p>
 * Description: 组件中定义的生命周期
 * </p>
 * 
 * @author caven
 * @version $Revision$ May 22, 2012
 * @author (lastest modification by $Author$)
 * @since 20100620
 */
public interface DboxComponentLifecycle {
	void init();
	void exit();
}