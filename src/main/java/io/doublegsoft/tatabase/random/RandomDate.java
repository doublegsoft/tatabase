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
package io.doublegsoft.tatabase.random;

import com.doublegsoft.jcommons.utils.Dates;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * It is the random date implementing the {@link RandomResource} interface.
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 1.0
 */
public class RandomDate implements RandomResource<Date> {

  private final Date seedStart;

  private final Date seedEnd;

  public RandomDate(Date seedStart, Date seedEnd) {
    this.seedStart = seedStart;
    this.seedEnd = seedEnd;
  }

  public RandomDate(String seedStart, String seedEnd) {
    this(Dates.valueOf(seedStart), Dates.valueOf(seedEnd));
  }

  @Override
  public List<Date> random(int count) {
    List<Date> retVal = new ArrayList<>();
    long start = seedStart.getTime();
    long end = seedEnd == null ? start : seedEnd.getTime();
    Random rand = new Random();
    for (int i = 0; i < count; i++) {
      long time = rand.longs(start, end).findFirst().getAsLong();
      retVal.add(new Date(time));
    }
    return retVal;
  }

}
