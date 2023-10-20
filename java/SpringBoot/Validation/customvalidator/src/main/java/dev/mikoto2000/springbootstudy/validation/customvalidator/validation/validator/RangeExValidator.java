package dev.mikoto2000.springbootstudy.validation.customvalidator.validation.validator;

import dev.mikoto2000.springbootstudy.validation.customvalidator.validation.constraints.RangeEx;

import java.util.Arrays;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * RangeExValidator
 */
public class RangeExValidator implements ConstraintValidator<RangeEx, Long> {

  private long min;
  private long max;
  private long[] others;

  @Override
  public void initialize(RangeEx rangeEx) {
    this.min = rangeEx.min();
    this.max = rangeEx.max();
    this.others = rangeEx.others();
  }

  @Override
  public boolean isValid(Long value, ConstraintValidatorContext context) {

    // others に存在しているか確認
    if (Arrays.stream(others).anyMatch(e -> e == value)) {
      // others に存在しているものは OK
      return true;
    } else {
      // others に存在していない場合、 min, max の範囲チェックを行う
      if (min <= value && value <= max) {
        // 範囲内であれば OK
        return true;
      } else {
        // 範囲外であれば NG
        return false;
      }
    }
  }
}
