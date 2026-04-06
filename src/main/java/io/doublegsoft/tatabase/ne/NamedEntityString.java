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
import java.util.List;
import java.util.Random;

/**
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a> 
 * 
 * @since 4.0
 */
public class NamedEntityString extends NamedEntity {
  
  public List<String> get(int count, Number maxLength) {
    if (maxLength == null || maxLength.intValue() == 0) maxLength = 5;
    Random rand = new Random();
    List<String> retVal = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      int length = 0;
      if (maxLength.intValue() <= 2) {
        length = 1;
      } else {
        length = rand.ints(2, maxLength.intValue()).findFirst().getAsInt();
      }
      StringBuilder str = new StringBuilder();
      while (str.length() < length) {
        str.append(get("/any/string"));
      }
      retVal.add(str.toString());
    }
    return retVal;
  } 
  
}
