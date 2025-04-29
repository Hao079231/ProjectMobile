package vn.ute.mobile.project.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import vn.ute.mobile.project.dto.account.AccountDto;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
  private String id;
  private String phone;
  private String image;
  private AccountDto account;
}
