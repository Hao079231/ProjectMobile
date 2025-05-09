package vn.ute.mobile.project.form.user_point;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserPointForm {
  @NotNull(message = "Point cannot be null")
  private Integer point;
}
