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

  @NotBlank(message = "必須です")
  @Size(max = 100, message = "{max}文字以内で入力してください")
  private String name;

  @NotBlank(message = "必須です")
  @Size(max = 100, message = "{max}文字以内で入力してください")
  private String kanaName;

  @Size(max = 100, message = "{max}文字以内で入力してください")
  private String nickname;

  @NotBlank(message = "必須です")
  @Email(message = "メールアドレス形式が不正です")
  @Size(max = 255, message = "{max}文字以内で入力してください")
  private String email;

  @Size(max = 100, message = "{max}文字以内で入力してください")
  private String area;

  @Min(value = 0, message = "{value}以上を入力してください")
  private Integer age;

  @Size(max = 10, message = "{max}文字以内で入力してください")
  private String gender;

  @Size(max = 255, message = "{max}文字以内で入力してください")
  private String remark;

  private boolean isDeleted;
}
