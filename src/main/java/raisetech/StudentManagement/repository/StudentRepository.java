package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students")
  List<Student> search();

  @Select("SELECT * FROM students_courses")
  List<StudentCourse> searchStudentCourses();

  @Insert("INSERT INTO students (name, kana_name, nickname, email, area, age, gender, remark, is_deleted) VALUES (#{name}, #{kanaName}, #{nickname}, #{email}, #{area}, #{age}, #{gender}, #{remark}, #{deleted})")
  @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
  void registerStudent(Student student);

  @Insert("INSERT INTO students_courses (student_id, course_name, course_start_dt, course_end_dt) VALUES (#{studentId}, #{courseName}, #{courseStartDt}, #{courseEndDt})")
  @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
  void registerStudentCourse(StudentCourse course);
}
