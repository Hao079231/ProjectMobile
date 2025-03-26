package vn.ute.mobile.project.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateAuthForm {
  @NotEmpty(message = "username cannot null")
  private String username;
  @NotEmpty(message = "password cannot null")
  private String password;
}
