package vn.ute.mobile.project.form.grammar;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateGrammarForm {
  @NotEmpty(message = "name cannot null")
  private String name;
  @NotEmpty(message = "content cannot null")
  private String content;
}
