package vn.ute.mobile.project.mapper;

import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import vn.ute.mobile.project.dto.account.AccountDto;
import vn.ute.mobile.project.form.account.CreateAccountForm;
import vn.ute.mobile.project.model.Account;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AccountMapper {
  @Mapping(source = "username", target = "username")
  @Mapping(source = "fullname", target = "fullname")
  @Mapping(source = "email", target = "email")
  @Mapping(source = "password", target = "password")
  @BeanMapping(ignoreByDefault = true)
  @Named("fromCreateAccountToEntity")
  Account fromCreateAccountToEntity(CreateAccountForm createAccountForm);

  @Mapping(source = "username", target = "username")
  @Mapping(source = "fullname", target = "fullname")
  @Mapping(source = "email", target = "email")
  @Mapping(source = "password", target = "password")
  @BeanMapping(ignoreByDefault = true)
  @Named("fromAccountToDto")
  AccountDto fromAccountToDto(Account account);

  @IterableMapping(elementTargetType = AccountDto.class, qualifiedByName = "fromAccountToDto")
  List<AccountDto> fromAccountToDtoList(List<Account> accounts);
}
