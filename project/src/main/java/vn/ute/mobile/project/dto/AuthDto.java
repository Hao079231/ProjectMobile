package vn.ute.mobile.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthDto {
  private Boolean result;
  private String token;
}
