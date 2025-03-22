package vn.ute.mobile.project.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import vn.ute.mobile.project.validation.impl.GenderValidation;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GenderValidation.class)
@Documented
public @interface Gender {
  boolean allowNull() default false;
  String message() default "User gender invalid. Valid values is 1 (male), 2 (female) or 3 (other).";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
