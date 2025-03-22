package vn.ute.mobile.project.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ute.mobile.project.constant.AppConstant;
import vn.ute.mobile.project.dto.ApiMessageDto;
import vn.ute.mobile.project.dto.ErrorCode;
import vn.ute.mobile.project.exception.BabRequestException;
import vn.ute.mobile.project.form.account.CreateAccountForm;
import vn.ute.mobile.project.mapper.AccountMapper;
import vn.ute.mobile.project.model.Account;
import vn.ute.mobile.project.repository.AccountRepository;

@RestController
@RequestMapping("/v1/api")
public class AccountController {
  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private AccountMapper accountMapper;

  @PostMapping("/register")
  public ApiMessageDto<String> register(@RequestBody @Valid CreateAccountForm request){
    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
    Account account = accountRepository.findFirstByUsername(request.getUsername());
    if (account != null){
      throw new BabRequestException("Username already exist", ErrorCode.ACCOUNT_ERROR_USERNAME_EXIST);
    }
    account = accountMapper.fromCreateAccountToEntity(request);
    account.setIsAdmin(AppConstant.ACCOUNT_IS_ADMIN);
    account.setStatus(AppConstant.ACCOUNT_STATUS_ACTIVE);
    accountRepository.save(account);
    apiMessageDto.setMessage("Register account successful");
    return apiMessageDto;
  }
}
