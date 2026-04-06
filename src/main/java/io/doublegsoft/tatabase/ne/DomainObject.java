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

import io.doublegsoft.tatabase.random.RandomResource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * It is the random domain model such as organization, person, address implementing the {@link RandomResource} interface.
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 1.0
 */
public class DomainObject implements DomainResource<String> {

  protected final List<String> data = new ArrayList<>();

  protected static String dataDir;

  private final String domain;

  private final String attribute;

  public static void setDataDir(String dataDir) {
    DomainObject.dataDir = dataDir;
  }

  public DomainObject(String domain, String attribute) {
    this.domain = domain;
    this.attribute = attribute;
  }

  @Override
  public List<String> domain(int count) {
    List<String> retVal = new ArrayList<>();
    try {
      if (data.isEmpty()) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(dataDir + "/" + domain, attribute + ".csv")), "utf-8"));
        String line;
        while ((line = reader.readLine()) != null) {
          data.add(line.split(",")[0]);
        }
        reader.close();
      }
    } catch (RuntimeException | IOException ex) {
      for (int i = 0; i < count; i++) {
        retVal.add("TEST");
      }
      return retVal;
    }
    Random rand = new Random();
    int bound = data.size();
    for (int i = 0; i < count; i++) {
      retVal.add(data.get(rand.nextInt(bound)));
    }
    return retVal;
  }

}
