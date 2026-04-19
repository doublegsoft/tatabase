/*
 * DOUBLEGSOFT.IO CONFIDENTIAL
 *
 * Copyright (C) doublegsoft.io
 *
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of doublegsoft.com and its suppliers, if any.
 * The intellectual and technical concepts contained herein
 * are proprietary to doublegsoft.com and its suppliers  and
 * may be covered by China and Foreign Patents, patents in
 * process, and are protected by trade secret or copyright law.
 *
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from doublegsoft.com.
 */
package io.doublegsoft.tatabase.ne;

import com.doublegsoft.jcommons.utils.Dates;
import com.ibm.icu.util.Calendar;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a> 
 * 
 * @since 4.0
 */
public class NamedEntityDateTime {
  
  public List<String> get(int count, Date previous) {
    List<String> retVal = new ArrayList<>();
    Date now = new Date();
    Random rand = new Random();
    for (int i = 0; i < count; i++) {
      int diff = rand.nextInt(10000);
      Date start = Dates.increment(now, Calendar.DATE, -diff);
      if (previous == null) {
        retVal.add(Dates.stringify(start, Calendar.SECOND));
      } else {
        diff = diff <= 0 ? 1 : diff;
        Date end = Dates.increment(previous, Calendar.DATE, diff);
        retVal.add(Dates.stringify(end, Calendar.SECOND));
      }
    }
    return retVal;
  }
  
}
