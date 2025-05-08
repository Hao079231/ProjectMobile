package vn.ute.mobile.project.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import vn.ute.mobile.project.dto.account.AccountDto;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
  @JsonIgnoreProperties({"id"})
  private AccountDto account;
}
