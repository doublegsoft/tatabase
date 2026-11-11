package io.doublegsoft.tatabase.grammar;

import io.doublegsoft.tatabase.TatabaseParser;
import io.doublegsoft.tatabase.TestBase;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;

public class BasicTest extends TestBase {

  @Test
  public void test() throws Exception {
    TatabaseParser.Tatabase_casesContext ctxCases = parseCases("grammar/basic.ttb");
    Assert.assertNotNull(ctxCases);
    Assert.assertEquals(2, ctxCases.tatabase_case().size());
  }

}
