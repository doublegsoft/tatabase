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
package io.doublegsoft.tatabase;

import java.util.Map;
import org.junit.Test;

/**
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a> 
 * 
 * @since 4.0
 */
public class TatabaseTest {
  
  int COUNT = 1000;
  
  @Test
  public void story() {
    Tatabase tb = new Tatabase();
    
    tb.build("b", "id", "sequence", COUNT, "BB");
    tb.build("b", "name", "department", COUNT, null);
    
    tb.build("a", "id", "sequence", COUNT, "AA");
    tb.build("a", "name", "person", COUNT, null);
    tb.build("a", "dob", "date", COUNT, null);
    tb.build("a", "gender", "enum", COUNT, "enum[1: 男, 2: 女]");
    tb.build("a", "hiredate", "a#dob", COUNT, null);
    tb.build("a", "note", "string", COUNT, "50");
    tb.build("a", "department", "b#id", COUNT, null);
    
    for (Map<String, String> obj : tb.values("a")) {
      System.out.println(obj);
    }
  }

  @Test
  public void value() {
    Tatabase tb = new Tatabase();

    System.out.println(tb.value("merchandise"));
  }
  
}
