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

import io.doublegsoft.tatabase.random.RandomEnumeration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * Test for {@link RandomEnumeration}.
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 1.0
 */
public class RandomEnumerationTest {

  @Test
  public void test() throws Exception {
    List<String> values = new ArrayList<>();
    values.addAll(Arrays.asList("A", "B", "C", "D"));
    new RandomEnumeration(values).random(100).forEach(enmr -> {
      System.out.println(enmr);
    });
  }

}
