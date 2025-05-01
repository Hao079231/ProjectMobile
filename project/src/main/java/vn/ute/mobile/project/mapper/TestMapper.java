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
import vn.ute.mobile.project.dto.test.TestDto;
import vn.ute.mobile.project.form.test.CreateTestForm;
import vn.ute.mobile.project.form.test.UpdateTestForm;
import vn.ute.mobile.project.model.Test;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TestMapper {
  @Mapping(source = "question", target = "question")
  @Mapping(source = "answerA", target = "answerA")
  @Mapping(source = "answerB", target = "answerB")
  @Mapping(source = "answerC", target = "answerC")
  @Mapping(source = "answerD", target = "answerD")
  @Mapping(source = "correctAnswer", target = "correctAnswer")
  @BeanMapping(ignoreByDefault = true)
  Test fromCreateToTest(CreateTestForm createTestForm);

  @Mapping(source = "question", target = "question")
  @Mapping(source = "answerA", target = "answerA")
  @Mapping(source = "answerB", target = "answerB")
  @Mapping(source = "answerC", target = "answerC")
  @Mapping(source = "answerD", target = "answerD")
  @Mapping(source = "correctAnswer", target = "correctAnswer")
  @BeanMapping(ignoreByDefault = true)
  void updateTest(UpdateTestForm updateTestForm, @MappingTarget Test test);

  @Mapping(source = "question", target = "question")
  @Mapping(source = "answerA", target = "answerA")
  @Mapping(source = "answerB", target = "answerB")
  @Mapping(source = "answerC", target = "answerC")
  @Mapping(source = "answerD", target = "answerD")
  @Mapping(source = "correctAnswer", target = "correctAnswer")
  @BeanMapping(ignoreByDefault = true)
  @Named("fromTestToDto")
  TestDto fromTestToDto(Test test);

  @IterableMapping(elementTargetType = TestDto.class, qualifiedByName = "fromTestToDto")
  List<TestDto> fromTestToDtoList(List<Test> tests);
}
