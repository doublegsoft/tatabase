package io.doublegsoft.tatabase;

import com.doublegsoft.jcommons.metavalue.ObjectValueDefinition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestCase {

  private String code;

  private String label;

  private String description;

  private final List<ObjectValueDefinition> prerequisite = new ArrayList<>();

  private ObjectValueDefinition input;

  private final List<ObjectValueDefinition> output = new ArrayList<>();

  private final Map<String, ObjectValueDefinition> variables = new HashMap<>();

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<ObjectValueDefinition> getPrerequisite() {
    return prerequisite;
  }

  public ObjectValueDefinition getInput() {
    return input;
  }

  public void setInput(ObjectValueDefinition input) {
    this.input = input;
  }

  public List<ObjectValueDefinition> getOutput() {
    return output;
  }

  public Map<String, ObjectValueDefinition> getVariables() {
    return variables;
  }

  public ObjectValueDefinition getVariable(String name) {
    return variables.get(name);
  }
}
