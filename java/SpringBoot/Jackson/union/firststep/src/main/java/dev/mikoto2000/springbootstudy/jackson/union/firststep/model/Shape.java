package dev.mikoto2000.springbootstudy.jackson.union.firststep.model;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Data;

/**
 * Shape
 *
 * 直和型(Rectangle | Circle) を定義。
 */
@Component
@JsonTypeInfo(
    // 後ろで定義している JsonSubTypes の `name` を使って型を判定する
    use = JsonTypeInfo.Id.NAME,
    // 型情報をプロパティに格納する
    include = JsonTypeInfo.As.PROPERTY,
    // 格納するプロパティ名は `type`
    property = "type"
)
@JsonSubTypes({
    // "type": "circle" の場合、 Circle クラスにマッピングする
    @JsonSubTypes.Type(value = Circle.class, name = "circle"),
    // "type": "rectangle" の場合、 Rectangle クラスにマッピングする
    @JsonSubTypes.Type(value = Rectangle.class, name = "rectangle")
})
@Data
public abstract class Shape {
}

