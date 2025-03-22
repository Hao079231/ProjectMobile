package vn.ute.mobile.project.form.login;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import vn.ute.mobile.project.validation.Password;

@Getter
@Setter
public class CreateUserLogin {
  @Email
  private String email;
  @Password
  private String password;
}
