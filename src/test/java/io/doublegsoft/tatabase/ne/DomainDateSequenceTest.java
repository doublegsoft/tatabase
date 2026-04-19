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
import io.doublegsoft.tatabase.ne.DomainDateSequence;
import java.util.Calendar;
import org.junit.Test;

/**
 * Test for {@link DomainDateSequence}.
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 1.0
 */
public class DomainDateSequenceTest {

  @Test
  public void test() throws Exception {
    new DomainDateSequence("2017-01-01", 1, Calendar.HOUR_OF_DAY).domain(200).forEach(date -> {
      System.out.println(Dates.stringify(date, Calendar.SECOND));
    });
  }

}
