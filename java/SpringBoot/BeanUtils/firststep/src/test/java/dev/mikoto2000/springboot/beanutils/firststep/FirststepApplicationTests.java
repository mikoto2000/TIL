package dev.mikoto2000.springboot.beanutils.firststep;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.mikoto2000.springboot.beanutils.firststep.model.DstModel;
import dev.mikoto2000.springboot.beanutils.firststep.model.SrcModel;

@SpringBootTest
class FirststepApplicationTests {

  @Test
  void testBeanUtils_copyProperties() {
    SrcModel src = new SrcModel("test", 1, new BigDecimal("123.45"));

    DstModel dst = new DstModel();

    BeanUtils.copyProperties(src, dst);

    System.out.println(src);
    System.out.println(dst);

  }

  @Test
  void testObjectMapper_convertValue() {
    Map<String,Object> src = new HashMap<>();
    src.put("string", "test");
    src.put("integer", 1);
    src.put("bigDecimal", new BigDecimal("123.45"));

    DstModel dst = new ObjectMapper().convertValue(src, DstModel.class);

    System.out.println(src);
    System.out.println(dst);

  }

  @Test
  void testIntrospector() throws Exception {

    List<String> propertyNames = List.of("string", "integer", "bigDecimal");
    List<PropertyDescriptor> descriptors = List.of(Introspector.getBeanInfo(SrcModel.class).getPropertyDescriptors());
    List<String> srcModelProperties = descriptors.stream()
      .map((descriptor) -> descriptor.getName())
      .toList();

    System.out.printf("srcModelProperties: %s\n", srcModelProperties);

    // propertyNames で定義されている名前すべてが Bean に存在するかを確認
    for (String propertyName : propertyNames) {
      System.out.printf("propertyName: %s\n", propertyName);
      if (!srcModelProperties.contains(propertyName)) {
        throw new RuntimeException("property(" + propertyName + ") not found.");
      }
    }
  }
}

