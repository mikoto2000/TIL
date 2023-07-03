package dev.mikoto2000.springbootstudy.jackson.union.firststep.model;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Rectangle
 *
 * Shape の具象クラス。幅(width)と高さ(height)を持つ。
 */
@Component
@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
public class Rectangle extends Shape {
    private double width;
    private double height;
}

