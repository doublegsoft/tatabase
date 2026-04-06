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

import io.doublegsoft.tatabase.ne.DomainObject;
import java.io.File;
import java.nio.file.Files;
import org.junit.Test;

/**
 * Test for {@link TatabaseBuilder}.
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 1.0
 */
public class TatabaseBuilderTest {

  @Test
  public void test_json() throws Exception {
    DomainObject.setDataDir("../tatabase-data");
    String dsl = new String(Files.readAllBytes(new File("./src/test/resources/ttb/test1.ttb").toPath()), "utf-8");
    new TatabaseBuilder().parse(dsl).build(Format.JSON).values().forEach(value -> {
      System.out.println(value);
    });
  }

  @Test
  public void test_sql() throws Exception {
    DomainObject.setDataDir("../tatabase-data");
    String dsl = new String(Files.readAllBytes(new File("./src/test/resources/ttb/test1.ttb").toPath()), "utf-8");
    new TatabaseBuilder().parse(dsl).build(Format.SQL).values().forEach(value -> {
      System.out.println(value);
    });
  }

}
