package vn.ute.mobile.project.form.login;

import lombok.Getter;
import lombok.Setter;
import vn.ute.mobile.project.validation.Password;

@Getter
@Setter
public class CreateAdminLogin {
  private String username;
  @Password
  private String password;
}
