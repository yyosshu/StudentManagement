package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students WHERE is_deleted = FALSE")
  List<Student> searchStudents();

  @Select("SELECT * FROM students WHERE id=#{id}")
  Student searchStudent(int id);

  @Select("SELECT * FROM students_courses")
  List<StudentCourse> searchStudentCourses();

  @Select("SELECT * FROM students_courses WHERE student_id=#{studentId}")
  List<StudentCourse> searchStudentCoursesByStudentId(int studentId);

  @Insert("INSERT INTO students (name, kana_name, nickname, email, area, age, gender, remark, is_deleted) VALUES (#{name}, #{kanaName}, #{nickname}, #{email}, #{area}, #{age}, #{gender}, #{remark}, #{deleted})")
  @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
  void registerStudent(Student student);

  @Insert("INSERT INTO students_courses (student_id, course_name, course_start_dt, course_end_dt) VALUES (#{studentId}, #{courseName}, #{courseStartDt}, #{courseEndDt})")
  @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
  void registerStudentCourse(StudentCourse course);

  @Update("UPDATE students SET name=#{name}, kana_name=#{kanaName}, nickname=#{nickname}, email=#{email}, area=#{area}, age=#{age}, gender=#{gender}, remark=#{remark}, is_deleted=#{deleted} WHERE id=#{id}")
  void updateStudent(Student student);

  @Update("UPDATE students_courses SET course_name=#{courseName}, course_start_dt=#{courseStartDt}, course_end_dt=#{courseEndDt} WHERE id=#{id}")
  void updateStudentCourse(StudentCourse course);
}
