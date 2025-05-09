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
import vn.ute.mobile.project.dto.dto.QuestionDto;
import vn.ute.mobile.project.form.question.CreateQuestionForm;
import vn.ute.mobile.project.form.question.UpdateQuestionForm;
import vn.ute.mobile.project.model.Question;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface QuestionMapper {
  @Mapping(source = "question", target = "question")
  @Mapping(source = "answer", target = "answer")
  @Mapping(source = "allChoice", target = "allChoice")
  @BeanMapping(ignoreByDefault = true)
  Question fromCreateToQuestion(CreateQuestionForm createQuestionForm);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "question", target = "question")
  @Mapping(source = "answer", target = "answer")
  @Mapping(source = "allChoice", target = "allChoice")
  @BeanMapping(ignoreByDefault = true)
  void updateQuestion(UpdateQuestionForm updateQuestionForm, @MappingTarget Question question);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "question", target = "question")
  @Mapping(source = "answer", target = "answer")
  @Mapping(source = "allChoice", target = "allChoice")
  @BeanMapping(ignoreByDefault = true)
  @Named("fromQuestionToDto")
  QuestionDto fromQuestionToDto(Question question);

  @IterableMapping(elementTargetType = QuestionDto.class, qualifiedByName = "fromQuestionToDto")
  List<QuestionDto> fromQuestionToDtoList(List<Question> questions);
}
