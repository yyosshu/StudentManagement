package raisetech.StudentManagement.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

@Service
public class StudentService {

  private final StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    return repository.search();
  }

  public List<StudentCourse> searchStudentCourseList() {
    return repository.searchStudentCourses();
  }

  @Transactional
  public void registerStudentDetail(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();
    student.setDeleted(false);
    repository.registerStudent(student);
    StudentCourse studentCourse = studentDetail.getStudentCourses().getFirst();
    studentCourse.setStudentId(student.getId());
    repository.registerStudentCourse(studentCourse);
  }

  @Transactional(readOnly = true)
  public StudentDetail getStudentDetail(int studentId) {
    StudentDetail studentDetail = new StudentDetail();
    Student student = repository.searchById(studentId);
    List<StudentCourse> studentCourses = repository.searchStudentCoursesByStudentId(studentId);
    studentDetail.setStudent(student);
    studentDetail.setStudentCourses(studentCourses);
    return studentDetail;
  }

  @Transactional
  public void updateStudentDetail(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    studentDetail.getStudentCourses().forEach(repository::updateStudentCourse);
  }
}
