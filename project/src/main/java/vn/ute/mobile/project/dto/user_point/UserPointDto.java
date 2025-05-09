package vn.ute.mobile.project.dto.user_point;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import vn.ute.mobile.project.dto.user.UserDto;

@Getter
@Setter
public class UserPointDto {
  private String id;
  private UserDto user;
  private Integer point;
  @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
  private LocalDateTime time;
}
