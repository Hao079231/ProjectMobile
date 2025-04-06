package vn.ute.mobile.project.form.deck;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDeckForm {
  @NotNull(message = "id deck cannot null")
  private Long id;
  @NotEmpty(message = "name deck cannot null")
  private String name;
}
