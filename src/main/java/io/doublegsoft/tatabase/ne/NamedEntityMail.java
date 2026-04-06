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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a> 
 * 
 * @since 4.0
 */
public class NamedEntityMail extends NamedEntity {
  
  public List<String> get(int count) {
    List<String> users = get(count, "/ne/user");
    List<String> domains = get(count, "/ne/domain");
    for (int i = 0; i < users.size(); i++) {
      users.set(i, users.get(i) + "@" + domains.get(i));
    }
    return users;
  } 
  
}
