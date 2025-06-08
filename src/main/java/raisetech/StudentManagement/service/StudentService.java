package raisetech.StudentManagement.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.repository.StudentRepository;

@Service
public class StudentService {

  private final StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    // 年齢が30代の学生のみ抽出する。
    return repository.search().stream()
        .filter(student -> student.getAge() >= 30 && student.getAge() <= 39).toList();
  }

  public List<StudentCourse> searchStudentCourseList() {
    // コース名が「Javaコース」のコースのみ抽出する。
    return repository.searchStudentCourses().stream()
        .filter(studentCourse -> studentCourse.getCourseName().equals("Javaコース")).toList();
  }
}
