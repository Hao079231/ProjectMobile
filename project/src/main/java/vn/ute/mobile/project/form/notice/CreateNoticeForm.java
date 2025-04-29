package vn.ute.mobile.project.form.notice;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateNoticeForm {
  @NotEmpty(message = "name cannot null")
  private String noticeName;
  @NotEmpty(message = "content cannot null")
  private String noticeContent;
}
