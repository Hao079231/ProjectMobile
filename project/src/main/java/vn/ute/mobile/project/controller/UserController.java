package vn.ute.mobile.project.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ute.mobile.project.dto.ApiMessageDto;
import vn.ute.mobile.project.dto.ErrorCode;
import vn.ute.mobile.project.dto.user.UserDto;
import vn.ute.mobile.project.exception.BabRequestException;
import vn.ute.mobile.project.exception.NotFoundException;
import vn.ute.mobile.project.form.user.UpdateUserForm;
import vn.ute.mobile.project.mapper.UserMapper;
import vn.ute.mobile.project.model.Account;
import vn.ute.mobile.project.model.CustomUserPrincipal;
import vn.ute.mobile.project.model.User;
import vn.ute.mobile.project.repository.AccountRepository;
import vn.ute.mobile.project.repository.UserRepository;

@RestController
@RequestMapping("/v1/api/user")
public class UserController {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserMapper userMapper;
  @Autowired
  private AccountRepository accountRepository;

  @GetMapping("/get")
  public ApiMessageDto<UserDto> get(){
    ApiMessageDto<UserDto> apiMessageDto = new ApiMessageDto<>();
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserPrincipal)) {
      throw new RuntimeException("User not authenticated");
    }
    CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();
    User user = userRepository.findById(principal.getUserId()).orElseThrow(() -> new NotFoundException("User not found"));
    apiMessageDto.setData(userMapper.fromUserToDto(user));
    apiMessageDto.setMessage("Get user successfully");
    return apiMessageDto;
  }

  @PutMapping("/update")
  public ApiMessageDto<String> update(@RequestBody @Valid UpdateUserForm request){
    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserPrincipal)) {
      throw new RuntimeException("User not authenticated");
    }
    CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();
    User user = userRepository.findById(principal.getUserId()).orElseThrow(() -> new NotFoundException("User not found"));
    if (accountRepository.findAccountByUsername(request.getUsername()) != null){
      throw new BabRequestException("User name already exist", ErrorCode.ACCOUNT_ERROR_USERNAME_EXIST);
    }
    Account account = accountRepository.findById(principal.getUserId()).orElseThrow(() -> new NotFoundException("Account not found"));

    account.setUsername(request.getUsername());
    account.setFullname(request.getFullname());
    account.setEmail(request.getEmail());
    accountRepository.save(account);

    userMapper.updateUpdateUserToEntity(request, user);
    userRepository.save(user);
    apiMessageDto.setMessage("Update user successfully");
    return apiMessageDto;
  }
}
