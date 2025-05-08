package vn.ute.mobile.project.form.topic;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTopicForm {
  @NotEmpty(message = "id cannot null")
  private String id;
  @NotEmpty(message = "image topic cannot null")
  private String imageView;
  @NotEmpty(message = "topic cannot null")
  private String topic;
}
