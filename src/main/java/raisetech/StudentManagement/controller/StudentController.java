package raisetech.StudentManagement.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
  @Operation(summary = "受講生一覧検索", description = "受講生詳細の一覧を取得します。")
  @ApiResponse(
      responseCode = "200",
      description = "正常終了",
      content = @Content(
          mediaType = "application/json",
          array = @ArraySchema(schema = @Schema(implementation = StudentDetail.class))))
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    return service.searchStudentList();
  }

  @Hidden
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
  @Operation(
      summary = "受講生検索",
      description = "受講生IDを指定して受講生詳細を取得します。")
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "正常終了",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = StudentDetail.class))),
      @ApiResponse(
          responseCode = "500",
          description = "パラメータ不正（ID範囲外）",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(example = """
                  {
                    "timestamp": "2025-07-02T22:49:08.721+00:00",
                    "status": 500,
                    "error": "Internal Server Error",
                    "message": "getStudent.id: 1 から 999 の間にしてください",
                    "path": "/student/1000"
                  }
                  """)
          )
      )
  })
  @GetMapping("/student/{id}")
  public StudentDetail getStudent(
      @Parameter(description = "受講生ID（1〜999）", example = "1", required = true)
      @PathVariable @Range(min = 1, max = 999) int id) {
    return service.searchStudent(id);
  }

  /**
   * 受講生詳細の新規登録を行います。
   *
   * @param studentDetail 受講生詳細
   * @return 登録した受講生詳細
   */
  @Operation(
      summary = "受講生登録",
      description = "受講生と受講生に紐づく受講生コース情報を登録します。",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          required = true,
          description = "登録する受講生詳細",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = StudentDetail.class)
          )
      ),
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "登録成功",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = StudentDetail.class)
              )
          ),
      }
  )
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
  @Operation(
      summary = "受講生更新",
      description = "受講生とそのコース情報を更新します。",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          required = true,
          description = "更新する受講生詳細",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = StudentDetail.class)
          )
      ),
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "更新成功",
              content = @Content(
                  mediaType = "text/plain",
                  schema = @Schema(example = "更新処理が成功しました。")
              )
          ),
      }
  )
  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudentPost(@RequestBody @Valid StudentDetail studentDetail) {
    service.updateStudentDetail(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }
}
