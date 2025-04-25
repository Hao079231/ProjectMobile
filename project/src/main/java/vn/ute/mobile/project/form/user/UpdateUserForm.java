package vn.ute.mobile.project.form.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserForm {
  @NotEmpty(message = "user name cannot null")
  private String username;
  @Email
  @NotEmpty(message = "email cannot null")
  private String email;
  private String phone;
  @NotEmpty(message = "address cannot null")
  private String address;
  private String image;
}
