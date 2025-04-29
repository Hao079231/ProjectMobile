package vn.ute.mobile.project.dto.notice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import vn.ute.mobile.project.dto.user.UserDto;

@Getter
@Setter
public class NoticeDto {
  private String id;
  @JsonIgnoreProperties({"image", "otp", "phone"})
  private UserDto user;
  private String noticeName;
  private String noticeContent;
}
