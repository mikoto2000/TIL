package dev.mikoto2000.springboot.beanutils.firststep.model;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DstModel
 */
@Data
@NoArgsConstructor
public class DstModel {
  private String string;
  private Integer integer;
  private BigDecimal bigDecimal;
}

