package dev.mikoto2000.springbootstudy.validation.customvalidator.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;

@SpringBootTest
public class OperationTest {

    @Autowired
    Validator validator;

    @ParameterizedTest
    @ValueSource(longs = { 1L, 2L, 3L })
    public void testOperationOperationType(long value) {
        var operation = new Operation(value, 0L);

        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(operation, "bean");
        validator.validate(operation, errors);

        assertFalse(errors.hasErrors());
    }

    @ParameterizedTest
    @ValueSource(longs = { 0L, 4L })
    public void testOperationInvalidOperationType(long value) {
        var operation = new Operation(value, 0L);

        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(operation, "bean");
        validator.validate(operation, errors);

        assertTrue(errors.hasErrors());

        errors.getFieldErrors().forEach((e) -> {
            var field = e.getField();
            var message = e.getDefaultMessage();
            var regectedValue = e.getRejectedValue();
            assertEquals("operationType", field);
            assertEquals("指定できる値は次のいずれかです: 1, 2, 3", message);
            assertEquals(value, regectedValue);
        });
    }

    @ParameterizedTest
    @ValueSource(longs = { 1L, 10L, 255L })
    public void testOperationValidNumber(long value) {
        var operation = new Operation(1L, value);

        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(operation, "bean");
        validator.validate(operation, errors);

        assertFalse(errors.hasErrors());
    }

    @ParameterizedTest
    @ValueSource(longs = { -1L, 11L })
    public void testOperationInvalidNumber(long value) {
        var operation = new Operation(1L, value);

        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(operation, "bean");
        validator.validate(operation, errors);

        assertTrue(errors.hasErrors());

        errors.getFieldErrors().forEach((e) -> {
            var field = e.getField();
            var message = e.getDefaultMessage();
            var regectedValue = e.getRejectedValue();
            assertEquals("number", field);
            assertEquals("指定できる値は0から10の間の値、または、次のいずれかの値です: [255]", message);
            assertEquals(value, regectedValue);
        });
    }
}
