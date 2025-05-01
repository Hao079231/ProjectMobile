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
import vn.ute.mobile.project.dto.grammar.GrammarDto;
import vn.ute.mobile.project.exception.BabRequestException;
import vn.ute.mobile.project.exception.NotFoundException;
import vn.ute.mobile.project.form.grammar.CreateGrammarForm;
import vn.ute.mobile.project.form.grammar.UpdateGrammarForm;
import vn.ute.mobile.project.mapper.GrammarMapper;
import vn.ute.mobile.project.model.Grammar;
import vn.ute.mobile.project.model.User;
import vn.ute.mobile.project.model.criteria.GrammarCriteria;
import vn.ute.mobile.project.repository.GrammarRepository;
import vn.ute.mobile.project.repository.UserRepository;

@RestController
@RequestMapping("/v1/api/grammar")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GrammarController extends AbasicController{
  @Autowired
  private GrammarRepository grammarRepository;
  @Autowired
  private GrammarMapper grammarMapper;
  @Autowired
  private UserRepository userRepository;

  @PostMapping("/create")
  public ApiMessageDto<String> create(@RequestBody CreateGrammarForm request){
    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
    User user = userRepository.findById(getCurrentUser()).orElseThrow(() -> new NotFoundException("User not found",
        ErrorCode.ACCOUNT_ERROR_NOTFOUND));
    if (grammarRepository.existsByName(request.getName())){
      throw new BabRequestException("Grammar name already exist", ErrorCode.GRAMMAR_ERROR_EXIST);
    }
    Grammar grammar = grammarMapper.fromCreateToGrammar(request);
    grammar.setUser(user);
    grammar.setStatus(AppConstant.APP_STATUS_ACTIVE);
    grammarRepository.save(grammar);
    apiMessageDto.setMessage("Create grammar successfully");
    return apiMessageDto;
  }

  @GetMapping("/list")
  public ApiMessageDto<ResponseListDto<List<GrammarDto>>> getList(GrammarCriteria request, Pageable pageable){
    ApiMessageDto<ResponseListDto<List<GrammarDto>>> apiMessageDto = new ApiMessageDto<>();
    Page<Grammar> grammars = grammarRepository.findAll(request.getSpecification(), pageable);
    List<GrammarDto> grammarDtos = grammarMapper.fromGrammarToDtoList(grammars.getContent());
    ResponseListDto<List<GrammarDto>> responses = new ResponseListDto<>(grammarDtos, grammars.getTotalElements(), grammars.getTotalPages());
    apiMessageDto.setData(responses);
    apiMessageDto.setMessage("Get list grammar successfully");
    return apiMessageDto;
  }

  @GetMapping("/get/{id}")
  public ApiMessageDto<GrammarDto> get(@PathVariable String id){
    ApiMessageDto<GrammarDto> apiMessageDto = new ApiMessageDto<>();
    Grammar grammar = grammarRepository.findById(id).orElseThrow(() -> new NotFoundException("Grammar not found",
        ErrorCode.GRAMMAR_ERROR_NOTFOUND));
    apiMessageDto.setData(grammarMapper.fromGrammarToDto(grammar));
    apiMessageDto.setMessage("Get grammar successfully");
    return apiMessageDto;
  }

  @PutMapping("/update/{id}")
  public ApiMessageDto<String> update(@RequestBody UpdateGrammarForm request, @PathVariable String id){
    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
    User user = userRepository.findById(getCurrentUser()).orElseThrow(() -> new NotFoundException("User not found",
        ErrorCode.ACCOUNT_ERROR_NOTFOUND));
    Grammar grammar = grammarRepository.findById(id).orElseThrow(() -> new NotFoundException("Grammar not found",
        ErrorCode.GRAMMAR_ERROR_NOTFOUND));
    if (!grammar.getName().equals(request.getName())){
      if (grammarRepository.existsByName(request.getName())){
        throw new BabRequestException("Grammar already exist", ErrorCode.GRAMMAR_ERROR_EXIST);
      }
    }
    grammarMapper.updateGrammar(request, grammar);
    grammar.setUser(user);
    grammarRepository.save(grammar);
    apiMessageDto.setMessage("Update grammar successfully");
    return apiMessageDto;
  }

  @DeleteMapping("/delete/{id}")
  public ApiMessageDto<String> delete(@PathVariable String id){
    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
    User user = userRepository.findById(getCurrentUser()).orElseThrow(() -> new NotFoundException("User not found",
        ErrorCode.ACCOUNT_ERROR_NOTFOUND));
    Grammar grammar = grammarRepository.findById(id).orElseThrow(() -> new NotFoundException("Grammar not found",
        ErrorCode.GRAMMAR_ERROR_NOTFOUND));
    grammarRepository.delete(grammar);
    apiMessageDto.setMessage("Delete grammar successfully");
    return apiMessageDto;
  }
}
