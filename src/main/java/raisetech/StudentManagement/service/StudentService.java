package raisetech.StudentManagement.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

/**
 * 受講生情報を取り扱うサービスです。 受講生の検索や登録、更新処理を行います。
 */
@Service
public class StudentService {

  private final StudentRepository repository;
  private final StudentConverter converter;

  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  /**
   * 受講生一覧検索です。 全件検索を行うので、条件指定は行いません。
   *
   * @return 受講生一覧（全件）
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.searchStudents();
    List<StudentCourse> studentCourseList = repository.searchStudentCourses();
    return converter.convertStudentDetails(studentList, studentCourseList);
  }

  /**
   * 受講生検索です。 IDに紐づく受講生を検索した後、その受講生に紐づく受講生コース情報を取得して設定します。
   *
   * @param id 受講生ID
   * @return 受講生
   */
  @Transactional(readOnly = true)
  public StudentDetail searchStudent(int id) {
    Student student = repository.searchStudent(id);
    List<StudentCourse> studentCourses = repository.searchStudentCoursesByStudentId(id);
    return new StudentDetail(student, studentCourses);
  }

  @Transactional
  public StudentDetail registerStudentDetail(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();
    student.setDeleted(false);
    repository.registerStudent(student);
    StudentCourse studentCourse = studentDetail.getStudentCourses().getFirst();
    studentCourse.setStudentId(student.getId());
    studentCourse.setCourseStartDt(LocalDateTime.now());
    studentCourse.setCourseEndDt(studentCourse.getCourseStartDt().plusMonths(3));
    repository.registerStudentCourse(studentCourse);
    return studentDetail;
  }

  @Transactional
  public void updateStudentDetail(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    studentDetail.getStudentCourses().forEach(repository::updateStudentCourse);
  }
}
