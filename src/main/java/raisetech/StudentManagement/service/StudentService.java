package raisetech.StudentManagement.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;
import raisetech.StudentManagement.service.converter.StudentConverter;

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

  /**
   * 受講生とその受講生に紐づく受講生コース情報を登録します。
   *
   * @param studentDetail 登録する受講生詳細
   * @return 登録された受講生詳細
   */
  @Transactional
  public StudentDetail registerStudentDetail(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();
    student.setDeleted(false);
    repository.registerStudent(student);
    studentDetail.getStudentCourses().forEach(studentCourse -> {
      studentCourse.setStudentId(student.getId());
      studentCourse.setCourseStartDt(LocalDateTime.now());
      studentCourse.setCourseEndDt(studentCourse.getCourseStartDt().plusMonths(3));
      repository.registerStudentCourse(studentCourse);
    });
    return studentDetail;
  }

  /**
   * 受講生とその受講生に紐づく受講生コース情報を更新します。
   *
   * @param studentDetail 受講生詳細
   */
  @Transactional
  public void updateStudentDetail(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    studentDetail.getStudentCourses().forEach(repository::updateStudentCourse);
  }
}
