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

import org.junit.Test;

/**
 * Test for {@link DomainObject}.
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 1.0
 */
public class DomainObjectTest {

  @Test
  public void test_organization() throws Exception {
    DomainObject.setDataDir("../tatabase-data");
    new DomainObject("organization", "name").domain(100).forEach(val -> {
      System.out.println(val);
    });
  }

  @Test
  public void test_department() throws Exception {
    DomainObject.setDataDir("../tatabase-data");
    new DomainObject("department", "name").domain(100).forEach(val -> {
      System.out.println(val);
    });
  }

  @Test
  public void test_person() throws Exception {
    DomainObject.setDataDir("../tatabase-data");
    new DomainObject("person", "name").domain(100).forEach(val -> {
      System.out.println(val);
    });
  }

  @Test
  public void test_landmark() throws Exception {
    DomainObject.setDataDir("../tatabase-data");
    new DomainObject("landmark", "name").domain(100).forEach(val -> {
      System.out.println(val);
    });
  }

  @Test
  public void test_district() throws Exception {
    DomainObject.setDataDir("../tatabase-data");
    new DomainObject("district", "code").domain(100).forEach(val -> {
      System.out.println(val);
    });
  }

}
