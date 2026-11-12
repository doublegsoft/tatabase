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
import com.doublegsoft.jcommons.metabean.AttributeDefinition;
import com.doublegsoft.jcommons.metabean.ModelDefinition;
import com.doublegsoft.jcommons.metabean.ObjectDefinition;
import com.doublegsoft.jcommons.metavalue.AttributeValueDefinition;
import com.doublegsoft.jcommons.metavalue.ObjectValueDefinition;
import com.doublegsoft.jcommons.utils.Safe;
import io.doublegsoft.tatabase.TatabaseParser.Tatabase_attributeContext;
import io.doublegsoft.tatabase.TatabaseParser.Tatabase_objectContext;
import io.doublegsoft.tatabase.TatabaseParser.Tatabase_typeContext;
import io.doublegsoft.tatabase.TatabaseParser.Tatabase_valueContext;
import io.doublegsoft.tatabase.ne.DomainConstant;
import io.doublegsoft.tatabase.ne.DomainDateSequence;
import io.doublegsoft.tatabase.ne.NamedEntityMobile;
import io.doublegsoft.tatabase.ne.NamedEntityPhone;
import io.doublegsoft.tatabase.ne.NamedEntitySequence;
import io.doublegsoft.tatabase.random.RandomDate;
import io.doublegsoft.tatabase.ne.DomainObject;
import io.doublegsoft.tatabase.ne.NamedEntityDate;
import io.doublegsoft.tatabase.ne.NamedEntityMail;
import io.doublegsoft.tatabase.random.RandomEnumeration;
import io.doublegsoft.tatabase.random.RandomNumber;
import io.doublegsoft.tatabase.random.RandomReference;
import io.doublegsoft.tatabase.random.RandomUUID;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

/**
 * {@link TatabaseBuilder} builds the test data.
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 1.0
 */
public class TatabaseBuilder {

  private final static HashObject model = new HashObject();

  public List<TestCase> build(String dsl) {
    List<TestCase> retVal = new ArrayList<>();
    io.doublegsoft.tatabase.TatabaseParser.Tatabase_casesContext ctxCases = parseCases(dsl);
    for (io.doublegsoft.tatabase.TatabaseParser.Tatabase_caseContext ctxCase : ctxCases.tatabase_case()) {
      TestCase testcase = new TestCase();
      testcase.setCode(ctxCase.code.getText());
      String label = ctxCase.label.getText();
      testcase.setLabel(label.substring(1, label.length() - 1));
      if (ctxCase.anybase_string() != null) {
        StringBuilder descr = new StringBuilder();
        for (io.doublegsoft.tatabase.TatabaseParser.Anybase_stringContext ctxStr : ctxCase.anybase_string()) {
          if (descr.length() > 0) {
            descr.append("\n");
          }
          String str = ctxStr.getText();
          descr.append(str.substring(1, str.length() - 1));
        }
      }
      for (io.doublegsoft.tatabase.TatabaseParser.Tatabase_case_prerequisiteContext ctxPre : ctxCase.tatabase_case_prerequisite()) {
        ObjectValueDefinition objVal = new ObjectValueDefinition();
        io.doublegsoft.tatabase.TatabaseParser.Tatabase_case_obj_setContext ctxObjSet = ctxPre.tatabase_case_obj_set();
        objVal.setName(ctxObjSet.name.getText());
        for (io.doublegsoft.tatabase.TatabaseParser.Tatabase_case_attr_setContext ctxAttrSet : ctxObjSet.tatabase_case_attrs_set().tatabase_case_attr_set()) {
          AttributeValueDefinition attrVal = new AttributeValueDefinition();
          attrVal.setName(ctxAttrSet.name.getText());
          attrVal.setValue(parseValue(ctxAttrSet.value));
          objVal.getAttributeValues().add(attrVal);
        }
        if (ctxPre.tatabase_case_assign() != null) {
          String varName = ctxPre.tatabase_case_assign().anybase_id().getText();
          testcase.getVariables().put(varName, objVal);
        }
        testcase.getPrerequisite().add(objVal);
      }
      for (io.doublegsoft.tatabase.TatabaseParser.Tatabase_case_outputContext ctxOutput : ctxCase.tatabase_case_output()) {
        ObjectValueDefinition objVal = new ObjectValueDefinition();
        io.doublegsoft.tatabase.TatabaseParser.Tatabase_case_obj_assertContext ctxObjAssert = ctxOutput.tatabase_case_obj_assert();
        objVal.setName(ctxObjAssert.name.getText());
        for (io.doublegsoft.tatabase.TatabaseParser.Tatabase_case_attr_assertContext ctxAttrAssert : ctxObjAssert.tatabase_case_attrs_assert().tatabase_case_attr_assert()) {
          AttributeValueDefinition attrVal = new AttributeValueDefinition();
          attrVal.setName(ctxAttrAssert.name.getText());
          attrVal.setValue(parseValue(ctxAttrAssert.value));
          objVal.getAttributeValues().add(attrVal);
        }
        if (ctxObjAssert.attr != null) {
          AttributeValueDefinition attrVal = new AttributeValueDefinition();
          attrVal.setName(ctxObjAssert.attr.getText());
          attrVal.setValue(parseValue(ctxObjAssert.tatabase_value()));
          objVal.setSearch(attrVal);
        }
        testcase.getOutput().add(objVal);
      }
      retVal.add(testcase);
    }
    return retVal;
  }

