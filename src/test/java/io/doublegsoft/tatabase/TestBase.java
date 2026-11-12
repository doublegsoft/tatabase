package io.doublegsoft.tatabase;

import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.File;
import java.nio.file.Files;

public class TestBase {

  public io.doublegsoft.tatabase.TatabaseParser.Tatabase_casesContext parseCases(String path) throws Exception {
    String dsl = loadText(path);
    io.doublegsoft.tatabase.TatabaseLexer lexer = new io.doublegsoft.tatabase.TatabaseLexer(CharStreams.fromString(dsl));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    io.doublegsoft.tatabase.TatabaseParser parser = new io.doublegsoft.tatabase.TatabaseParser(tokens);
     parser.setErrorHandler(new BailErrorStrategy());
    return parser.tatabase_cases();
  }

  public String loadText(String path) throws Exception {
    return new String(Files.readAllBytes(new File("./src/test/resources/" + path).toPath()), "utf-8");
  }

}
