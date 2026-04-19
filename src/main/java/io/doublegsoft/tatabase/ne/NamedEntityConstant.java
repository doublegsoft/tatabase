/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.doublegsoft.tatabase.ne;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gg
 */
public class NamedEntityConstant {
  
  public List<String> get(int count, String constant) {
    List<String> retVal = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      retVal.add(constant);
    }
    return retVal;
  }
  
}
