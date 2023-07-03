package dev.mikoto2000.springbootstudy.jackson.union.firststep.model;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Circle
 *
 * Shape の具象クラス。半径(r)をプロパティとして持つ。
 */
@Component
@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
public class Circle extends Shape {
    private double r;
}

