package dev.mikoto2000.springbootstudy.validation.customvalidator.validation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.OverridesAttribute;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.hibernate.validator.constraints.Range;

import dev.mikoto2000.springbootstudy.validation.customvalidator.validation.validator.RangeExValidator;

/**
 * RangeEx。
 */
@Target({
    ElementType.FIELD,
    ElementType.METHOD,
    ElementType.PARAMETER,
    ElementType.ANNOTATION_TYPE,
    ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RangeExValidator.class)
@Documented
@Repeatable(RangeEx.List.class)
@Range
@ReportAsSingleViolation
public @interface RangeEx {

  /**
   * 最小値。
   */
  @OverridesAttribute(constraint = Min.class, name = "value")
  long min() default 0;

  /**
   * 最大値。
   */
  @OverridesAttribute(constraint = Max.class, name = "value")
  long max() default Long.MAX_VALUE;

  String message() default "{dev.mikoto2000.springbootstudy.validation.customvalidator.validation.constraints.RangeEx.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  /**
   * 最大値と最小値範囲以外で許可する値のリスト。
   */
  long[] others();

  @Target({
      ElementType.FIELD,
      ElementType.METHOD,
      ElementType.PARAMETER,
      ElementType.ANNOTATION_TYPE })
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  @interface List {
    RangeEx[] value();
  }
}
