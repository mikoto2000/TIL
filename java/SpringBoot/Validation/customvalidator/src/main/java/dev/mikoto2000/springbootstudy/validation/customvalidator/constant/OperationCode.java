package dev.mikoto2000.springbootstudy.validation.customvalidator.constant;

import java.util.Arrays;

/**
 * OperationCode
 */
public enum OperationCode implements BaseEnum {
  CREATE(1L),
  MODIFY(2L),
  DELETE(3L);

  private final Long code;

  OperationCode(Long code) {
    this.code = code;
  }

  @Override
  public Long getCode() {
    return code;
  }

  public static OperationCode of(Long code) {
    return BaseEnum.of(OperationCode.values(), code);
  }

  @Override
  public BaseEnum[] value() {
    return this.value();
  }

  @Override
  public long[] codes() {
    return Arrays.asList(OperationCode.values()).stream()
        .mapToLong((e) -> e.getCode())
        .toArray();
  }
}
