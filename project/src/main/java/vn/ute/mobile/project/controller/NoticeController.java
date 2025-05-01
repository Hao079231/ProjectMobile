package vn.ute.mobile.project.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ute.mobile.project.constant.AppConstant;
import vn.ute.mobile.project.dto.ApiMessageDto;
import vn.ute.mobile.project.dto.ErrorCode;
import vn.ute.mobile.project.dto.ResponseListDto;
import vn.ute.mobile.project.dto.notice.NoticeDto;
import vn.ute.mobile.project.exception.NotFoundException;
import vn.ute.mobile.project.form.notice.CreateNoticeForm;
import vn.ute.mobile.project.form.notice.UpdateNoticeForm;
import vn.ute.mobile.project.mapper.NoticeMapper;
import vn.ute.mobile.project.model.Notice;
import vn.ute.mobile.project.model.User;
import vn.ute.mobile.project.model.criteria.NoticeCriteria;
import vn.ute.mobile.project.repository.NoticeRepository;
import vn.ute.mobile.project.repository.UserRepository;

@RestController
@RequestMapping("/v1/api/notice")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NoticeController extends AbasicController{
  @Autowired
  private NoticeRepository noticeRepository;
  @Autowired
  private NoticeMapper noticeMapper;
  @Autowired
  private UserRepository userRepository;

  @PostMapping("/create")
  public ApiMessageDto<String> create(@RequestBody @Valid CreateNoticeForm request){
    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
    User user = userRepository.findById(getCurrentUser()).orElseThrow(() -> new NotFoundException("User not found",
        ErrorCode.ACCOUNT_ERROR_NOTFOUND));
    Notice notice = noticeMapper.fromCreateFormToNotice(request);
    notice.setUser(user);
    notice.setStatus(AppConstant.ACCOUNT_STATUS_ACTIVE);
    noticeRepository.save(notice);
    apiMessageDto.setMessage("Create notice successfully");
    return apiMessageDto;
  }

  @GetMapping("/list")
  public ApiMessageDto<ResponseListDto<List<NoticeDto>>> getList(NoticeCriteria request, Pageable pageable){
    ApiMessageDto<ResponseListDto<List<NoticeDto>>> apiMessageDto = new ApiMessageDto<>();
    User user = userRepository.findById(getCurrentUser()).orElseThrow(()-> new NotFoundException("User not found",
        ErrorCode.ACCOUNT_ERROR_NOTFOUND));
    request.setUserId(getCurrentUser());
    Page<Notice> notices = noticeRepository.findAll(request.getSpecification(), pageable);
    List<NoticeDto> noticeDtos = noticeMapper.fromNoticeToDtoList(notices.getContent());
    ResponseListDto<List<NoticeDto>> response = new ResponseListDto<>(noticeDtos, notices.getTotalElements(), notices.getTotalPages());
    apiMessageDto.setData(response);
    apiMessageDto.setMessage("Get list notice successfully");
    return apiMessageDto;
  }

  @GetMapping("/get/{id}")
  public ApiMessageDto<NoticeDto> get(@PathVariable String id){
    ApiMessageDto<NoticeDto> apiMessageDto = new ApiMessageDto<>();
    User user = userRepository.findById(getCurrentUser()).orElseThrow(()-> new NotFoundException("User not found",
        ErrorCode.ACCOUNT_ERROR_NOTFOUND));
    Notice notice = noticeRepository.findByIdAndUser(id, user).orElseThrow(()-> new NotFoundException("Notice not found",
        ErrorCode.NOTICE_ERROR_NOTFOUND));
    apiMessageDto.setData(noticeMapper.fromNoticeToDto(notice));
    apiMessageDto.setMessage("Get notice successfully");
    return apiMessageDto;
  }

  @PutMapping("/update/{id}")
  public ApiMessageDto<String> update(@RequestBody @Valid UpdateNoticeForm request, @PathVariable String id){
    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
    User user = userRepository.findById(getCurrentUser()).orElseThrow(()-> new NotFoundException("User not found",
        ErrorCode.ACCOUNT_ERROR_NOTFOUND));
    Notice notice = noticeRepository.findByIdAndUser(id, user).orElseThrow(()-> new NotFoundException("Notice not found",
        ErrorCode.NOTICE_ERROR_NOTFOUND));
    noticeMapper.updateNoticeForm(request, notice);
    noticeRepository.save(notice);
    apiMessageDto.setMessage("Update notice successfully");
    return apiMessageDto;
  }

  @DeleteMapping("/delete/{id}")
  public ApiMessageDto<String> delete(@PathVariable String id){
    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
    User user = userRepository.findById(getCurrentUser()).orElseThrow(() -> new NotFoundException("User not found",
        ErrorCode.ACCOUNT_ERROR_NOTFOUND));
    Notice notice = noticeRepository.findByIdAndUser(id, user).orElseThrow(()-> new NotFoundException("Notice not found",
        ErrorCode.NOTICE_ERROR_NOTFOUND));
    noticeRepository.delete(notice);
    apiMessageDto.setMessage("Delete notice successfully");
    return apiMessageDto;
  }
}
