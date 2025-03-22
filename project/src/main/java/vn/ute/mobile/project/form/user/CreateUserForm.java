package vn.ute.mobile.project.form.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import vn.ute.mobile.project.validation.Gender;
import vn.ute.mobile.project.validation.Password;

@Getter
@Setter
public class CreateUserForm {
  @NotEmpty(message = "user name cannot null")
  private String username;
  @NotEmpty(message = "full name cannot null")
  private String fullname;
  @Email
  @NotEmpty(message = "email cannot null")
  private String email;
  @Password
  @NotEmpty(message = "password cannot null")
  private String password;
  @NotEmpty(message = "phone cannot null")
  private String phone;
  @NotNull(message = "gender cannot null")
  @Gender
  private Integer gender;
}
