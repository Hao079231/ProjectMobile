package vn.ute.mobile.project.mapper;

import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import vn.ute.mobile.project.dto.user_point.UserPointDto;
import vn.ute.mobile.project.form.user_point.CreateUserPointForm;
import vn.ute.mobile.project.model.UserPoint;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    uses = {UserMapper.class})
public interface UserPointMapper {
  @Mapping(source = "point", target = "point")
  @BeanMapping(ignoreByDefault = true)
  UserPoint fromCreateToUserPoint(CreateUserPointForm createUserPointForm);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "user", target = "user", qualifiedByName = "fromUserToDto")
  @Mapping(source = "point", target = "point")
  @Mapping(source = "time", target = "time")
  @BeanMapping(ignoreByDefault = true)
  @Named("fromUserPointToDto")
  UserPointDto fromUserPointToDto(UserPoint userPoint);

  @IterableMapping(elementTargetType = UserPointDto.class, qualifiedByName = "fromUserPointToDto")
  List<UserPointDto> fromUserPointToDtoList(List<UserPoint> userPoints);
}
