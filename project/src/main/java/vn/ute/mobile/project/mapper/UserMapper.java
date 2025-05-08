package vn.ute.mobile.project.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import vn.ute.mobile.project.dto.user.UserDto;
import vn.ute.mobile.project.model.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    uses = {AccountMapper.class})
public interface UserMapper {
  @Mapping(source = "account", target = "account", qualifiedByName = "fromAccountToDto")
  @BeanMapping(ignoreByDefault = true)
  @Named("fromUserToDto")
  UserDto fromUserToDto(User user);
}
