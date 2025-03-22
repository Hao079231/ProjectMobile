package vn.ute.mobile.project.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import vn.ute.mobile.project.constant.AppConstant;
import vn.ute.mobile.project.validation.Gender;

public class GenderValidation implements ConstraintValidator<Gender, Integer> {
  private boolean allowNull;
  @Override
  public void initialize(Gender constraintAnnotation) {
    allowNull = constraintAnnotation.allowNull();
  }

  @Override
  public boolean isValid(Integer gender, ConstraintValidatorContext constraintValidatorContext) {
    if (gender == null){
      return allowNull;
    }
    return AppConstant.VALID_GENDER.contains(gender);
  }
}
