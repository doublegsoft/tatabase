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

import com.doublegsoft.jcommons.metabean.type.DomainType;
import io.doublegsoft.tatabase.ne.NamedEntityMobile;
import io.doublegsoft.tatabase.ne.DomainObject;
import io.doublegsoft.tatabase.ne.NamedEntity;
import io.doublegsoft.tatabase.ne.NamedEntityDate;
import io.doublegsoft.tatabase.ne.NamedEntityMail;
import io.doublegsoft.tatabase.ne.NamedEntityPhone;
import io.doublegsoft.tatabase.ne.NamedEntitySequence;
import io.doublegsoft.tatabase.random.RandomNumber;
import io.doublegsoft.tatabase.random.RandomUUID;
import io.doublegsoft.typebase.CustomObject;
import io.doublegsoft.typebase.Typebase;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.*;
import java.util.Map.Entry;

import net.doublegsoft.appbase.ObjectMap;
import net.doublegsoft.appbase.util.Strings;

/**
 * {@link Tatabase} is an api for the test data.
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 1.0
 */
public class Tatabase {
  
  public static final Typebase TYPEBASE = new Typebase();
  
  public static final TatabaseBuilder BUILDER = new TatabaseBuilder();
  
  /**
   * @since 4.0
   */
  public static final Map<String, List<String>> story = new HashMap<>();
  
  public void clear() {
    story.clear();
  }
  
  public void build(String objname, String attrname, String namedEntity, int count, String param) {
    String name = objname + "#" + attrname;
    List<String> values = NamedEntity.get(namedEntity, count, param);
    if (values.isEmpty()) {
      // 对象主键引用
      values = story.get(namedEntity);
      Random rand = new Random();
      List<String> randomValues = new ArrayList<>();
      for (int i = 0; i < count; i++) {
        try {
          randomValues.add(NamedEntity.DATE.get(1, java.sql.Date.valueOf(values.get(i))).get(0));
        } catch (Throwable ex) {
          // 对象引用
          if (values != null) {
            randomValues.add(values.get(rand.ints(0, count - 1).findFirst().getAsInt()));
          } else {
            randomValues.add("");
          }
        }
      }
      story.put(name, randomValues);
      return;
    }
    story.put(name, values);
  }
  
  public List<Map<String, String>> values(String objname) {
    List<Map<String, String>> retVal = new ArrayList<>();
    for (Entry<String, List<String>> entry : story.entrySet()) {
      String key = entry.getKey();
      // the data is of the object
      if (key.indexOf(objname + "#") == 0) {
        String attrname = key.substring((objname + "#").length());
        List<String> vals = entry.getValue();
        if (retVal.isEmpty()) {
          for (String val : vals) {
            Map<String, String> attrs = new HashMap<>();
            attrs.put(attrname, val);
            retVal.add(attrs);
          }
        } else {
          for (int i = 0; i < vals.size(); i++) {
            Map<String, String> attrs = retVal.get(i);
            attrs.put(attrname, vals.get(i));
          }
        }
      }
    }
    return retVal;
  }
  
  public String string(Number length) {
    String retVal = NamedEntity.STRING.get(1, length).get(0);
    if (retVal.length() > length.intValue()) {
      retVal = retVal.substring(0, length.intValue());
    }
    return retVal;
  }
  
  public String number(Number min, Number max) {
    return NamedEntity.NUMBER.get(1, min, max).get(0);
  }
  
  public String date() {
    return NamedEntity.DATE.get(1, null).get(0);
  }
  
  public String datetime() {
    return NamedEntity.DATETIME.get(1, null).get(0);
  }
  
  public String enumcode(String expr) {
    return NamedEntity.ENUM.get(1, expr).get(0);
  }
  
  @Deprecated
  public String value(DomainType domainType, String langtype) {
    if (domainType.getName().equals("name")) {
      String type = domainType.getOption("type");
      if (type == null) {
        return value("name", null, langtype);
      }
      try {
        DomainObject domObj = new DomainObject(type, "name");
        return domObj.domain(1).get(0);
      } catch (Exception ex) {
        return value("name", null, langtype);
      }
    }
    return value(domainType.getName(), null, langtype);
  }

  public String value(String domain) {
    List<String> strs = new ArrayList<>();
    for(Scanner sc = new Scanner(getClass().getResourceAsStream("/ne/" + domain), "UTF-8"); sc.hasNext();) {
      String line = sc.nextLine();
      strs.add(line);
    }
    Random rand = new Random();
    return strs.get(rand.nextInt(strs.size()));
  }

  @Deprecated
  public String value(String domain, String prefix, String langtype) {
    String dmn = domain == null ? "" : domain.toLowerCase();
    int count = 100;
    int randomIndex = new Random().nextInt(count - 1);
    switch (dmn) {
      case "date":
        return new NamedEntityDate().get(count, null).get(randomIndex);
      case "name":
        if (Strings.isBlank(prefix)) {
          prefix = "测试名称";
        }
        return new NamedEntitySequence().get(count, prefix).get(randomIndex);
      case "mobile":
        return new NamedEntityMobile().get(count).get(randomIndex);
      case "email":
        return new NamedEntityMail().get(count).get(randomIndex);
      case "phone":
        return new NamedEntityPhone().get(count).get(randomIndex);
      case "money":
        return new RandomNumber(0, 100, 2).random(count).get(randomIndex).toPlainString();
      case "number":
        return new RandomNumber(0, 100, 0).random(count).get(randomIndex).toPlainString();
      case "int":
        return new RandomNumber(0, 100, 0).random(count).get(randomIndex).toPlainString();
      case "uuid":
        return new RandomUUID().random(count).get(randomIndex);
      case "format":
        
      case "datetime":
      case "lmt":
      case "now":
        return new java.sql.Timestamp(System.currentTimeMillis()).toString();
    }
    if (dmn.startsWith("'") && dmn.endsWith("'")) {
      dmn = dmn.substring(1, dmn.length() - 1).toUpperCase();
      BigDecimal intVal = new RandomNumber(0, 100, 0).random(count).get(randomIndex);
      return dmn + String.format("%03d", intVal.intValue());
    }
    return "null";
  }
  
  /**
   * Makes test data file under root directory.
   * 
   * @param root
   *        the output root directory
   * 
   * @param path
   *        the output file path including directory and file name
   * 
   * @param tplpath
   *        the template file path
   * 
   * @param customObject
   *        the custom object which is applied to page type definition 
   *        and it is a specific collection widget type such as table, 
   *        tree, list etc.
   * 
   * @since 4.5
   */
  public String touch(String root, String path, String tplpath, CustomObject customObject) throws IOException {
    File outfile = new File(root + "/" + path);
    if (!outfile.getParentFile().exists()) {
      outfile.getParentFile().mkdirs();
    }
    outfile.createNewFile();
    
    String tpl = new String(Files.readAllBytes(new File(tplpath).toPath()), "UTF-8");
    ObjectMap data = new ObjectMap();
    data.set("tatabase", this);
    data.set("customObject", customObject);
    String content = Strings.templatize(tpl, data);
    OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(outfile), "UTF-8");
    fw.write(content);
    fw.close();
    return "";
  }
  
  /**
   * Gets a string value randomly.
   * 
   * @param domain
   *        the domain type name
   * 
   * @param param
   *        the parameter expression 
   * @return 
   * 
   * @since 4.5
   */
  public String random(String domain, String param) {
    List<String> retVal = NamedEntity.get(domain, 1, param);
    if (retVal.isEmpty()) {
      return "";
    }
    return retVal.get(0);
  }

}
