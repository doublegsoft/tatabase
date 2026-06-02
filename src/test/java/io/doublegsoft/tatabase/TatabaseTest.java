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

import com.doublegsoft.jcommons.metabean.AttributeDefinition;
import com.doublegsoft.jcommons.metabean.ModelDefinition;
import com.doublegsoft.jcommons.metabean.ObjectDefinition;
import com.doublegsoft.jcommons.metabean.type.DomainType;
import com.doublegsoft.jcommons.metabean.type.PrimitiveType;
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
  public void value() throws Exception {
    ModelDefinition model = new ModelDefinition();
    ObjectDefinition obj = new ObjectDefinition("dummy", model);
    Tatabase tb = new Tatabase("./model");
    AttributeDefinition attr = new AttributeDefinition("a", obj);
    attr.setType(new PrimitiveType("int"));
    System.out.println(tb.value(attr));
    attr = new AttributeDefinition("b", obj);
    attr.setType(new PrimitiveType("number"));
    System.out.println(tb.value(attr));
    attr = new AttributeDefinition("c", obj);
    attr.setType(new PrimitiveType("now"));
    System.out.println(tb.value(attr));
    attr = new AttributeDefinition("d", obj);
    attr.setType(new PrimitiveType("string"));
    attr.getConstraint().setMaxSize(20);
    System.out.println(tb.value(attr));
    attr = new AttributeDefinition("e", obj);
    attr.setType(new PrimitiveType("string"));
    attr.getConstraint().setDomainType(new DomainType("enum[01:ABC('大A'),BB:BCD('大B'),CC:C('大C')]"));
    System.out.println(tb.value(attr));
    attr = new AttributeDefinition("f", obj);
    attr.setType(new PrimitiveType("long"));
    attr.getConstraint().setIdentifiable(true);
    System.out.println(tb.value(attr));
  }
  
}
