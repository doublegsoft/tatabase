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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.doublegsoft.appbase.util.Dates;

/**
 * It is the domain date sequence implementing the {@link DomainResource} interface.
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 1.0
 */
public class DomainDateSequence implements DomainResource<Date> {

  private final Date seedStart;

  private final int duration;

  private final int field;

  public DomainDateSequence(Date seedStart, int duration, int field) {
    this.seedStart = seedStart;
    this.duration = duration;
    this.field = field;
  }

  public DomainDateSequence(String seedStart, int duration, int field) {
    this(Dates.valueOf(seedStart), duration, field);
  }

  @Override
  public List<Date> domain(int count) {
    List<Date> retVal = new ArrayList<>();
    Date date = seedStart;
    retVal.add(date);
    for (int i = 0; i < count; i++) {
      date = Dates.increment(date, field, duration);
      retVal.add(date);
    }
    return retVal;
  }

}
