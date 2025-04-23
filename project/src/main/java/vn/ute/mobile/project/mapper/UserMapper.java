package vn.ute.mobile.project.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import vn.ute.mobile.project.dto.user.UserDto;
import vn.ute.mobile.project.form.user.CreateUserForm;
import vn.ute.mobile.project.form.user.UpdateUserForm;
import vn.ute.mobile.project.model.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    uses = {AccountMapper.class})
public interface UserMapper {
  @Mapping(source = "phone", target = "phone")
  @Mapping(source = "address", target = "address")
  @Mapping(source = "image", target = "image")
  @BeanMapping(ignoreByDefault = true)
  @Named("fromCreateUserToEntity")
  User fromCreateUserToEntity(CreateUserForm createUserForm);

  @Mapping(source = "phone", target = "phone")
  @Mapping(source = "address", target = "address")
  @Mapping(source = "image", target = "image")
  @BeanMapping(ignoreByDefault = true)
  @Named("updateUpdateUserToEntity")
  void updateUpdateUserToEntity(UpdateUserForm updateUserForm, @MappingTarget User user);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "account", target = "account", qualifiedByName = "fromAccountToDto")
  @Mapping(source = "phone", target = "phone")
  @BeanMapping(ignoreByDefault = true)
  @Named("fromUserToDto")
  UserDto fromUserToDto(User user);
}
