package vn.ute.mobile.project.form.topic;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTopicForm {
  @NotEmpty(message = "image topic cannot null")
  private String imageView;
  @NotEmpty(message = "topic cannot null")
  private String topic;
}
