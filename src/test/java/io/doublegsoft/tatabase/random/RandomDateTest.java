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

import io.doublegsoft.tatabase.random.RandomDate;
import net.doublegsoft.appbase.util.Dates;
import org.junit.Test;

/**
 * Test for {@link RandomDate}.
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 1.0
 */
public class RandomDateTest {

  @Test
  public void test() throws Exception {
    new RandomDate("2017-01-01", "2017-12-31").random(200).forEach(date -> {
      System.out.println(Dates.stringify(date));
    });
  }

}
