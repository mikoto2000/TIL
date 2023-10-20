package dev.mikoto2000.springbootstudy.validation.customvalidator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import dev.mikoto2000.springbootstudy.validation.customvalidator.constant.OperationCode;
import dev.mikoto2000.springbootstudy.validation.customvalidator.validation.constraints.RangeEx;
import dev.mikoto2000.springbootstudy.validation.customvalidator.validation.constraints.ValidCode;

/**
 * User
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Operation {
    /**
     * 動作確認用 Long 変数。
     *
     * enum の `OperationCode` で定義されているコード値のみを許可。
     */
    @ValidCode(value = OperationCode.class)
    private Long operationType;

    /**
     * 動作確認用 Long 変数。
     *
     * 0 から 10, または、 255 のみ許可。
     */
    @RangeEx(min = 0L, max = 10L, others = {255L})
    private Long number;
}