  /**
   * Gets all test insert SQL statements for the given model.
   * 
   * @param model
   *        the application model
   * 
   * @return 
   *        the SQL statements
   * 
   * @since 3.0
   *
   * @deprecated
   */
  public String sqlize(ModelDefinition model) {
    Map<ObjectDefinition, ObjectDefinitionHolder> holders = new HashMap();
    for (ObjectDefinition obj : model.getObjects()) {
      for (AttributeDefinition attr : obj.getAttributes()) {
        
      }
    }
    return null;
  }

  public TatabaseBuilder parse(String dsl) throws IOException {
    io.doublegsoft.tatabase.TatabaseLexer lexer = new io.doublegsoft.tatabase.TatabaseLexer(CharStreams.fromString(dsl));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    io.doublegsoft.tatabase.TatabaseParser parser = new io.doublegsoft.tatabase.TatabaseParser(tokens);
    // parser.setErrorHandler(new BailErrorStrategy());
    io.doublegsoft.tatabase.TatabaseParser.Tatabase_modelContext ctxModel = parser.tatabase_model();

    ctxModel.tatabase_object().stream().forEach((ctxObj) -> {
      model.add("objs", parseObject(ctxObj));
    });
    supplementReferences();
    return this;
  }

  public HashObject build(Format format) {
    return format.to(model);
  }

  public io.doublegsoft.tatabase.TatabaseParser.Tatabase_casesContext parseCases(String dsl) {
    io.doublegsoft.tatabase.TatabaseLexer lexer = new io.doublegsoft.tatabase.TatabaseLexer(CharStreams.fromString(dsl));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    io.doublegsoft.tatabase.TatabaseParser parser = new io.doublegsoft.tatabase.TatabaseParser(tokens);
    parser.setErrorHandler(new BailErrorStrategy());
    return parser.tatabase_cases();
  }

  private HashObject parseObject(Tatabase_objectContext ctxObj) {
    HashObject retVal = new HashObject();
    retVal.set("name", ctxObj.name.getText());
    int count = 0;
    if (ctxObj.size != null) {
      count = Integer.valueOf(ctxObj.size.getText());
    }
    retVal.set("count", count);
    for (Tatabase_attributeContext ctxAttr : ctxObj.tatabase_attribute()) {
      retVal.add("attrs", parseAttribute(ctxAttr, count));
    }
    return retVal;
  }

