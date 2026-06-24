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

import com.doublegsoft.jcommons.metabean.AttributeDefinition;
import com.doublegsoft.jcommons.metabean.ModelDefinition;
import com.doublegsoft.jcommons.metabean.ObjectDefinition;
import com.doublegsoft.jcommons.metabean.type.DomainType;
import com.doublegsoft.jcommons.metabean.type.PrimitiveType;
import com.doublegsoft.jcommons.metaui.WidgetDefinition;
import com.doublegsoft.jcommons.utils.Strings;
import io.doublegsoft.tatabase.ne.*;
import io.doublegsoft.tatabase.random.RandomDate;
import io.doublegsoft.tatabase.random.RandomNumber;
import io.doublegsoft.tatabase.random.RandomUUID;
import io.doublegsoft.typebase.EnumValue;
import io.doublegsoft.typebase.Typebase;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;

/**
 * {@link Tatabase} is an api for the test data.
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 1.0
 */
public class Tatabase {
  
  public static final Typebase TYPEBASE = new Typebase();

  public static final ModelDefinition DUMMY = new ModelDefinition();

  public static final ObjectDefinition OBJ = new ObjectDefinition("dummy", DUMMY);
  
  public static final TatabaseBuilder BUILDER = new TatabaseBuilder();

  private final String dataPath;

  public Tatabase() {
    dataPath = null;
  }

  public Tatabase(String dataPath) {
    this.dataPath = dataPath;
  }

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

  public String number(Number min, Number max, int scale) {
    return NamedEntity.NUMBER.get(1, min, max, scale).get(0);
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

  public String value(String domain) throws IOException {
    List<String> strs = new ArrayList<>();
    for (Scanner sc = new Scanner(Objects.requireNonNull(getClass().getResourceAsStream(
        "/ne/" + toCamelCase(domain))), "UTF-8"); sc.hasNext(); ) {
      String line = sc.nextLine();
      strs.add(line);
    }
    Random rand = new Random();
    return strs.get(rand.nextInt(strs.size()));
  }

  public String value(WidgetDefinition widget) throws IOException {
    String type = widget.getType();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    switch (type) {
      case "date":
        if (widget.getId() != null) {
          if (widget.getId().contains("birth")) {
            return sdf.format(new RandomDate("1960-01-01", "2024-12-31").random(0).get(0));
          }
        }
        return sdf.format(new RandomDate("2025-01-01", "2026-12-31").random(0).get(0));
      case "time":
        return new SimpleDateFormat("HH:mm").format(new Date());
      case "number":
        return String.valueOf(new RandomNumber(1, 1000, 0).random(1).get(0).intValue());
      case "longtext":
        return string(50);
      default:
        String dataexpr = widget.value("data");
        if (dataexpr != null && dataexpr.startsWith("enum[")) {
          List<EnumValue> enums = new Typebase().enumtype(dataexpr);
          int index = ThreadLocalRandom.current().nextInt(0, enums.size());
          return enums.get(index).getCode();
        }
    }
    return string(10);
  }

  public String value(AttributeDefinition attr) throws IOException {
    try {
      if (!Strings.isEmpty(dataPath)) {
        String attrname = toCamelCase(attr.getName());
        return new NamedEntityAnything().get(dataPath, attrname);
      }
    } catch (Throwable ignored) {

    }
    if (attr.isIdentifiable() && Strings.in(attr.getType().getName(), "uuid", "string")) {
      return UUID.randomUUID().toString();
    } if (attr.isIdentifiable() && Strings.in(attr.getType().getName(), "long")) {
      String num = number(1, 100);
      return num.substring(0, num.indexOf("."));
    } else if (Strings.in(attr.getType().getName(),"now", "lmt") ||
        "now".equals(attr.getConstraint().getDefaultValue())) {
      return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    } else if (attr.getConstraint().getDomainType() != null &&
        attr.getConstraint().getDomainType().getName().startsWith("enum")) {
      List<EnumValue> enums = new Typebase().enumtype(attr.getConstraint().getDomainType().getName());
      int index = ThreadLocalRandom.current().nextInt(0, enums.size());
      return enums.get(index).getCode();
    } else if (Strings.in(attr.getType().getName(), "int", "integer", "long")) {
      String num = number(0, 10000);
      return num.substring(0, num.indexOf("."));
    } else if (Strings.in(attr.getType().getName(), "number")) {
      String num = number(0, 10000);
      return num.substring(0, num.length() - 2);
    } else if (attr.isLabelled("reference")) {
      if ("id".equals(attr.getLabelledOption("reference", "value"))) {
        String num = number(1, 100);
        return num.substring(0, num.indexOf("."));
      } else {
        String[] strs = new String[]{"A.A", "B.B", "C.C", "D.D"};
        int index = ThreadLocalRandom.current().nextInt(0, strs.length);
        return strs[index];
      }
    } else if (attr.getName().contains("phone")) {
      return new NamedEntityPhone().get(1).get(0);
    } else if (attr.getName().contains("mobile")) {
      return new NamedEntityMobile().get(1).get(0);
    } else if (attr.getName().contains("address")) {
      return new NamedEntityAddress().get(1).get(0);
    }
    int size = attr.getConstraint().getMaxSize();
    if (size == 0) {
      size = 10;
    }
    if (size > 20) {
      size = 20;
    }
    return string(size);
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

  public static String toCamelCase(String input) {
    if (input == null || input.isEmpty()) {
      return input;
    }

    StringBuilder result = new StringBuilder();
    boolean capitalizeNext = false;

    for (char c : input.toCharArray()) {
      if (c == '_' || c == '-' || c == ' ') {
        capitalizeNext = true;
      } else {
        if (capitalizeNext) {
          result.append(Character.toUpperCase(c));
          capitalizeNext = false;
        } else {
          result.append(Character.toLowerCase(c));
        }
      }
    }

    return result.toString();
  }
}
