package mobi.dreambox.frameowrk.core.util;

import java.security.SecureRandom;
import java.util.Random;


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
public class GUIDUtil {
	private static String GUID_BASE_PART = null;
    private static long GUID_NUMBER_PART = 0;
    private static volatile SecureRandom numberGenerator = null;

    /**
     * Generate a id.
     *
     * @return String
     */
    public synchronized static String generateID() {
        if(StringUtil.isNull(GUIDUtil.GUID_BASE_PART)
				|| GUIDUtil.GUID_NUMBER_PART + 1 > Long.MAX_VALUE) {
			GUIDUtil.GUID_BASE_PART = GUIDTool.genGUID();
			GUIDUtil.GUID_NUMBER_PART = 1;
		}

 	    return GUIDUtil.GUID_BASE_PART + GUIDUtil.GUID_NUMBER_PART++;
    }
    
    public synchronized static long generateLongID() {
        SecureRandom ng = numberGenerator;
        if (ng == null) {
            numberGenerator = ng = new SecureRandom();
        }

        byte[] randomBytes = new byte[16];
        ng.nextBytes(randomBytes);
        randomBytes[6]  &= 0x0f;  /* clear version        */
        randomBytes[6]  |= 0x40;  /* set to version 4     */
        randomBytes[8]  &= 0x3f;  /* clear variant        */
        randomBytes[8]  |= 0x80;  /* set to IETF variant  */

        long longID = 0;
        Random random = new Random();
        int n = 0;
        for (int i=0; i < 16; i++) {
        	n = random.nextInt(16);
        	longID = (longID << n) | (randomBytes[i] & 0xff);
        }
        return longID;
    }
}
