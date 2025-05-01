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
import vn.ute.mobile.project.dto.grammar.GrammarDto;
import vn.ute.mobile.project.form.grammar.CreateGrammarForm;
import vn.ute.mobile.project.form.grammar.UpdateGrammarForm;
import vn.ute.mobile.project.model.Grammar;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface GrammarMapper {
  @Mapping(source = "name", target = "name")
  @Mapping(source = "content", target = "content")
  @BeanMapping(ignoreByDefault = true)
  Grammar fromCreateToGrammar(CreateGrammarForm createGrammarForm);

  @Mapping(source = "name", target = "name")
  @Mapping(source = "content", target = "content")
  @BeanMapping(ignoreByDefault = true)
  void updateGrammar(UpdateGrammarForm updateGrammarForm, @MappingTarget Grammar grammar);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "name", target = "name")
  @Mapping(source = "content", target = "content")
  @BeanMapping(ignoreByDefault = true)
  @Named("fromGrammarToDto")
  GrammarDto fromGrammarToDto(Grammar grammar);

  @IterableMapping(elementTargetType = GrammarDto.class, qualifiedByName = "fromGrammarToDto")
  List<GrammarDto> fromGrammarToDtoList(List<Grammar> grammars);
}
