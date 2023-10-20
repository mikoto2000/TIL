package dev.mikoto2000.springbootstudy.validation.customvalidator.validation.validator;

import dev.mikoto2000.springbootstudy.validation.customvalidator.constant.BaseEnum;
import dev.mikoto2000.springbootstudy.validation.customvalidator.validation.constraints.ValidCode;

import java.util.Arrays;
import java.util.stream.Collectors;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

/**
 * IncludeEnumValidator。
 */
public class IncludeEnumValidator implements ConstraintValidator<ValidCode, Long> {

  private Class<? extends BaseEnum> enumClass;

  @Override
  public void initialize(ValidCode validCode) {
    this.enumClass = validCode.value();
  }

  @Override
  public boolean isValid(Long value, ConstraintValidatorContext context) {

    // Enum 内の値リストを取得
    BaseEnum[] enumConstants = enumClass.getEnumConstants();

    // フィールドに設定された値から enum を作ってみて、
    // 例外が発生するかを確認。
    // 例外が発生するならバリデーションエラー。
    try {
      // 実際に enum を作ってみる。
      BaseEnum.of(enumConstants, value);

      // バリデーション成功なので true を返却。
      return true;
    } catch (Exception exception) {

      // エラーメッセージ表示用に、 enum 生成時に使える値リストを取得
      String allowedCodes = Arrays.stream(enumConstants)
          .map(e -> e.getCode().toString())
          .collect(Collectors.joining(", "));

      // エラーメッセージテンプレート用のプレースホルダ `allowedCodes` を追加。
      context.disableDefaultConstraintViolation();
      HibernateConstraintValidatorContext hibernateContext = context.unwrap(HibernateConstraintValidatorContext.class);
      hibernateContext.disableDefaultConstraintViolation();
      hibernateContext.addMessageParameter("allowedCodes", allowedCodes)
          .buildConstraintViolationWithTemplate(hibernateContext.getDefaultConstraintMessageTemplate())
          .addConstraintViolation();

      // バリデーション失敗なので false を返却。
      return false;
    }
  }
}
