package dev.mikoto2000.springbootstudy.validation.firststep.model;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @NotEmpty
    private String id;

    @NotEmpty
    private String name;

    @NotNull
    private Boolean useMail;

    @Email
    private String mail;

    // useMail が true の時のみ mail が必須
    @AssertTrue(message = "メールを使用する場合は、メールアドレスを指定してください")
    private boolean isUseMail() {
      if (useMail && mail == null) {
        // useMail が true なのにメールアドレスを設定していない場合、バリデーションエラー
        return false;
      }

      return true;
    }
}

