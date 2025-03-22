package vn.ute.mobile.project.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import vn.ute.mobile.project.validation.Password;

public class PasswordValidation implements ConstraintValidator<Password, String> {

  private Boolean allowNull;
  private int minLength;
  private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$";

  @Override
  public void initialize(Password constraintAnnotation) {
    allowNull = constraintAnnotation.allowNull();
    minLength = constraintAnnotation.minLength();
  }

  @Override
  public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
    if (password.isEmpty()){
      return allowNull;
    }
    return password.length() >= minLength && password.matches(PASSWORD_PATTERN);
  }
}
