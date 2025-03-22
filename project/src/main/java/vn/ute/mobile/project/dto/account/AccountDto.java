package vn.ute.mobile.project.dto.account;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import vn.ute.mobile.project.dto.AbasicDto;
import vn.ute.mobile.project.validation.Password;

@Getter
@Setter
public class AccountDto extends AbasicDto {
  private Long id;
  private String username;
  private String fullname;
  @Email
  private String email;
  @Password
  private String password;
}
