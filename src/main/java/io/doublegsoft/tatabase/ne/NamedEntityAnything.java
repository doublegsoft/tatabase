package io.doublegsoft.tatabase.ne;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class NamedEntityAnything {

  public String get(String dataDir, String attrname) throws IOException {
    List<String> all = get(dataDir, attrname, 1);
    int index = ThreadLocalRandom.current()
        .nextInt(0, all.size());
    return all.get(index);
  }

  public List<String> get(String dataDir, String attrname, int count) throws IOException {
    File dir = new File(dataDir);
    File dataFile = new File(dir, attrname);
    List<String> lines = Files.readAllLines(dataFile.toPath());
    List<String> retVal = new ArrayList<>();
    for (String line : lines) {
      if (retVal.size() < count) {
        retVal.add(line);
      } else {
        break;
      }
    }
    return retVal;
  }

}
