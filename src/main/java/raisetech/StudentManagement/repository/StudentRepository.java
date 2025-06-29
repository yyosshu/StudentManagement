package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

/**
 * 受講生テーブルと受講生コース情報テーブルと紐づくリポジトリです。
 */
@Mapper
public interface StudentRepository {

  /**
   * 受講生の全件検索を行います。
   *
   * @return 受講生一覧（全件）
   */
  @Select("SELECT * FROM students WHERE is_deleted = FALSE")
  List<Student> searchStudents();

  /**
   * 受講生の検索を行います。
   *
   * @param id 受講生ID
   * @return 受講生
   */
  @Select("SELECT * FROM students WHERE id=#{id}")
  Student searchStudent(int id);

  /**
   * 受講生コース情報の全件検索を行います。
   *
   * @return 受講生コース情報一覧（全件）
   */
  @Select("SELECT * FROM students_courses")
  List<StudentCourse> searchStudentCourses();

  /**
   * 受講生IDに紐づく受講生コース情報を検索します。
   *
   * @param studentId 受講生ID
   * @return 受講生IDにも紐づく受講生コース情報
   */
  @Select("SELECT * FROM students_courses WHERE student_id=#{studentId}")
  List<StudentCourse> searchStudentCoursesByStudentId(int studentId);

  /**
   * 受講生を登録します。
   *
   * @param student 受講生
   */
  @Insert("INSERT INTO students (name, kana_name, nickname, email, area, age, gender, remark, is_deleted) VALUES (#{name}, #{kanaName}, #{nickname}, #{email}, #{area}, #{age}, #{gender}, #{remark}, #{deleted})")
  @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
  void registerStudent(Student student);

  /**
   * 受講生コース情報を登録します。
   *
   * @param course 受講生コース情報
   */
  @Insert("INSERT INTO students_courses (student_id, course_name, course_start_dt, course_end_dt) VALUES (#{studentId}, #{courseName}, #{courseStartDt}, #{courseEndDt})")
  @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
  void registerStudentCourse(StudentCourse course);

  /**
   * 受講生を更新します。
   *
   * @param student 受講生
   */
  @Update("UPDATE students SET name=#{name}, kana_name=#{kanaName}, nickname=#{nickname}, email=#{email}, area=#{area}, age=#{age}, gender=#{gender}, remark=#{remark}, is_deleted=#{deleted} WHERE id=#{id}")
  void updateStudent(Student student);

  /**
   * 受講生コース情報を更新します。
   *
   * @param course 受講生コース情報
   */
  @Update("UPDATE students_courses SET course_name=#{courseName}, course_start_dt=#{courseStartDt}, course_end_dt=#{courseEndDt} WHERE id=#{id}")
  void updateStudentCourse(StudentCourse course);
}
