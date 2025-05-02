package dev.mikoto2000.springboot.beanutils.firststep.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * SrcModel
 */
@Data
@AllArgsConstructor
public class SrcModel {
  private String string;
  private Integer integer;
  private BigDecimal bigDecimal;
}