  private HashObject parseAttribute(Tatabase_attributeContext ctxAttr, int count) {
    HashObject retVal = new HashObject();
    retVal.set("name", ctxAttr.name.getText());
    if (ctxAttr.obj != null) {
      retVal.set("obj", parseObject(ctxAttr.obj));
      return retVal;
    }
    Tatabase_typeContext ctxType = ctxAttr.tatabase_type();
    if (ctxType.date != null) {
      retVal.set("datatype", "date");
      retVal.set("values", new DomainConstant(Date.valueOf(ctxType.date.getText().replace('#', ' '))).domain(count));
    } else if (ctxType.datetime != null) {
      retVal.set("datatype", "datetime");
      retVal.set("values", new DomainConstant(Timestamp.valueOf(ctxType.date.getText().replace('#', ' '))).domain(count));
    } else if (ctxType.num != null) {
      int dotIndex = ctxType.num.getText().indexOf(".");
      if (dotIndex == -1) {
        retVal.set("values", new DomainConstant(Integer.valueOf(ctxType.num.getText())).domain(count));
      } else {
        retVal.set("values", new DomainConstant(Double.valueOf(ctxType.num.getText())).domain(count));
      }
    } else if (ctxType.nil != null) {
      retVal.set("values", new DomainConstant(null).domain(count));
    } else if (ctxType.id != null) {
      retVal.set("values", new RandomUUID().random(count));
    } else if (ctxType.ref != null) {
      retVal.set("object", ctxType.ref.obj.getText());
      retVal.set("attribute", ctxType.ref.attr.getText());
    } else if (ctxType.rnge != null) {
      String prefix = ctxType.rnge.prefix == null ? "" : ctxType.rnge.prefix.getText();
      if (!prefix.isEmpty()) {
        prefix = prefix.substring(1, prefix.length() - 1);
      }
      Tatabase_valueContext ctxValue = ctxType.rnge.startValue;
      if (ctxValue.num != null) {
        String strStartValue = ctxValue.num.getText();
        String strEndValue = ctxType.rnge.endValue.num.getText();
        int dotIndex = strStartValue.indexOf(".");
        int precision = 0;
        if (dotIndex != -1) {
          precision = strStartValue.substring(dotIndex + 1).length();
        }
        Double dblStart = Double.valueOf(strStartValue);
        Double dblEnd = Double.valueOf(strEndValue);
        if (prefix.isEmpty()) {
          // between two numbers
          retVal.set("values", new RandomNumber(dblStart, dblEnd, precision).random(count));
        } else {
          // string sequence from start
          retVal.set("values", new NamedEntitySequence().get(count, prefix));
        }
      } else if (ctxValue.date != null) {
        String strStartValue = ctxValue.date.getText();

        Date dateStart = Date.valueOf(strStartValue.replace('#', ' '));
        if (ctxType.rnge.endValue.date != null) {
          // between two days
          String strEndValue = ctxType.rnge.endValue.date.getText();
          Date dateEnd = Date.valueOf(strEndValue.replace('#', ' '));
          retVal.set("values", new RandomDate(dateStart, dateEnd).random(count));
        } else if (ctxType.rnge.endValue.duration != null) {
          // date sequence from start
          int duration = 1;
          int field = Calendar.HOUR;
          if (ctxType.rnge.endValue.duration.year != null) {
            duration = Integer.valueOf(ctxType.rnge.endValue.duration.year.anybase_int().getText());
            field = Calendar.YEAR;
          } else if (ctxType.rnge.endValue.duration.month != null) {
            duration = Integer.valueOf(ctxType.rnge.endValue.duration.month.anybase_int().getText());
            field = Calendar.MONTH;
          } else if (ctxType.rnge.endValue.duration.day != null) {
            duration = Integer.valueOf(ctxType.rnge.endValue.duration.day.anybase_int().getText());
            field = Calendar.DAY_OF_MONTH;
          } else if (ctxType.rnge.endValue.duration.hour != null) {
            duration = Integer.valueOf(ctxType.rnge.endValue.duration.hour.anybase_int().getText());
            field = Calendar.HOUR_OF_DAY;
          } else if (ctxType.rnge.endValue.duration.minute != null) {
            duration = Integer.valueOf(ctxType.rnge.endValue.duration.minute.anybase_int().getText());
            field = Calendar.MINUTE;
          }
          retVal.set("values", new DomainDateSequence(dateStart, duration, field).domain(count));
        }
      }
    } else if (ctxType.lmt != null) {
      // last modified time
      retVal.set("datatype", "datetime");
    } else if (ctxType.dmn != null) {
      String attribute = ctxType.dmn.attr.getText();
      if (ctxType.dmn.obj != null) {
        String domain = ctxType.dmn.obj.getText();
        retVal.set("values", new DomainObject(domain, attribute).domain(count));
      } else if (attribute.equalsIgnoreCase("phone")) {
        retVal.set("values", new NamedEntityPhone().get(count));
      } else if (attribute.equalsIgnoreCase("mobile")) {
        retVal.set("values", new NamedEntityMobile().get(count));
      } else if (attribute.equalsIgnoreCase("lmt") || attribute.equalsIgnoreCase("now")) {
        retVal.set("datatype", "datetime");
      } else if (attribute.equalsIgnoreCase("email")) {
        retVal.set("values", new NamedEntityMail().get(count));
      } else if (attribute.equalsIgnoreCase("name")) {
        retVal.set("values", new DomainObject("common", "name").domain(count));
      } else if (attribute.equalsIgnoreCase("address")) {
        retVal.set("values", new DomainObject("common", "address").domain(count));
      } else if (attribute.equalsIgnoreCase("date")) {
        retVal.set("datatype", "date");
        retVal.set("values", new NamedEntityDate().get(count, null));
      } else if (attribute.equals("password")) {
        // 123
        retVal.set("values", new DomainConstant("202cb962ac59075b964b07152d234b70".toUpperCase()).domain(count));
      } else if (attribute.equalsIgnoreCase("uuid")) {
        retVal.set("values", new RandomUUID().random(count));
      }

    } else if (ctxType.enmr != null) {
      // TODO: JUST ONLY SUPPORT STRING ENUMERATION
      List<String> enmr = new ArrayList<>();
      ctxType.enmr.tatabase_name().forEach(name -> {
        enmr.add(name.getText());
      });
      retVal.set("values", new RandomEnumeration(enmr).random(count));
    } else if (ctxType.str != null) {
      String str = ctxType.str.getText();
      str = str.substring(1, str.length() - 1);
      retVal.set("values", new DomainConstant(str).domain(count));
    }
    return retVal;
  }

