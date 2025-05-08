package vn.ute.mobile.project.form.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserForm {
  @NotEmpty(message = "user name cannot null")
  private String username;
}
