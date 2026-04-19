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

import com.doublegsoft.jcommons.utils.Safe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a> 
 * 
 * @since 4.0
 */
public class NamedEntity {
  
  private static final Map<String, List<String>> cache = new HashMap<>();
  
  public static final NamedEntityAddress ADDRESS = new NamedEntityAddress();
  
  public static final NamedEntityDate DATE = new NamedEntityDate();
  
  public static final NamedEntityDateTime DATETIME = new NamedEntityDateTime();
  
  public static final NamedEntityTime TIME = new NamedEntityTime();
  
  public static final NamedEntityIdCardNumber ID_CARD_NUMBER = new NamedEntityIdCardNumber();
  
  public static final NamedEntityDepartment DEPARTMENT = new NamedEntityDepartment();
  
  public static final NamedEntityOrganization ORGANIZATION = new NamedEntityOrganization();
  
  public static final NamedEntityMail MAIL = new NamedEntityMail();
  
  public static final NamedEntityDomain DOMAIN = new NamedEntityDomain();
  
  public static final NamedEntityMobile MOBILE = new NamedEntityMobile();
  
  public static final NamedEntityPerson PERSON = new NamedEntityPerson();
  
  public static final NamedEntityPhone PHONE = new NamedEntityPhone();
  
  public static final NamedEntitySequence SEQUENCE = new NamedEntitySequence();
  
  public static final NamedEntityString STRING = new NamedEntityString();
  
  public static final NamedEntityURL URL = new NamedEntityURL();
  
  public static final NamedEntityUser USER = new NamedEntityUser();
  
  public static final NamedEntityEnum ENUM = new NamedEntityEnum();
  
  public static final NamedEntityNumber NUMBER = new NamedEntityNumber();
  
  public static final NamedEntityConstant CONSTANT = new NamedEntityConstant();
  
  public static final NamedEntityName NAME = new NamedEntityName();
  
  public static final NamedEntityFormat FORMAT = new NamedEntityFormat();
  
  public static List<String> get(String namedEntity, int count, String param) {
    switch (namedEntity) {
      case "date":
        return DATE.get(count, Safe.safeDate(param));
      case "time":
        return TIME.get(count, Safe.safeDate(param));
      case "datetime":
        return DATETIME.get(count, Safe.safeDate(param));
      case "id_card_number":
        return ID_CARD_NUMBER.get(count);
      case "department":
        return DEPARTMENT.get(count);
      case "organization":
        return ORGANIZATION.get(count);
      case "mail":
        return MAIL.get(count);
      case "domain":
        return DOMAIN.get(count);
      case "mobile":
        return MOBILE.get(count);
      case "person":
        return PERSON.get(count);
      case "phone":
        return PHONE.get(count);
      case "sequence":
        return SEQUENCE.get(count, (String) param);
      case "string":
        return STRING.get(count, Safe.safeInteger(param));
      case "url":
        return URL.get(count);
      case "user":
        return USER.get(count);
      case "enum":
        return ENUM.get(count, (String) param);
      case "constant":
        return CONSTANT.get(count, (String) param);
      case "number":
        return NUMBER.get(count, 0, 100);
      case "name":
        return NAME.get((String) param, count);
      case "format":
        return FORMAT.get((String) param, count);
      default:
        return Collections.emptyList();
    }
  }
  
  protected List<String> get(int count, String namedEntity) {
    List<String> retVal = new ArrayList<>();
    Map<String,Integer> map = new HashMap<>();
    for (int i = 0; i < count; ++i) {
      String s = random(namedEntity);
//      if (!map.containsKey(s))
//        map.put(s, 0);
//      map.put(s, map.get(s) + 1);
      retVal.add(s);
    }
//    retVal.addAll(map.keySet());
    return retVal;
  } 
  
  protected String get(String namedEntity) {
    return random(namedEntity);
  } 
  
  protected String random(String namedEntity) {
    if (!cache.containsKey(namedEntity)) {
      List<String> strs = new ArrayList<>();
      for(Scanner sc = new Scanner(getClass().getResourceAsStream(namedEntity), "UTF-8"); sc.hasNext();) {
        String line = sc.nextLine();
        strs.add(line);
      }
      cache.put(namedEntity, strs);
    }
    List<String> rows = cache.get(namedEntity);
    Random rand = new Random();
    return rows.get(rand.nextInt(rows.size()));
  }
  
}
