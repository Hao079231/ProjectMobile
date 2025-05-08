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
import vn.ute.mobile.project.dto.vocabulary.VocabularyDto;
import vn.ute.mobile.project.form.vocabulary.CreateVocabularyForm;
import vn.ute.mobile.project.form.vocabulary.UpdateVocabularyForm;
import vn.ute.mobile.project.model.Vocabulary;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    uses = {UserMapper.class})
public interface VocabularyMapper {
  @Mapping(source = "image", target = "image")
  @Mapping(source = "word", target = "word")
  @Mapping(source = "answer", target = "answer")
  @BeanMapping(ignoreByDefault = true)
  Vocabulary fromCreateToVocabulary(CreateVocabularyForm createVocabularyForm);

  @Mapping(source = "image", target = "image")
  @Mapping(source = "word", target = "word")
  @Mapping(source = "answer", target = "answer")
  @BeanMapping(ignoreByDefault = true)
  void updateVocabulary(UpdateVocabularyForm updateVocabularyForm, @MappingTarget Vocabulary vocabulary);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "image", target = "image")
  @Mapping(source = "word", target = "word")
  @Mapping(source = "answer", target = "answer")
  @BeanMapping(ignoreByDefault = true)
  @Named("fromVocabularyToDto")
  VocabularyDto fromVocabularyToDto(Vocabulary vocabulary);

  @IterableMapping(elementTargetType = VocabularyDto.class, qualifiedByName = "fromVocabularyToDto")
  List<VocabularyDto> fromVocabularyToDtoList(List<Vocabulary> vocabularies);
}
