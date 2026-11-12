package io.doublegsoft.tatabase.syntax;

import io.doublegsoft.tatabase.TatabaseParser;
import io.doublegsoft.tatabase.TestBase;
import org.junit.Assert;
import org.junit.Test;

public class BasicTest extends TestBase {

  @Test
  public void test() throws Exception {
    TatabaseParser.Tatabase_casesContext ctxCases = parseCases("syntax/basic.ttb");
    Assert.assertNotNull(ctxCases);
    Assert.assertEquals(2, ctxCases.tatabase_case().size());
  }

}
