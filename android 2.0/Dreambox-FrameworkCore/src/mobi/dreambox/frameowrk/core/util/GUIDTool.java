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

package mobi.dreambox.frameowrk.core.util;

public class GUIDTool
{
  public static String genGUID()
  {
    String s1 = null;
    try {
      GUIDGenerator gen = new GUIDGenerator();
      s1 = gen.getUUID();
    }
    catch (Exception e)
    {
    }
    return s1;
  }

  public static void main(String[] args)
  {
    GUIDTool GUIDTool1 = new GUIDTool();
  }
}
