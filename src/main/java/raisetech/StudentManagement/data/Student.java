package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生")
@Getter
@Setter
public class Student {

  @Schema(description = "ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
  private int id;

  @NotBlank()
  @Size(max = 100)
  @Schema(description = "氏名", example = "山田 太郎")
  private String name;

  @NotBlank()
  @Size(max = 100)
  @Schema(description = "カナ氏名", example = "ヤマダ タロウ")
  private String kanaName;

  @Size(max = 100)
  @Schema(description = "ニックネーム", example = "タロ")
  private String nickname;

  @NotBlank()
  @Size(max = 255)
  @Email()
  @Schema(description = "メールアドレス", format = "email", example = "taro@example.com")
  private String email;

  @Size(max = 100)
  @Schema(description = "地域", example = "東京都")
  private String area;

  @Min(value = 18)
  @Schema(description = "年齢 (18 歳以上)", example = "25")
  private Integer age;

  @Size(max = 10)
  @Schema(description = "性別", example = "男性")
  private String gender;

  @Size(max = 255)
  @Schema(description = "備考", example = "夜間コース希望")
  private String remark;

  @Schema(description = "削除済みフラグ", example = "false", requiredMode = Schema.RequiredMode.REQUIRED)
  private boolean deleted;
}
