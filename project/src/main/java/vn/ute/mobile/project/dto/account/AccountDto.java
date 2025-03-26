package vn.ute.mobile.project.dto.account;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDto {
  private Long id;
  private String username;
  private String fullname;
  private String email;
}
