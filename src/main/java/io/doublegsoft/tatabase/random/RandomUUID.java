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
import java.util.UUID;

/**
 * It is the random uppercased uuid string implementing the {@link RandomResource} interface.
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 1.0
 */
public class RandomUUID implements RandomResource<String> {

  @Override
  public List<String> random(int count) {
    List<String> retVal = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      retVal.add(UUID.randomUUID().toString().toUpperCase());
    }
    return retVal;
  }

}
