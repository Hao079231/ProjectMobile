package vn.ute.mobile.project.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ute.mobile.project.constant.AppConstant;
import vn.ute.mobile.project.dto.ApiMessageDto;
import vn.ute.mobile.project.dto.ErrorCode;
import vn.ute.mobile.project.dto.user_point.UserPointDto;
import vn.ute.mobile.project.exception.NotFoundException;
import vn.ute.mobile.project.form.user_point.CreateUserPointForm;
import vn.ute.mobile.project.mapper.UserPointMapper;
import vn.ute.mobile.project.model.User;
import vn.ute.mobile.project.model.UserPoint;
import vn.ute.mobile.project.repository.UserPointRepository;
import vn.ute.mobile.project.repository.UserRepository;

@RestController
@RequestMapping("/v1/api/user-point")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserPointController extends AbasicController{
  @Autowired
  private UserPointRepository userPointRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserPointMapper userPointMapper;

  @Transactional
  @PostMapping("/create")
  public ApiMessageDto<String> create(@RequestBody @Valid CreateUserPointForm request){
    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
    User user = userRepository.findById(getCurrentUser()).orElseThrow(() -> new NotFoundException("User not found",
        ErrorCode.ACCOUNT_ERROR_NOTFOUND));
    UserPoint userPoint = userPointRepository.findByUserId(user.getId()).orElse(null);
    if (userPoint == null){
      userPoint = userPointMapper.fromCreateToUserPoint(request);
      userPoint.setUser(user);
      userPoint.setTime(LocalDateTime.now());
      userPoint.setStatus(AppConstant.APP_STATUS_ACTIVE);
      userPointRepository.save(userPoint);
    } else {
      if (userPoint.getPoint() < request.getPoint()){
        userPointRepository.updatePointById(userPoint.getId(), request.getPoint());
      }
    }
    apiMessageDto.setMessage("Create user point successfully");
    return apiMessageDto;
  }

  @GetMapping("/list")
  public ApiMessageDto<List<UserPointDto>> getList(){
    ApiMessageDto<List<UserPointDto>> apiMessageDto = new ApiMessageDto<>();
    User user = userRepository.findById(getCurrentUser()).orElseThrow(() -> new NotFoundException("User not found",
        ErrorCode.ACCOUNT_ERROR_NOTFOUND));
    List<UserPoint> userPoints = userPointRepository.findAllByOrderByPointDesc();
    List<UserPointDto> userPointDtos = userPointMapper.fromUserPointToDtoList(userPoints);
    apiMessageDto.setData(userPointDtos);
    apiMessageDto.setMessage("Get list user point successfully");
    return apiMessageDto;
  }
}
