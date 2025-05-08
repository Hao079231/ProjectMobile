package vn.ute.mobile.project.form.vocabulary;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateVocabularyForm {
  @NotEmpty(message = "id cannot null")
  private String id;
  @NotEmpty(message = "image cannot null")
  private String image;
  @NotEmpty(message = "word cannot null")
  private String word;
  @NotEmpty(message = "answer cannot null")
  private String answer;
  @NotEmpty(message = "topic cannot null")
  private String topicId;
}
