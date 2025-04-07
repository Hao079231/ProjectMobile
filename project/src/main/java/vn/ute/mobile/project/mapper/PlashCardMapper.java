package vn.ute.mobile.project.mapper;

import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import vn.ute.mobile.project.dto.plashcard.PlashCardDto;
import vn.ute.mobile.project.form.flashcard.CreatePlashCardForm;
import vn.ute.mobile.project.form.flashcard.UpdatePlashCardForm;
import vn.ute.mobile.project.model.PlashCard;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    uses = {DeckMapper.class})
public interface PlashCardMapper {
  @Mapping(source = "englishWord", target = "englishWord")
  @Mapping(source = "vietnameseMeaning", target = "vietnameseMeaning")
  @Mapping(source = "type", target = "type")
  @Mapping(source = "example", target = "example")
  @Mapping(source = "image", target = "image")
  @BeanMapping(ignoreByDefault = true)
  @Named("fromCreatePlashCardToEntity")
  PlashCard fromCreatePlashCardToEntity(CreatePlashCardForm createPlashCardForm);

  @Mapping(source = "englishWord", target = "englishWord")
  @Mapping(source = "vietnameseMeaning", target = "vietnameseMeaning")
  @Mapping(source = "type", target = "type")
  @Mapping(source = "example", target = "example")
  @Mapping(source = "image", target = "image")
  @BeanMapping(ignoreByDefault = true)
  @Named("fromCreatePlashCardToEntity")
  void updatePlashCardToEntity(UpdatePlashCardForm updatePlashCardForm, @MappingTarget PlashCard plashCard);

  @Mapping(source = "englishWord", target = "englishWord")
  @Mapping(source = "vietnameseMeaning", target = "vietnameseMeaning")
  @Mapping(source = "type", target = "type")
  @Mapping(source = "example", target = "example")
  @Mapping(source = "image", target = "image")
  @Mapping(source = "deck", target = "deck", qualifiedByName = "fromDeckToDto")
  @BeanMapping(ignoreByDefault = true)
  @Named("fromEntityToPlashCardDto")
  PlashCardDto fromEntityToPlashCardDto(PlashCard plashCard);

  @IterableMapping(elementTargetType = PlashCardDto.class, qualifiedByName = "fromEntityToPlashCardDto")
  List<PlashCardDto> fromEntityToPlashCardDtoList(List<PlashCard> plashCards);
}
