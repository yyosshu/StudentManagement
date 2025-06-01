package raisetech.StudentManagement;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StudentManagementApplication {

  private String name = "Java Test";
  private String age = "30";
  private final Map<String, String> map = new HashMap<>();

  public static void main(String[] args) {
    SpringApplication.run(StudentManagementApplication.class, args);
  }

  @GetMapping("/hello")
  public String hello() {
    return "Hello, World!";
  }

  @GetMapping("/name")
  public String getName() {
    return name;
  }

  @PostMapping("/name")
  public void setName(String name) {
    this.name = name;
  }

  @GetMapping("/info")
  public String getInfo() {
    return name + " " + age;
  }

  @PostMapping("/info")
  public void setInfo(String name, String age) {
    this.name = name;
    this.age = age;
  }

  @GetMapping("/infoMap")
  public String getInfoMap() {
    return map.keySet().stream()
        .map(name -> name + " " + map.get(name))
        .collect(Collectors.joining(","));
  }

  @PostMapping("/infoMap")
  public void setInfoMap(String name, String age) {
    if (!map.containsKey(name)) {
      map.put(name, age);
    }
  }
}
