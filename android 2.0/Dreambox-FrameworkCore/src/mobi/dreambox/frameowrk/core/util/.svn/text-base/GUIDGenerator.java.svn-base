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

import java.io.PrintStream;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.security.SecureRandom;

public class GUIDGenerator
{
  private long checkTime;
  private String midValue;
  private String midValueUnformated;
  private SecureRandom seeder;

  public GUIDGenerator()
    throws Exception
  {
    create();
  }

  public void create() throws Exception {
    try {
      StringBuffer stringbuffer = new StringBuffer();
      StringBuffer stringbuffer1 = new StringBuffer();
      this.seeder = new SecureRandom();
      InetAddress inetaddress = InetAddress.getLocalHost();
      byte[] abyte0 = inetaddress.getAddress();
      String s = hexFormat(getInt(abyte0), 8);
      String s1 = hexFormat(hashCode(), 8);

      stringbuffer1.append(s.substring(0, 4));
      stringbuffer.append(s.substring(0, 4));

      stringbuffer1.append(s.substring(4));
      stringbuffer.append(s.substring(4));

      stringbuffer1.append(s1.substring(0, 4));
      stringbuffer.append(s1.substring(0, 4));

      stringbuffer1.append(s1.substring(4));
      stringbuffer.append(s1.substring(4));
      this.midValue = stringbuffer.toString();
      this.midValueUnformated = stringbuffer1.toString();
      this.checkTime = System.currentTimeMillis();
    }
    catch (Exception exception)
    {
      int i;
      throw new Exception("error - failure to create bean " + exception);
    }
  }

  private int getInt(byte[] abyte0)
  {
    int i = 0;
    int j = 24;
    for (int k = 0; j >= 0; k++) {
      int l = abyte0[k] & 0xFF;
      i += (l << j);
      j -= 8;
    }

    return i;
  }

  public String getUUID()
    throws RemoteException
  {
    return getVal(this.midValue);
  }

  public String getUnformatedUUID()
    throws RemoteException
  {
    return getVal(this.midValueUnformated);
  }

  private String getVal(String s)
    throws RemoteException
  {
    long l = System.currentTimeMillis();
    int i = (int)l & 0xFFFFFFFF;
    int j = this.seeder.nextInt();
    return hexFormat(i, 8) + s + hexFormat(j, 8);
  }

  private String hexFormat(int i, int j)
  {
    String s = Integer.toHexString(i);
    return padHex(s, j) + s;
  }

  public static void main(String[] args)
  {
    try
    {
      GUIDGenerator guid = new GUIDGenerator();
      System.out.println("GUID:" + guid.getUUID());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private String padHex(String s, int i)
  {
    StringBuffer stringbuffer = new StringBuffer();
    if (s.length() < i) {
      for (int j = 0; j < i - s.length(); j++) {
        stringbuffer.append("0");
      }
    }
    return stringbuffer.toString();
  }
}