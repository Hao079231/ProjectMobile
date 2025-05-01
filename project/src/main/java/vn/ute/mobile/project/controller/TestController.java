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
import vn.ute.mobile.project.dto.ApiMessageDto;
import vn.ute.mobile.project.dto.ErrorCode;
import vn.ute.mobile.project.dto.ResponseListDto;
import vn.ute.mobile.project.dto.test.TestDto;
import vn.ute.mobile.project.exception.BabRequestException;
import vn.ute.mobile.project.exception.NotFoundException;
import vn.ute.mobile.project.form.test.CreateTestForm;
import vn.ute.mobile.project.form.test.UpdateTestForm;
import vn.ute.mobile.project.mapper.TestMapper;
import vn.ute.mobile.project.model.Test;
import vn.ute.mobile.project.model.criteria.TestCriteria;
import vn.ute.mobile.project.repository.TestRepository;

@RestController
@RequestMapping("/v1/api/test")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TestController {
  @Autowired
  private TestRepository testRepository;
  @Autowired
  private TestMapper testMapper;

  @PostMapping("/create")
  public ApiMessageDto<String> create(@RequestBody @Valid CreateTestForm request){
    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
    if (testRepository.existsByQuestion(request.getQuestion())){
      throw new BabRequestException("Question already exist", ErrorCode.TEST_ERROR_EXIST);
    }
    Test test = testMapper.fromCreateToTest(request);
    testRepository.save(test);
    apiMessageDto.setMessage("Create test successfully");
    return apiMessageDto;
  }

  @GetMapping("/list")
  public ApiMessageDto<ResponseListDto<List<TestDto>>> getList(TestCriteria request, Pageable pageable){
    ApiMessageDto<ResponseListDto<List<TestDto>>> apiMessageDto = new ApiMessageDto<>();
    Page<Test> tests = testRepository.findAll(request.getSpecification(), pageable);
    List<TestDto> testDtos = testMapper.fromTestToDtoList(tests.getContent());
    ResponseListDto<List<TestDto>> responses = new ResponseListDto<>(testDtos, tests.getTotalElements(), tests.getTotalPages());
    apiMessageDto.setData(responses);
    apiMessageDto.setMessage("Get list test successfully");
    return apiMessageDto;
  }

  @GetMapping("/get/{id}")
  public ApiMessageDto<TestDto> get(@PathVariable String id){
    ApiMessageDto<TestDto> apiMessageDto = new ApiMessageDto<>();
    Test test = testRepository.findById(id).orElseThrow(() -> new NotFoundException("Test not found",
        ErrorCode.TEST_ERROR_NOTFOUND));
    apiMessageDto.setData(testMapper.fromTestToDto(test));
    apiMessageDto.setMessage("Get test successfully");
    return apiMessageDto;
  }

  @PutMapping("/update/{id}")
  public ApiMessageDto<String> update(@RequestBody @Valid UpdateTestForm request, @PathVariable String id){
    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
    Test test = testRepository.findById(id).orElseThrow(() -> new NotFoundException("Test not found", ErrorCode.TEST_ERROR_NOTFOUND));
    if (!test.getQuestion().equals(request.getQuestion())){
      if (testRepository.existsByQuestion(request.getQuestion())){
        throw new BabRequestException("Question already exist", ErrorCode.TEST_ERROR_EXIST);
      }
    }
    testMapper.updateTest(request, test);
    testRepository.save(test);
    apiMessageDto.setMessage("Update test successfully");
    return apiMessageDto;
  }

  @DeleteMapping("/delete/{id}")
  public ApiMessageDto<String> delete(@PathVariable String id){
    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
    Test test = testRepository.findById(id).orElseThrow(() -> new NotFoundException("Test not found",
        ErrorCode.TEST_ERROR_NOTFOUND));
    testRepository.delete(test);
    apiMessageDto.setMessage("Delete test successfully");
    return apiMessageDto;
  }
}
