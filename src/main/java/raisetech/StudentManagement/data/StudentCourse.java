package raisetech.StudentManagement.data;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class StudentCourse {

  private int id;
  private int studentId;

  @NotBlank(message = "必須です")
  @Size(max = 200, message = "{max}文字以内で入力してください")
  private String courseName;

  @FutureOrPresent(message = "現在日時または現在日時より後を指定してください")
  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  private LocalDateTime courseStartDt;

  @Future(message = "現在日時より後を指定してください")
  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  private LocalDateTime courseEndDt;
}
