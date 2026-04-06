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
import com.doublegsoft.jcommons.lang.StringPair;
import com.doublegsoft.jcommons.metabean.AttributeDefinition;
import com.doublegsoft.jcommons.metabean.ModelDefinition;
import com.doublegsoft.jcommons.metabean.ObjectDefinition;
import com.doublegsoft.jcommons.metabean.type.CustomType;
import com.doublegsoft.jcommons.metabean.type.PrimitiveType;
import io.doublegsoft.typebase.Typebase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * {@link TatabaseContext} holds business objects.
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 1.0
 * 
 * @version 2.0 - change the principle of this type. 
 */
public class TatabaseContext {
  
  private static final Typebase TYPEBASE = new Typebase();
  
  private HashObject wrappers = new HashObject();
  
  private Map<ObjectDefinition, List<Object>> objectIds = new HashMap<>();
  
  private Map<ObjectDefinition, List<Object>> existingIds = new HashMap<>();
  
  private final ModelDefinition model;
  
  /**
   * This funciton is applied in template files.
   * 
   * @param model
   *        the modeld definition
   * 
   * @return {@link TatabaseContext} instance
   */
  public static TatabaseContext newInstance(ModelDefinition model) {
    return new TatabaseContext(model);
  }
  
  public TatabaseContext(ModelDefinition model) {
    this.model = model;
  }
  
  /**
   * Adds an existing object id.
   * 
   * @param obj
   *        object definition
   * 
   * @param id 
   *        the id value for object definition
   */
  public TatabaseContext addObjectId(ObjectDefinition obj, Object id) {
    List<Object> ids = objectIds.get(obj);
    if (ids == null) {
      ids = new ArrayList<>();
      objectIds.put(obj, ids);
      existingIds.put(obj, new ArrayList<>());
    }
    ids.add(id);
    return this;
  }
  
  /**
   * Gets the id value of an object definition, and the return value is not in the given existings.
   * 
   * @param obj
   *        the object definition to get id value
   * 
   * @param existings 
   *        the existing id values which have been got
   */
  public Object getObjectId(ObjectDefinition obj, List<Object> existings) {
    List<Object> ids = objectIds.get(obj);
    if (ids == null) {
      return null;
    }
    for (Object id : ids) {
      boolean alreadyExisting = false;
      for (Object existing : existings) {
        if (id.equals(existing)) {
          alreadyExisting = true;
          break;
        }
      }
      if (!alreadyExisting) {
        existings.add(id);
        return id;
      }
    }
    return null;
  }
  
  /**
   * Gets the value for the givne attribute definition, and the support rules are below:
   * <ul>
   * <li>default value</li>
   * <li>enumeration</li>
   * <li>long</li>
   * <li>integer</li>
   * </ul>
   * 
   * @param attr
   *        the attribute definition
   * 
   * @param ctx
   *        the tatabase context
   * 
   * @return the test value
   */
  public Object getValue(AttributeDefinition attr, TatabaseContext ctx) {
    if (attr.getConstraint().getDefaultValue() != null) {
      return attr.getConstraint().getDefaultValue();
    }
    if (attr.getConstraint().getDomainType().getExpression().indexOf("enum") == 0) {
      List<StringPair> pairs = TYPEBASE.enumtype(attr.getConstraint().getDomainType().getExpression());
      StringPair pair = pairs.get(randomInt(0, pairs.size() - 1));
      return pair.getKey();
    }
    if (PrimitiveType.INTEGER.equals(attr.getType().getName())) {
      return randomInt(1, 100);
    }
    if (PrimitiveType.LONG.equals(attr.getType().getName())) {
      return randomInt(1, 100);
    }
    if (attr.getType().isCustom()) {
      CustomType type = (CustomType) attr.getType();
      List<Object> ids = objectIds.get(type.getObjectDefinition());
      if (ids == null) {
        return null;
      }
      return getObjectId(type.getObjectDefinition(), existingIds.get(type.getObjectDefinition()));
    }
    return null;
  }
  
  private static int randomInt(int min, int max) {
    Random rand = new Random();
    return rand.nextInt((max - min) - 1) + min;
  }
  
}
