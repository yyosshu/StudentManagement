package raisetech.StudentManagement.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

@Controller
public class StudentController {

  private final StudentService service;
  private final StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  @GetMapping("/studentList")
  public String getStudentList(Model model) {
    List<Student> students = service.searchStudentList();
    List<StudentCourse> studentCourses = service.searchStudentCourseList();
    model.addAttribute("studentList", converter.convertStudentDetails(students, studentCourses));
    return "studentList";
  }

  @GetMapping("/studentDetailList")
  public String getStudentDetailList(Model model) {
    List<Student> students = service.searchStudentList();
    List<StudentCourse> studentCourses = service.searchStudentCourseList();
    model.addAttribute("studentList", converter.convertStudentDetails(students, studentCourses));
    return "studentDetailList";
  }

  @GetMapping("/registerStudent")
  public String registerStudentGet(Model model) {
    model.addAttribute("studentDetail", new StudentDetail());
    return "registerStudent";
  }

  @PostMapping("/registerStudent")
  public String registerStudentPost(
      @Valid @ModelAttribute StudentDetail studentDetail,
      BindingResult result) {
    if (result.hasErrors()) {
      return "registerStudent";
    }
    service.registerStudentDetail(studentDetail);
    return "redirect:/studentDetailList";
  }

  @GetMapping("/updateStudent/{studentId}")
  public String updateStudentGet(@PathVariable int studentId, Model model) {
    StudentDetail studentDetail = service.getStudentDetail(studentId);
    model.addAttribute("studentDetail", studentDetail);
    return "updateStudent";
  }

  @PostMapping("/updateStudent")
  public String updateStudentPost(
      @Valid @ModelAttribute StudentDetail studentDetail,
      BindingResult result) {
    if (result.hasErrors()) {
      return "updateStudent";
    }
    service.updateStudentDetail(studentDetail);
    return "redirect:/studentDetailList";
  }
}
