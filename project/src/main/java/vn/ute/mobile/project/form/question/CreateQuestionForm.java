package vn.ute.mobile.project.form.question;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateQuestionForm {
  @NotEmpty(message = "Question cannot null")
  private String question;
  @NotEmpty(message = "Answer cannot null")
  private String answer;
  @NotEmpty(message = "All choice cannot null")
  private String allChoice;
}
