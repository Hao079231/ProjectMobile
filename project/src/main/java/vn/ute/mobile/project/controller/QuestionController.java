package vn.ute.mobile.project.controller;

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
import vn.ute.mobile.project.dto.dto.QuestionDto;
import vn.ute.mobile.project.exception.BabRequestException;
import vn.ute.mobile.project.exception.NotFoundException;
import vn.ute.mobile.project.form.question.CreateQuestionForm;
import vn.ute.mobile.project.form.question.UpdateQuestionForm;
import vn.ute.mobile.project.mapper.QuestionMapper;
import vn.ute.mobile.project.model.Question;
import vn.ute.mobile.project.model.User;
import vn.ute.mobile.project.model.criteria.QuestionCriteria;
import vn.ute.mobile.project.repository.QuestionRepository;
import vn.ute.mobile.project.repository.UserRepository;

@RestController
@RequestMapping("/v1/api/question")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class QuestionController extends AbasicController{
  @Autowired
  private QuestionRepository questionRepository;
  @Autowired
  private QuestionMapper questionMapper;
  @Autowired
  private UserRepository userRepository;

  @PostMapping("/create")
  public ApiMessageDto<String> create(@RequestBody CreateQuestionForm request){
    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
    if (questionRepository.existsByQuestionAndAllChoice(request.getQuestion(), request.getAllChoice())){
      throw new BabRequestException("Question and choices already exist", ErrorCode.QUESTION_ERROR_EXIST);
    }
    Question question = questionMapper.fromCreateToQuestion(request);
    question.setStatus(AppConstant.APP_STATUS_ACTIVE);
    questionRepository.save(question);
    apiMessageDto.setMessage("Create question successfully");
    return apiMessageDto;
  }

  @GetMapping("/list")
  public ApiMessageDto<ResponseListDto<List<QuestionDto>>> getList(QuestionCriteria request, Pageable pageable){
    ApiMessageDto<ResponseListDto<List<QuestionDto>>> apiMessageDto = new ApiMessageDto<>();
    User user = userRepository.findById(getCurrentUser()).orElseThrow(() -> new NotFoundException("User not found",
        ErrorCode.ACCOUNT_ERROR_NOTFOUND));
    Page<Question> questions = questionRepository.findAll(request.getSpecification(), pageable);
    List<QuestionDto> questionDtos = questionMapper.fromQuestionToDtoList(questions.getContent());
    ResponseListDto<List<QuestionDto>> responses = new ResponseListDto<>(questionDtos, questions.getTotalElements(), questions.getTotalPages());
    apiMessageDto.setData(responses);
    apiMessageDto.setMessage("Get list question successfully");
    return apiMessageDto;
  }

  @GetMapping("/get/{id}")
  public ApiMessageDto<QuestionDto> get(@PathVariable String id){
    ApiMessageDto<QuestionDto> apiMessageDto = new ApiMessageDto<>();
    User user = userRepository.findById(getCurrentUser()).orElseThrow(() -> new NotFoundException("User not found",
        ErrorCode.ACCOUNT_ERROR_NOTFOUND));
    Question question = questionRepository.findById(id).orElseThrow(() -> new NotFoundException("Question not found",
        ErrorCode.QUESTION_ERROR_NOTFOUND));
    apiMessageDto.setData(questionMapper.fromQuestionToDto(question));
    apiMessageDto.setMessage("Get question successfully");
    return apiMessageDto;
  }

  @PutMapping("/update")
  public ApiMessageDto<String> update(@RequestBody UpdateQuestionForm request){
    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
    Question question = questionRepository.findById(request.getId()).orElseThrow(() -> new NotFoundException("Question not found",
        ErrorCode.QUESTION_ERROR_NOTFOUND));
    if (questionRepository.existsByQuestionAndAllChoiceAndIdNot(request.getQuestion(), request.getAllChoice(), request.getId())){
      throw new BabRequestException("Question and choices already exist", ErrorCode.QUESTION_ERROR_EXIST);
    }
    questionMapper.updateQuestion(request, question);
    questionRepository.save(question);
    apiMessageDto.setMessage("Update question successfully");
    return apiMessageDto;
  }

  @DeleteMapping("/delete/{id}")
  public ApiMessageDto<String> delete(@PathVariable String id){
    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
    Question question = questionRepository.findById(id).orElseThrow(() -> new NotFoundException("Question not found", ErrorCode.QUESTION_ERROR_NOTFOUND));
    questionRepository.delete(question);
    apiMessageDto.setMessage("Delete question successfully");
    return apiMessageDto;
  }
}
