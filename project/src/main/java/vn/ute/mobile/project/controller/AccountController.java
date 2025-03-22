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
import vn.ute.mobile.project.form.login.CreateAdminLogin;
import vn.ute.mobile.project.form.login.CreateUserLogin;
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

  @PostMapping("/login")
  public ApiMessageDto<String> login(@RequestBody @Valid CreateAdminLogin request){
    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
    Account account = accountRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword()).
        orElseThrow(() -> new BabRequestException("Username or password invalid", ErrorCode.ACCOUNT_ERROR_BADREQUEST));
    apiMessageDto.setMessage("Login successful");
    return apiMessageDto;
  }

  @PostMapping("/user-login")
  public ApiMessageDto<String> loginByUser(@RequestBody @Valid CreateUserLogin request){
    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
    Account account = accountRepository.findByEmailAndPassword(request.getEmail(), request.getPassword()).
        orElseThrow(() -> new BabRequestException("Email or password invalid", ErrorCode.ACCOUNT_ERROR_BADREQUEST));
    if (account.getStatus().equals(AppConstant.ACCOUNT_STATUS_PENDING)){
      throw new BabRequestException("Account not active", ErrorCode.ACCOUNT_ERROR_NOTACTIVE);
    }
    apiMessageDto.setMessage("Login successful");
    return apiMessageDto;
  }
}
