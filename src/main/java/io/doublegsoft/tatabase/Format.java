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

import com.doublegsoft.jcommons.lang.HashObject;
import com.doublegsoft.jcommons.lang.StringHolder;
import com.doublegsoft.jcommons.utils.Dates;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * It is the output format under tatabase.
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 1.0
 */
public enum Format {

  JSON {
    @Override
    public HashObject to(HashObject context) {
      HashObject retVal = new HashObject();
      List<HashObject> objs = context.get("objs");
      for (HashObject obj : objs) {
        HashObject objJson = new HashObject();
        List<HashObject> attrs = obj.get("attrs");
        int count = obj.get("count");
        for (int i = 0; i < count; i++) {
          HashObject single = new HashObject();
          for (HashObject attr : attrs) {
            List<Object> values = attr.get("values");
            if (values == null) {
              continue;
            }
            single.set(attr.get("name"), values.get(i));
          }
          objJson.add("data", single);
        }
        retVal.set(obj.get("name"), com.alibaba.fastjson.JSON.toJSONString(objJson));
      }
      return retVal;
    }
  },
  
  SQL {
    @Override
    public HashObject to(HashObject context) {
      HashObject retVal = new HashObject();
      List<HashObject> objs = context.get("objs");
      objs.forEach(obj -> {
        StringHolder insertSqls = new StringHolder();
        List<HashObject> attrs = obj.get("attrs");
        int count = obj.get("count");
        for (int i = 0; i < count; i++) {
          StringHolder insertSql = new StringHolder();
          StringHolder valuesSql = new StringHolder();
          insertSql.append("insert into ").append((String) obj.get("name")).append(" (");
          valuesSql.append("values (");
          int index = 0;
          for (HashObject attr : attrs) {
            List<Object> values = attr.get("values");
            if (index > 0) {
              insertSql.append(", ");
              valuesSql.append(", ");
            }
            insertSql.append((String) attr.get("name"));
            valuesSql.append(values == null || values.get(i) == null ? "null" : "'" + value(attr, values.get(i)) + "'");
            index++;
          }
          insertSqls.append(insertSql.append(")").linefeed().append(valuesSql).append(");").linefeed());
        }
        retVal.set(obj.get("name"), insertSqls);
      });
      return retVal;
    }
  },
  
  UNIT {
    @Override
    public HashObject to(HashObject context) {
      HashObject retVal = new HashObject();
      return retVal;
    }
    
  };

  public abstract HashObject to(HashObject context);

  private static String value(HashObject attr, Object value) {
    if (value instanceof Date) {
      if ("datetime".equals(attr.get("datatype"))) {
        return Dates.stringify((Date) value, Calendar.SECOND);
      } else if ("date".equals(attr.get("datatype"))) {
        return Dates.stringify((Date) value);
      } else if ("time".equals(attr.get("datatype"))) {

      } else {
        return Dates.stringify((Date) value);
      }
    }
    return value.toString();
  }
}
