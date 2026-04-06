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

import java.util.Date;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a> 
 * 
 * @since 4.0
 */
public class NamedEntityTest {
  
  @Test
  public void test_address() throws Exception {
    List<String> results = new NamedEntityAddress().get(10);
    for (String row : results) {
      System.out.println(row);
    }
  }
  
  @Test
  public void test_mail() throws Exception {
    List<String> results = new NamedEntityMail().get(10);
    for (String row : results) {
      System.out.println(row);
    }
  }
  
  @Test
  public void test_string() throws Exception {
    List<String> results = new NamedEntityString().get(10, 100);
    for (String row : results) {
      System.out.println(row);
    }
  }
  
  @Test
  public void test_date() throws Exception {
    List<String> results = new NamedEntityDate().get(10, (Date) null);
    for (String row : results) {
      System.out.println(row);
    }
  }
  
}
