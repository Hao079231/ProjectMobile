package vn.ute.mobile.project.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import vn.ute.mobile.project.validation.impl.PasswordValidation;

@Documented
@Constraint(validatedBy = PasswordValidation.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
  boolean allowNull() default false;
  int minLength() default 8;
  String message() default "Password must be at least 8 characters, contain at least one uppercase letter, one lowercase letter, one digit, and one special character.";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
