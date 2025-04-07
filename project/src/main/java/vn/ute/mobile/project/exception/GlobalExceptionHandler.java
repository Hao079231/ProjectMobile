package vn.ute.mobile.project.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import vn.ute.mobile.project.dto.ApiMessageDto;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({NotFoundException.class})
  public ResponseEntity<ApiMessageDto<String>> globalExceptionHandler(NotFoundException ex) {
    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
    apiMessageDto.setCode(ex.getCode());
    apiMessageDto.setResult(false);
    apiMessageDto.setMessage(ex.getMessage());
    return new ResponseEntity<>(apiMessageDto, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({BabRequestException.class})
  public ResponseEntity<ApiMessageDto<String>> badRequestExceptionHandler(BabRequestException ex) {
    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
    apiMessageDto.setCode(ex.getCode());
    apiMessageDto.setResult(false);
    apiMessageDto.setMessage(ex.getMessage());
    return new ResponseEntity<>(apiMessageDto, HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {

    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error -> {
      String fieldName = error.getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    ApiMessageDto<Map<String, String>> apiMessageDto = new ApiMessageDto<>();
    apiMessageDto.setCode("VALIDATION_ERROR");
    apiMessageDto.setResult(false);
    apiMessageDto.setMessage("Validation failed");
    apiMessageDto.setData(errors);

    return new ResponseEntity<>(apiMessageDto, HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(
      NoHandlerFoundException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {

    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
    apiMessageDto.setResult(false);
    apiMessageDto.setMessage("Endpoint not found: " + ex.getRequestURL());
    apiMessageDto.setCode("404");

    return new ResponseEntity<>(apiMessageDto, HttpStatus.NOT_FOUND);
  }
}