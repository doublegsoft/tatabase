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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 1.0
 */
public class NamedEntityNumber {
  
  public List<String> get(int count, Number min, Number max) {
    List<String> retVal = new ArrayList<>();
    Random rand = new Random();
    for (int i = 0; i < count; i++) {
      double dbl = rand.doubles(min.doubleValue(), max.doubleValue()).findFirst().getAsDouble();
      retVal.add(new BigDecimal(dbl).setScale(4, RoundingMode.HALF_UP).toPlainString());
    }
    return retVal;
  }
  
}
