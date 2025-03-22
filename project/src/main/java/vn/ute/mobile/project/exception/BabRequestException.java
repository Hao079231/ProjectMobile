package vn.ute.mobile.project.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BabRequestException extends RuntimeException {
  private String code;
  public BabRequestException(String message) {
    super(message);
  }
  public BabRequestException(String message, String code) {
    super(message);
    this.code = code;
  }
}
