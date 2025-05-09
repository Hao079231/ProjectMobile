package vn.ute.mobile.project.controller;

import jakarta.validation.Valid;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.ute.mobile.project.config.JwtUtil;
import vn.ute.mobile.project.constant.AppConstant;
import vn.ute.mobile.project.dto.ApiMessageDto;
import vn.ute.mobile.project.dto.AuthDto;
import vn.ute.mobile.project.dto.ErrorCode;
import vn.ute.mobile.project.exception.BabRequestException;
import vn.ute.mobile.project.exception.NotFoundException;
import vn.ute.mobile.project.form.CreateAuthForm;
import vn.ute.mobile.project.form.CreateForgetPasswordForm;
import vn.ute.mobile.project.form.CreateResetPasswordForm;
import vn.ute.mobile.project.form.user.CreateUserForm;
import vn.ute.mobile.project.form.verify.CreateVerifyUserForm;
import vn.ute.mobile.project.mapper.UserMapper;
import vn.ute.mobile.project.model.Account;
import vn.ute.mobile.project.model.User;
import vn.ute.mobile.project.repository.AccountRepository;
import vn.ute.mobile.project.repository.UserRepository;
import vn.ute.mobile.project.service.EmailService;
import vn.ute.mobile.project.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/v1/api/auth/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthenticationController {
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private JwtUtil jwtUtil;
  @Autowired
  private UserServiceImpl userService;
  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private EmailService emailService;
  @Autowired
  private UserMapper userMapper;

  @PostMapping("/register")
  public ApiMessageDto<String> register(@RequestBody @Valid CreateUserForm request){
    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
    if (accountRepository.existsByUsername(request.getUsername())){
      throw new BabRequestException("User name already exist", ErrorCode.ACCOUNT_ERROR_USERNAME_EXIST);
    }
    if (accountRepository.existsByEmail(request.getEmail())){
      throw new BabRequestException("Email already exist", ErrorCode.ACCOUNT_ERROR_EMAIL_EXIST);
    }
    Account account = new Account();
    account.setUsername(request.getUsername());
    account.setEmail(request.getEmail());
    account.setPassword(passwordEncoder.encode(request.getPassword()));
    account.setStatus(AppConstant.ACCOUNT_STATUS_PENDING);

    String otpCode = generateOTP();

    User user = new User();
    user.setAccount(account);
    user.setStatus(AppConstant.ACCOUNT_STATUS_PENDING);
    user.setOtp(otpCode);
    userRepository.save(user);
    sendOTPEmail(request.getEmail(), otpCode, request.getUsername());
    apiMessageDto.setMessage("Registration successful. An OTP has been sent to your email");
    return apiMessageDto;
  }

  @PostMapping("/verify")
  public ApiMessageDto<String> verify(@RequestBody @Valid CreateVerifyUserForm request){
    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
    Account account = accountRepository.findByEmail(request.getEmail()).orElseThrow(() ->
        new NotFoundException("Account not found", ErrorCode.ACCOUNT_ERROR_NOTFOUND));

    User user = userRepository.findByIdAndOtp(account.getId(), request.getOtp());
    if (user == null){
      throw new NotFoundException("OTP code invalid", ErrorCode.ACCOUNT_ERROR_OTP_INVALID);
    }

    account.setStatus(AppConstant.ACCOUNT_STATUS_ACTIVE);
    accountRepository.save(account);
    user.setStatus(AppConstant.ACCOUNT_STATUS_ACTIVE);
    userRepository.clearOtpById(user.getId());
    userRepository.save(user);
    apiMessageDto.setMessage("Verify successful");
    return apiMessageDto;
  }

  @PostMapping("/login")
  public ApiMessageDto<AuthDto> login(@RequestBody @Valid CreateAuthForm request){
    ApiMessageDto<AuthDto> apiMessageDto = new ApiMessageDto<>();
    Account user = accountRepository.findAccountByUsername(request.getUsername()).orElseThrow(() ->
        new BabRequestException("Invalid username or password", ErrorCode.ACCOUNT_ERROR_BADREQUEST));
    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new BabRequestException("Invalid username or password", ErrorCode.ACCOUNT_ERROR_BADREQUEST);
    }
    if (!user.getStatus().equals(AppConstant.ACCOUNT_STATUS_ACTIVE)){
      throw new BabRequestException("Account cannot active", ErrorCode.ACCOUNT_ERROR_UNKNOWN);
    }
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

    UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
    String token = jwtUtil.generateToken(user.getUsername(), user.getId());
    AuthDto authDto = new AuthDto(true, token);
    apiMessageDto.setData(authDto);
    apiMessageDto.setMessage("Login successful");
    return apiMessageDto;
  }

  @PostMapping("/forget-password")
  public ApiMessageDto<String> sendEmail(@RequestBody CreateForgetPasswordForm request){
    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
    Account account = accountRepository.findByEmail(request.getEmail()).orElseThrow(() -> new NotFoundException("Account not found",
            ErrorCode.ACCOUNT_ERROR_NOTFOUND));
    account.setStatus(AppConstant.ACCOUNT_STATUS_PENDING);
    accountRepository.save(account);
    User user = userRepository.findById(account.getId()).orElseThrow(() -> new NotFoundException("User not found",
        ErrorCode.ACCOUNT_ERROR_NOTFOUND));
    user.setOtp(generateOTP());
    user.setStatus(AppConstant.ACCOUNT_STATUS_PENDING);
    userRepository.save(user);
    sendOTPResetPassword(account.getEmail(), user.getOtp(), account.getUsername());
    apiMessageDto.setMessage("Send email successfully");
    return apiMessageDto;
  }

  @PostMapping("/reset-password")
  public ApiMessageDto<String> resetPassword(@RequestBody @Valid CreateResetPasswordForm request){
    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
    Account account = accountRepository.findByEmail(request.getEmail()).orElseThrow(() -> new NotFoundException("Account not found",
        ErrorCode.ACCOUNT_ERROR_NOTFOUND));
    account.setPassword(passwordEncoder.encode(request.getPassword()));
    apiMessageDto.setMessage("Reset password successfully");
    accountRepository.save(account);
    return apiMessageDto;
  }

  private String generateOTP() {
    Random random = new Random();
    int otpNumber = 100000 + random.nextInt(900000);
    return String.valueOf(otpNumber);
  }

  private void sendOTPEmail(String email, String otpCode, String username) {
    String subject = "Xác thực tài khoản - Mã OTP của bạn";

    String emailContent = "Kính gửi " + username + ",\n\n"
        + "Cảm ơn bạn đã đăng ký tài khoản tại ứng dụng của chúng tôi. "
        + "Để hoàn tất quá trình đăng ký, vui lòng sử dụng mã OTP sau đây:\n\n"
        + "Mã OTP: " + otpCode + "\n\n"
        + "Vui lòng không chia sẻ mã này với bất kỳ ai để đảm bảo an toàn cho tài khoản của bạn.\n\n"
        + "Nếu bạn không thực hiện yêu cầu này, vui lòng bỏ qua email này.\n\n"
        + "Trân trọng,\n"
        + "Đội ngũ phát triển ứng dụng";

    emailService.sendEmail(email, subject, emailContent);
  }

  private void sendOTPResetPassword(String email, String otpCode, String username) {
    String subject = "Yêu cầu đặt lại mật khẩu - Mã OTP của bạn";

    String emailContent = "Kính gửi " + username + ",\n\n"
        + "Chúng tôi đã nhận được yêu cầu đặt lại mật khẩu cho tài khoản của bạn. "
        + "Vui lòng sử dụng mã OTP sau đây để xác thực và tiến hành đặt lại mật khẩu:\n\n"
        + "Mã OTP: " + otpCode + "\n\n"
        + "Vui lòng không chia sẻ mã này với bất kỳ ai để đảm bảo an toàn cho tài khoản của bạn.\n\n"
        + "Nếu bạn không thực hiện yêu cầu này, vui lòng bỏ qua email này hoặc liên hệ với chúng tôi để được hỗ trợ.\n\n"
        + "Trân trọng,\n"
        + "Đội ngũ phát triển ứng dụng";

    emailService.sendEmail(email, subject, emailContent);
  }
}
