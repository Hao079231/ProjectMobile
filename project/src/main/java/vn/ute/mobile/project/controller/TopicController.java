package vn.ute.mobile.project.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
import vn.ute.mobile.project.dto.topic.TopicDto;
import vn.ute.mobile.project.exception.BabRequestException;
import vn.ute.mobile.project.exception.NotFoundException;
import vn.ute.mobile.project.form.topic.CreateTopicForm;
import vn.ute.mobile.project.form.topic.UpdateTopicForm;
import vn.ute.mobile.project.mapper.TopicMapper;
import vn.ute.mobile.project.model.TopicModel;
import vn.ute.mobile.project.model.User;
import vn.ute.mobile.project.repository.TopicModelRepository;
import vn.ute.mobile.project.repository.UserRepository;
import vn.ute.mobile.project.repository.VocabularyRepository;

@RestController
@RequestMapping("/v1/api/topic")
public class TopicController extends AbasicController{
  @Autowired
  private TopicModelRepository topicModelRepository;
  @Autowired
  private TopicMapper topicMapper;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private VocabularyRepository vocabularyRepository;

  @PostMapping("/create")
  public ApiMessageDto<String> create(@RequestBody @Valid CreateTopicForm request){
    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
    if (topicModelRepository.existsByTopic(request.getTopic())){
      throw new BabRequestException("Topic already exist", ErrorCode.TOPIC_ERROR_EXIST);
    }
    TopicModel topicModel = topicMapper.fromCreateTopicToEntity(request);
    topicModel.setStatus(AppConstant.APP_STATUS_ACTIVE);
    topicModelRepository.save(topicModel);
    apiMessageDto.setMessage("Create topic successfully");
    return apiMessageDto;
  }

  @GetMapping("/list")
  public ApiMessageDto<List<TopicDto>> getList(){
    ApiMessageDto<List<TopicDto>> apiMessageDto = new ApiMessageDto<>();
    User user = userRepository.findById(getCurrentUser()).orElseThrow(() -> new NotFoundException("User not found",
        ErrorCode.ACCOUNT_ERROR_NOTFOUND));
    List<TopicModel> topicModels = topicModelRepository.findAll();
    List<TopicDto> topicDtos = topicMapper.fromTopicModelToDtoList(topicModels);
    apiMessageDto.setData(topicDtos);
    apiMessageDto.setMessage("Get list topic successfully");
    return apiMessageDto;
  }

  @GetMapping("/get/{id}")
  public ApiMessageDto<TopicDto> get(@PathVariable String id){
    ApiMessageDto<TopicDto> apiMessageDto = new ApiMessageDto<>();
    User user = userRepository.findById(getCurrentUser()).orElseThrow(() -> new NotFoundException("User not found",
        ErrorCode.ACCOUNT_ERROR_NOTFOUND));
    TopicModel topicModel = topicModelRepository.findById(id).orElseThrow(() -> new NotFoundException("Topic not found",
        ErrorCode.TOPIC_ERROR_NOTFOUND));
    apiMessageDto.setData(topicMapper.fromTopicModelToDto(topicModel));
    apiMessageDto.setMessage("Get topic successfully");
    return apiMessageDto;
  }

  @PutMapping("/update")
  public ApiMessageDto<String> update(@RequestBody @Valid UpdateTopicForm request){
    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
    TopicModel topicModel = topicModelRepository.findById(request.getId()).orElseThrow(() -> new NotFoundException("Topic not found",
        ErrorCode.TOPIC_ERROR_NOTFOUND));
    if (!topicModel.getId().equals(request.getId())){
      if (!topicModel.getTopic().equals(request.getTopic())){
        throw new BabRequestException("Topic already exist", ErrorCode.TOPIC_ERROR_EXIST);
      }
    }
    topicMapper.updateTopicModel(request, topicModel);
    topicModelRepository.save(topicModel);
    apiMessageDto.setMessage("Update topic successfully");
    return apiMessageDto;
  }

  @Transactional
  @DeleteMapping("/delete/{id}")
  public ApiMessageDto<String> delete(@PathVariable String id){
    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
    TopicModel topicModel = topicModelRepository.findById(id).orElseThrow(() -> new NotFoundException("Topic not found",
        ErrorCode.TOPIC_ERROR_NOTFOUND));
    vocabularyRepository.deleteByTopicId(id);
    topicModelRepository.delete(topicModel);
    apiMessageDto.setMessage("Delete topic successfully");
    return apiMessageDto;
  }
}
