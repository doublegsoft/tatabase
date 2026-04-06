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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * It is the random number implementing the {@link RandomResource} interface.
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 1.0
 */
public class RandomNumber implements RandomResource<BigDecimal> {

  private final double min;

  private final double max;

  private final int precision;

  public RandomNumber(double min, double max, int precision) {
    this.min = min;
    this.max = max;
    this.precision = precision;
  }

  @Override
  public List<BigDecimal> random(int count) {
    List<BigDecimal> retVal = new ArrayList<>();
    Random rand = new Random();
    for (int i = 0; i < count; i++) {
      double dbl = rand.doubles(min, max).findFirst().getAsDouble();
      retVal.add(new BigDecimal(dbl).setScale(precision, RoundingMode.HALF_UP));
    }
    return retVal;
  }

}
