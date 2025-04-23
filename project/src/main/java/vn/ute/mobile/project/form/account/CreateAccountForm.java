package vn.ute.mobile.project.form.account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import vn.ute.mobile.project.validation.Password;

@Getter
@Setter
public class CreateAccountForm {
  @NotEmpty(message = "user name cannot null")
  private String username;
  @Email
  @NotEmpty(message = "email cannot null")
  private String email;
  @Password
  @NotEmpty(message = "password cannot null")
  private String password;
}
