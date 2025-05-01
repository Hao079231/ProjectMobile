package vn.ute.mobile.project.form.test;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTestForm {
  @NotEmpty(message = "question cannot null")
  private String question;
  @NotEmpty(message = "answer a cannot null")
  private String answerA;
  @NotEmpty(message = "answer b cannot null")
  private String answerB;
  @NotEmpty(message = "answer c cannot null")
  private String answerC;
  @NotEmpty(message = "answer d cannot null")
  private String answerD;
  @NotEmpty(message = "correct answer cannot null")
  private String correctAnswer;
}