  private void supplementReferences() {
    List<HashObject> objs = model.get("objs");
    if (objs == null) {
      return;
    }
    for (HashObject obj : objs) {
      List<HashObject> attrs = obj.get("attrs");
      for (HashObject attr : attrs) {
        if (attr.get("object") != null) {
          String objectName = attr.get("object");
          String attributeName = attr.get("attribute");
          attr.set("values", new RandomReference(model, objectName, attributeName).random((int) obj.get("count")));
        }
      }
    }
  }

  private List<Object> findObjectAttributeValues(String objectName, String attributeName) {
    List<HashObject> objs = model.get("objs");
    for (HashObject obj : objs) {
      if (!objectName.equals(obj.get("name"))) {
        continue;
      }
      List<HashObject> attrs = obj.get("attrs");
      for (HashObject attr : attrs) {
        if (attributeName.equals(attr.get("name"))) {
          return attr.get("values");
        }
      }
    }
    return null;
  }

  private static Object parseValue(io.doublegsoft.tatabase.TatabaseParser.Tatabase_valueContext ctxVal) {
    if (ctxVal.var != null) {
      return ctxVal.var.getText();
    } else if (ctxVal.num != null) {
      if (ctxVal.num.anybase_int().size() == 1) {
        String val = ctxVal.num.anybase_int(0).getText();
        return Safe.safeInteger(val);
      } else {
        String val = ctxVal.num.anybase_int(0).getText() + "." +
            ctxVal.num.anybase_int(1).getText();
        return Safe.safeBigDecimal(val);
      }
    } else if (ctxVal.str != null) {
      String val = ctxVal.str.getText();
      val = val.substring(1, val.length() - 1);
      return val;
    } else if (ctxVal.date != null) {
      String val = ctxVal.date.getText();
      return Safe.safeDate(val);
    }
    return null;
  }
  
  /**
   * @since 3.0
   */
  private static class ObjectDefinitionHolder {
    
    private final ObjectDefinition objectModel;
    
    private final Map<AttributeDefinition, String> values = new HashMap();

    public ObjectDefinitionHolder(ObjectDefinition objectModel) {
      this.objectModel = objectModel;
    }

    public ObjectDefinition getObjectModel() {
      return objectModel;
    }
    
  }
}
