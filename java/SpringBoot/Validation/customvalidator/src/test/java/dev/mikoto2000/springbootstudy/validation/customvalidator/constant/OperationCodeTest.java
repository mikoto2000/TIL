package dev.mikoto2000.springbootstudy.validation.customvalidator.constant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OperationCodeTest {
    @Test
    public void testOfCreate() {
        var create = OperationCode.of(1L);
        assertEquals(OperationCode.CREATE, create);
    }

    @Test
    public void testOfModify() {
        var modify = OperationCode.of(2L);
        assertEquals(OperationCode.MODIFY, modify);
    }

    @Test
    public void testOfDelete() {
        var delete = OperationCode.of(3L);
        assertEquals(OperationCode.DELETE, delete);
    }

    @Test
    public void testOfOther() {
        var e = assertThrows(IllegalArgumentException.class, () -> OperationCode.of(4L));

        assertEquals("不明なコード: { class: OperationCode, code: 4 }", e.getMessage());
    }
}
