package vn.ute.mobile.project.dto.plashcard;

import lombok.Getter;
import lombok.Setter;
import vn.ute.mobile.project.dto.deck.DeckDto;

@Getter
@Setter
public class PlashCardDto {
  private String englishWord;
  private String vietnameseMeaning;
  private String type;
  private String example;
  private String image;
  private DeckDto deck;
}
