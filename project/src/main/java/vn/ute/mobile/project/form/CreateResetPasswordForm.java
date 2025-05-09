package vn.ute.mobile.project.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import vn.ute.mobile.project.validation.Password;

@Getter
@Setter
public class CreateResetPasswordForm {
  @NotEmpty(message = "Email cannot null")
  private String email;
  @NotEmpty(message = "Password cannot null")
  @Password
  private String password;
}
