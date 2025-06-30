package raisetech.StudentManagement.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.exception.TestException;
import raisetech.StudentManagement.service.StudentService;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして受け付けるコントローラーです。
 */
@Validated
@RestController
public class StudentController {

  private final StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   * 受講生詳細の一覧検索です。 全件検索を行うので、条件指定は行いません。
   *
   * @return 受講生詳細一覧（全件）
   */
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    return service.searchStudentList();
  }

  @GetMapping("/studentListWithEx")
  public List<StudentDetail> getStudentListWithEx() throws TestException {
    throw new TestException("getStudentListWithEx　は使用禁止です。getStudentList　を使ってください。");
  }

  /**
   * 受講生詳細の検索です。 IDに紐づく任意の受講生詳細の情報を取得します。
   *
   * @param id 受講生ID
   * @return 受講生詳細
   */
  @GetMapping("/student/{id}")
  public StudentDetail getStudent(@PathVariable @Range(min = 1, max = 999) int id) {
    return service.searchStudent(id);
  }

  /**
   * 受講生詳細の新規登録を行います。
   *
   * @param studentDetail 受講生詳細
   * @return 登録した受講生詳細
   */
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(
      @RequestBody @Valid StudentDetail studentDetail) {
    StudentDetail responseStudentDetail = service.registerStudentDetail(studentDetail);
    return ResponseEntity.ok(responseStudentDetail);
  }

  /**
   * 受講生詳細の更新を行います。キャッチフラグの更新もここで行います（論理削除）
   *
   * @param studentDetail 受講生詳細
   * @return 更新結果
   */
  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudentPost(@RequestBody @Valid StudentDetail studentDetail) {
    service.updateStudentDetail(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }
}
