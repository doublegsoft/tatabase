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

import com.doublegsoft.jcommons.lang.HashObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * It is the random model object reference like rdbms foreign key implementing the {@link RandomResource} interface.
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 1.0
 */
public class RandomReference<T> implements RandomResource<T> {

  private final HashObject context;

  private final String objName;

  private final String attrName;

  public RandomReference(HashObject context, String objName, String attrName) {
    this.context = context;
    this.objName = objName;
    this.attrName = attrName;
  }

  @Override
  public List<T> random(int count) {
    List<T> values = null;
    List<HashObject> objs = context.get("objs");
    for (HashObject obj : objs) {
      boolean found = false;
      if (objName.equals(obj.get("name"))) {
        List<HashObject> attrs = obj.get("attrs");
        for (HashObject attr : attrs) {
          if (attrName.equals(attr.get("name"))) {
            found = true;
            values = attr.get("values");
            break;
          }
        }
      }
      if (found) {
        break;
      }
    }
    if (values == null) {
      throw new NullPointerException("not found the values: " + objName + ":" + attrName);
    }
    List<T> retVal = new ArrayList<>();
    int bound = values.size();
    Random rand = new Random();
    for (int i = 0; i < count; i++) {
      int index = rand.ints(0, bound).findAny().getAsInt();
      retVal.add(values.get(index));
    }
    return retVal;
  }

}
