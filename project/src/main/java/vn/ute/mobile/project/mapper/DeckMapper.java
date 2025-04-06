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
import vn.ute.mobile.project.dto.deck.DeckDto;
import vn.ute.mobile.project.form.deck.CreateDeckForm;
import vn.ute.mobile.project.form.deck.UpdateDeckForm;
import vn.ute.mobile.project.model.Deck;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DeckMapper {
  @Mapping(source = "name", target = "name")
  @BeanMapping(ignoreByDefault = true)
  @Named("fromCreateDeckToEntity")
  Deck fromCreateDeckToEntity(CreateDeckForm createDeckForm);

  @Mapping(source = "name", target = "name")
  @BeanMapping(ignoreByDefault = true)
  @Named("updateDeckToEntity")
  void updateDeckToEntity(UpdateDeckForm updateDeckForm, @MappingTarget Deck deck);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "name", target = "name")
  @BeanMapping(ignoreByDefault = true)
  @Named("fromDeckToDto")
  DeckDto fromDeckToDto(Deck deck);

  @IterableMapping(elementTargetType = DeckDto.class, qualifiedByName = "fromDeckToDto")
  List<DeckDto> fromDeckToDtoList(List<Deck> phoneCalls);
}
