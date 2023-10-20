package dev.mikoto2000.springbootstudy.validation.customvalidator.constant;

/**
 * BaseEnum
 */
public interface BaseEnum {
  Long getCode();

  BaseEnum[] value();

  long[] codes();

  public static <T extends BaseEnum> T of(T[] values, Long code) {
    for (T v : values) {
      if (v.getCode() == code)
        return v;
    }

    throw new IllegalArgumentException(
        String.format(
            "不明なコード: { class: %s, code: %s }",
            values.getClass().getComponentType().getSimpleName(),
            code));
  }
}
