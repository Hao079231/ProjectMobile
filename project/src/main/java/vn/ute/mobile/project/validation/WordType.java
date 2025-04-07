package vn.ute.mobile.project.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import vn.ute.mobile.project.validation.impl.WordTypeValidation;

@Documented
@Constraint(validatedBy = WordTypeValidation.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface WordType {
  boolean allowNull() default false;
  String message() default "Word type must belong to: Noun, Verb, Adjective, Adverb, Pronoun, Determiner, Preposition, Conjunction, Interjection";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
