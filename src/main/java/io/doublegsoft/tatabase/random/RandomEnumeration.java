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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * It is the random enumeration implementing the {@link RandomResource} interface.
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 1.0
 */
public class RandomEnumeration implements RandomResource<String> {

  private final List<String> values = new ArrayList<>();

  public RandomEnumeration(List<String> values) {
    this.values.addAll(values);
  }

  @Override
  public List<String> random(int count) {
    List<String> retVal = new ArrayList<>();
    int bound = values.size();
    Random rand = new Random();
    for (int i = 0; i < count; i++) {
      int index = rand.ints(0, bound).findAny().getAsInt();
      retVal.add(values.get(index));
    }
    return retVal;
  }

}
