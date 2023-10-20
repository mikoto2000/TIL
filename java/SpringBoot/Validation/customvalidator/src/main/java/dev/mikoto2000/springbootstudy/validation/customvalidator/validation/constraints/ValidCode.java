package dev.mikoto2000.springbootstudy.validation.customvalidator.validation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import dev.mikoto2000.springbootstudy.validation.customvalidator.constant.BaseEnum;
import dev.mikoto2000.springbootstudy.validation.customvalidator.validation.validator.IncludeEnumValidator;

/**
 * ValidCode
 */
@Target({
    ElementType.FIELD,
    ElementType.METHOD,
    ElementType.PARAMETER,
    ElementType.ANNOTATION_TYPE,
    ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IncludeEnumValidator.class)
@Documented
@Repeatable(ValidCode.List.class)
public @interface ValidCode {
  String message() default "{dev.mikoto2000.springbootstudy.validation.customvalidator.validation.constraints.ValidCode.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  /**
   * 許可する enum。
   */
  Class<? extends BaseEnum> value();

  @Target({
      ElementType.FIELD,
      ElementType.METHOD,
      ElementType.PARAMETER,
      ElementType.ANNOTATION_TYPE })
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  @interface List {
    ValidCode[] value();
  }
}
