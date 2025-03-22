package vn.ute.mobile.project.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import vn.ute.mobile.project.form.user.CreateUserForm;
import vn.ute.mobile.project.model.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    uses = {AccountMapper.class})
public interface UserMapper {
  @Mapping(source = "phone", target = "phone")
  @Mapping(source = "gender", target = "gender")
  @BeanMapping(ignoreByDefault = true)
  @Named("fromCreateUserToEntity")
  User fromCreateUserToEntity(CreateUserForm createUserForm);
}
