package vn.ute.mobile.project.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import vn.ute.mobile.project.constant.AppConstant;
import vn.ute.mobile.project.validation.WordType;

public class WordTypeValidation implements ConstraintValidator<WordType, String> {
  private boolean allowNull;

  @Override
  public void initialize(WordType constraintAnnotation) {
    this.allowNull = constraintAnnotation.allowNull();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
    if (value == null) {
      return allowNull;
    }

    return AppConstant.VALID_WORD_TYPES.contains(value);
  }
}
