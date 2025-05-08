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
import vn.ute.mobile.project.dto.topic.TopicDto;
import vn.ute.mobile.project.form.topic.CreateTopicForm;
import vn.ute.mobile.project.form.topic.UpdateTopicForm;
import vn.ute.mobile.project.model.TopicModel;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TopicMapper {
  @Mapping(source = "imageView", target = "imageView")
  @Mapping(source = "topic", target = "topic")
  @BeanMapping(ignoreByDefault = true)
  TopicModel fromCreateTopicToEntity(CreateTopicForm createTopicForm);

  @Mapping(source = "imageView", target = "imageView")
  @Mapping(source = "topic", target = "topic")
  @BeanMapping(ignoreByDefault = true)
  void updateTopicModel(UpdateTopicForm updateTopicForm, @MappingTarget TopicModel topicModel);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "imageView", target = "imageView")
  @Mapping(source = "topic", target = "topic")
  @BeanMapping(ignoreByDefault = true)
  @Named("fromTopicModelToDto")
  TopicDto fromTopicModelToDto(TopicModel topicModel);

  @IterableMapping(elementTargetType = TopicDto.class, qualifiedByName = "fromTopicModelToDto")
  List<TopicDto> fromTopicModelToDtoList(List<TopicModel> topicModels);
}
