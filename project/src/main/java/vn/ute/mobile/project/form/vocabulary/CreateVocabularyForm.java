package vn.ute.mobile.project.form.vocabulary;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateVocabularyForm {
  @NotEmpty(message = "word cannot empty")
  private String word;
  @NotEmpty(message = "meaning cannot empty")
  private String meaning;
}
