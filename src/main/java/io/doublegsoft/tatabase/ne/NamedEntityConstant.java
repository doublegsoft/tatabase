/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.doublegsoft.tatabase.ne;

import com.ibm.icu.util.Calendar;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import net.doublegsoft.appbase.util.Dates;

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
