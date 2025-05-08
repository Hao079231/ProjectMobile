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
import vn.ute.mobile.project.dto.vocabulary.VocabularyDto;
import vn.ute.mobile.project.exception.BabRequestException;
import vn.ute.mobile.project.exception.NotFoundException;
import vn.ute.mobile.project.form.vocabulary.CreateVocabularyForm;
import vn.ute.mobile.project.form.vocabulary.UpdateVocabularyForm;
import vn.ute.mobile.project.mapper.VocabularyMapper;
import vn.ute.mobile.project.model.TopicModel;
import vn.ute.mobile.project.model.User;
import vn.ute.mobile.project.model.Vocabulary;
import vn.ute.mobile.project.model.criteria.VocabularyCriteria;
import vn.ute.mobile.project.repository.TopicModelRepository;
import vn.ute.mobile.project.repository.UserRepository;
import vn.ute.mobile.project.repository.VocabularyRepository;

@RestController
@RequestMapping("/v1/api/vocabulary")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VocabularyController extends AbasicController{
  @Autowired
  private VocabularyRepository vocabularyRepository;
  @Autowired
  private VocabularyMapper vocabularyMapper;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private TopicModelRepository topicModelRepository;

  @PostMapping("/create")
  public ApiMessageDto<String> create(@RequestBody @Valid CreateVocabularyForm request){
    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
    if (vocabularyRepository.existsByWord(request.getWord())){
      throw new BabRequestException("Vocabulary already exist", ErrorCode.VOCABULARY_ERROR_EXIST);
    }
    TopicModel topicModel = topicModelRepository.findById(request.getTopicId()).orElseThrow(() -> new NotFoundException("Topic not found",
        ErrorCode.TOPIC_ERROR_NOTFOUND));
    Vocabulary vocabulary = vocabularyMapper.fromCreateToVocabulary(request);
    vocabulary.setTopicModel(topicModel);
    vocabulary.setStatus(AppConstant.APP_STATUS_ACTIVE);
    vocabularyRepository.save(vocabulary);
    apiMessageDto.setMessage("Create vocabulary successfully");
    return apiMessageDto;
  }

  @GetMapping("/list/{topicId}")
  public ApiMessageDto<ResponseListDto<List<VocabularyDto>>> getList(VocabularyCriteria request, @PathVariable String topicId,Pageable pageable){
    ApiMessageDto<ResponseListDto<List<VocabularyDto>>> apiMessageDto = new ApiMessageDto<>();
    User user = userRepository.findById(getCurrentUser()).orElseThrow(()-> new NotFoundException("User not found", ErrorCode.ACCOUNT_ERROR_NOTFOUND));
    TopicModel topicModel = topicModelRepository.findById(topicId).orElseThrow(() -> new NotFoundException("Topic not found", ErrorCode.TOPIC_ERROR_NOTFOUND));
    request.setTopicId(topicId);
    Page<Vocabulary> vocabularies = vocabularyRepository.findAll(request.getSpecification(), pageable);
    List<VocabularyDto> vocabularyDtos = vocabularyMapper.fromVocabularyToDtoList(vocabularies.getContent());
    ResponseListDto<List<VocabularyDto>> response = new ResponseListDto<>(vocabularyDtos, vocabularies.getTotalElements(), vocabularies.getTotalPages());
    apiMessageDto.setData(response);
    apiMessageDto.setMessage("Get list vocabulary successfully");
    return apiMessageDto;
  }

//  @GetMapping("/get/{id}")
//  public ApiMessageDto<VocabularyDto> get(@PathVariable String id){
//    ApiMessageDto<VocabularyDto> apiMessageDto = new ApiMessageDto<>();
//    Vocabulary vocabulary = vocabularyRepository.findById(id).orElseThrow(()-> new NotFoundException("Vocabulary not found",
//        ErrorCode.VOCABULARY_ERROR_NOTFOUND));
//    apiMessageDto.setData(vocabularyMapper.fromVocabularyToDto(vocabulary));
//    apiMessageDto.setMessage("Get vocabulary successfully");
//    return apiMessageDto;
//  }

  @PutMapping("/update")
  public ApiMessageDto<String> update(@RequestBody @Valid UpdateVocabularyForm request){
    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
    Vocabulary vocabulary = vocabularyRepository.findById(request.getId()).orElseThrow(()-> new NotFoundException("Vocabulary not found",
        ErrorCode.VOCABULARY_ERROR_NOTFOUND));
    TopicModel topicModel = topicModelRepository.findById(request.getTopicId()).orElseThrow(() -> new NotFoundException("Topic not found",
        ErrorCode.TOPIC_ERROR_NOTFOUND));
    if (!vocabulary.getWord().equals(request.getWord())){
      if (vocabularyRepository.existsByWord(request.getWord())){
        throw new BabRequestException("Vocabulary already exist", ErrorCode.VOCABULARY_ERROR_EXIST);
      }
    }
    vocabularyMapper.updateVocabulary(request, vocabulary);
    vocabulary.setTopicModel(topicModel);
    vocabularyRepository.save(vocabulary);
    apiMessageDto.setMessage("Update vocabulary successfully");
    return apiMessageDto;
  }

  @DeleteMapping("/delete/{id}")
  public ApiMessageDto<String> delete(@PathVariable String id){
    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
    Vocabulary vocabulary = vocabularyRepository.findById(id).orElseThrow(()-> new NotFoundException("Vocabulary not found",
        ErrorCode.VOCABULARY_ERROR_NOTFOUND));
    vocabularyRepository.delete(vocabulary);
    apiMessageDto.setMessage("Delete vocabulary successfully");
    return apiMessageDto;
  }
}
