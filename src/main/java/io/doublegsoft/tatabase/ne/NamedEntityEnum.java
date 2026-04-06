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

import com.doublegsoft.jcommons.lang.StringPair;
import io.doublegsoft.typebase.Typebase;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a> 
 * 
 * @since 4.0
 */
public class NamedEntityEnum {
   
  public static final Typebase TYPEBASE = new Typebase();
  
  public List<String> get(int count, String expr) {
    List<String> retVal = new ArrayList<>();
    List<StringPair> pairs = TYPEBASE.enumtype(expr);
    Random rand = new Random();
    for (int i = 0; i < count; i++) {
      StringPair pair = pairs.get(rand.nextInt(pairs.size()));
      retVal.add(pair.getKey());
    }
    return retVal;
  }
  
}
