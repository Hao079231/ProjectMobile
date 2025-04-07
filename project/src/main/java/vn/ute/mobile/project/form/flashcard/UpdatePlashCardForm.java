package vn.ute.mobile.project.form.flashcard;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import vn.ute.mobile.project.validation.WordType;

@Getter
@Setter
public class UpdatePlashCardForm {
  @NotNull(message = "Plash card id cannot null")
  private Long id;
  @NotEmpty(message = "English word cannot null")
  private String englishWord;
  @NotEmpty(message = "Vietnamese meaning cannot null")
  private String vietnameseMeaning;
  @WordType
  private String type;
  private String example;
  private String image;
  @NotNull(message = "Deck id cannot null")
  private Long deckId;
}
