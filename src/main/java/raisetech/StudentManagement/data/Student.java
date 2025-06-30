package raisetech.StudentManagement.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {

  private int id;

  @NotBlank()
  @Size(max = 100)
  private String name;

  @NotBlank()
  @Size(max = 100)
  private String kanaName;

  @Size(max = 100)
  private String nickname;

  @NotBlank()
  @Size(max = 255)
  @Email()
  private String email;

  @Size(max = 100)
  private String area;

  @Min(value = 18)
  private Integer age;

  @Size(max = 10)
  private String gender;

  @Size(max = 255)
  private String remark;

  private boolean isDeleted;
}
