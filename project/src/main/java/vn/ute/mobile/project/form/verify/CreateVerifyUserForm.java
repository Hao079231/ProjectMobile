package vn.ute.mobile.project.form.verify;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateVerifyUserForm {
  @Email
  private String email;
  private String otp;
}
