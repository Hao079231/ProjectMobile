package vn.ute.mobile.project.dto.user;

import lombok.Getter;
import lombok.Setter;
import vn.ute.mobile.project.dto.account.AccountDto;

@Getter
@Setter
public class UserDto {
  private Long id;
  private String phone;
  private Integer gender;
  private AccountDto account;
}
