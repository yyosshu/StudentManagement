package raisetech.StudentManagement.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCourse {

  private int id;
  private int studentId;

  @NotBlank()
  @Size(max = 200)
  private String courseName;

  private LocalDateTime courseStartDt;
  private LocalDateTime courseEndDt;
}
