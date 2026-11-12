package io.doublegsoft.tatabase.parser;

import com.doublegsoft.jcommons.metavalue.ObjectValueDefinition;
import io.doublegsoft.tatabase.TatabaseBuilder;
import io.doublegsoft.tatabase.TestBase;
import io.doublegsoft.tatabase.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class BasicTest extends TestBase {

  @Test
  public void test() throws Exception {
    String expr = loadText("syntax/basic.ttb");
    TatabaseBuilder builder = new TatabaseBuilder();
    List<TestCase> cases = builder.build(expr);
    Assert.assertEquals(2, cases.size());
    TestCase testcase = cases.get(0);
    Assert.assertEquals("TEST_0001", testcase.getCode());
    Assert.assertEquals("一号测试，这里可以来一段测试描述", testcase.getLabel());
    Assert.assertEquals(1, testcase.getPrerequisite().size());
    Assert.assertEquals(1, testcase.getOutput().size());

    ObjectValueDefinition objVal = testcase.getPrerequisite().get(0);
    Assert.assertEquals(2, objVal.getAttributeValues().size());
    Assert.assertEquals("user", objVal.getName());

    objVal = testcase.getOutput().get(0);
    Assert.assertEquals(2, objVal.getAttributeValues().size());
    Assert.assertEquals("user", objVal.getName());

    testcase = cases.get(1);
    Assert.assertEquals("TEST_0002", testcase.getCode());
    Assert.assertEquals("二号测试，这里可以来一段测试描述", testcase.getLabel());
    Assert.assertEquals(1, testcase.getPrerequisite().size());
    Assert.assertEquals(1, testcase.getOutput().size());

    objVal = testcase.getPrerequisite().get(0);
    Assert.assertEquals(2, objVal.getAttributeValues().size());
    Assert.assertEquals("account", objVal.getName());

    objVal = testcase.getOutput().get(0);
    Assert.assertEquals(2, objVal.getAttributeValues().size());
    Assert.assertEquals("account", objVal.getName());
  }

}
