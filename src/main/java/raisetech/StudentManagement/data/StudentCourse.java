package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生コース情報")
@Getter
@Setter
public class StudentCourse {

  @Schema(description = "ID", example = "1001", requiredMode = Schema.RequiredMode.REQUIRED)
  private int id;

  @Schema(description = "受講生ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
  private int studentId;

  @NotBlank()
  @Size(max = 200)
  @Schema(description = "コース名", example = "Javaコース")
  private String courseName;

  @Schema(description = "受講開始日", format = "date-time", example = "2025-07-01T10:00:00+09:00")
  private LocalDateTime courseStartDt;

  @Schema(description = "受講終了日", format = "date-time", example = "2025-09-30T18:00:00+09:00")
  private LocalDateTime courseEndDt;
}
