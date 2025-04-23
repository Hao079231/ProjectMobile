package vn.ute.mobile.project.dto.user;

import lombok.Getter;
import lombok.Setter;
import vn.ute.mobile.project.dto.account.AccountDto;

@Getter
@Setter
public class UserDto {
  private String id;
  private String phone;
  private String address;
  private String image;
  private Integer score;
  private Integer ordinalNumber;
  private AccountDto account;
}
