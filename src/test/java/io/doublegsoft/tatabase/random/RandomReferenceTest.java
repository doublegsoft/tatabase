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
import java.util.Arrays;
import org.junit.Test;

/**
 * Test for {@link RandomEnumeration}.
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 1.0
 */
public class RandomReferenceTest {

  @Test
  public void test_string() throws Exception {
    HashObject context = new HashObject();
    HashObject obj = new HashObject();
    HashObject attr = new HashObject();
    obj.set("name", "obj1");
    attr.set("name", "attr1");
    obj.add("attrs", attr);
    attr.set("values", Arrays.asList("A", "B", "C", "D"));
    context.add("objs", obj);
    new RandomReference<>(context, "obj1", "attr1").random(100).forEach(val -> {
      System.out.println(val);
    });
  }

  @Test
  public void test_long() throws Exception {
    HashObject context = new HashObject();
    HashObject obj = new HashObject();
    HashObject attr = new HashObject();
    obj.set("name", "obj1");
    attr.set("name", "attr1");
    obj.add("attrs", attr);
    attr.set("values", Arrays.asList(1L, 2L, 3L, 4L));
    context.add("objs", obj);
    new RandomReference<>(context, "obj1", "attr1").random(100).forEach(val -> {
      System.out.println(val);
    });
  }

}
