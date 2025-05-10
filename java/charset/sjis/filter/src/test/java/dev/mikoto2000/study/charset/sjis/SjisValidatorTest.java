package dev.mikoto2000.study.charset.sjis;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * SjisValidatorTest
 */
public class SjisValidatorTest {

  @BeforeEach
  public void setup() {
  }

  @AfterEach
  public void tearDown() {
  }

  @Test
  public void testValidation半角() {
    final String 全角記号 = " ﾟ";

    var errors = SjisValidator.validate(全角記号, SjisValidator.VALID_AREA_HANKAKU);

    assertEquals(0, errors.size());

  }

  @Test
  public void testValidation全角記号() {
    final String 全角記号 = "　◯";

    var errors = SjisValidator.validate(全角記号, SjisValidator.VALID_AREA_全角記号);

    assertEquals(0, errors.size());

    final String 全角記号NG = "熙０";

    var errors2 = SjisValidator.validate(全角記号NG, SjisValidator.VALID_AREA_全角記号);

    assertEquals(2, errors2.size());
  }

  @Test
  public void testValidation全角英数() {
    final String 全角英数 = "０ｚ";

    var errors = SjisValidator.validate(全角英数, SjisValidator.VALID_AREA_全角英数);

    assertEquals(0, errors.size());

    final String 全角英数NG = "◯ぁ";

    var errors2 = SjisValidator.validate(全角英数NG, SjisValidator.VALID_AREA_全角英数);

    assertEquals(2, errors2.size());
  }

  @Test
  public void testValidation全角ひらがな() {
    final String 全角ひらがな = "ぁん";

    var errors = SjisValidator.validate(全角ひらがな, SjisValidator.VALID_AREA_全角ひらがな);

    assertEquals(0, errors.size());

    final String 全角ひらがなNG = "ｚァ";

    var errors2 = SjisValidator.validate(全角ひらがなNG, SjisValidator.VALID_AREA_全角ひらがな);

    assertEquals(2, errors2.size());
  }

  @Test
  public void testValidation全角カタカナ() {
    final String 全角カタカナ = "ァヶ";

    var errors = SjisValidator.validate(全角カタカナ, SjisValidator.VALID_AREA_全角カタカナ);

    assertEquals(0, errors.size());

    final String 全角カタカナNG = "んΑ";

    var errors2 = SjisValidator.validate(全角カタカナNG, SjisValidator.VALID_AREA_全角カタカナ);

    assertEquals(2, errors2.size());
  }

  @Test
  public void testValidation第一水準漢字() {
    final String 全角記号 = "亜腕";

    var errors = SjisValidator.validate(全角記号, SjisValidator.VALID_AREA_第一水準漢字);

    assertEquals(0, errors.size());

    final String 第一水準漢字NG = "╂弌";

    var errors2 = SjisValidator.validate(第一水準漢字NG, SjisValidator.VALID_AREA_第一水準漢字);

    assertEquals(2, errors2.size());
  }

  @Test
  public void testValidation第二水準漢字() {
    final String 第二水準漢字 = "弌熙";

    var errors = SjisValidator.validate(第二水準漢字, SjisValidator.VALID_AREA_第二水準漢字);

    assertEquals(0, errors.size());

    final String 第二水準漢字NG = "腕 ";

    var errors2 = SjisValidator.validate(第二水準漢字NG, SjisValidator.VALID_AREA_第二水準漢字);

    assertEquals(2, errors2.size());
  }

  @Test
  public void testValidation独自エリア() {
    final String Aのみ = "A";
    final SjisValidator.ValidArea AのみArea = new SjisValidator.ValidArea(0x0041, 0x0041);

    var errors = SjisValidator.validate(Aのみ, AのみArea);

    assertEquals(0, errors.size());

    final String AのみNG = " B";

    var errors2 = SjisValidator.validate(AのみNG, AのみArea);

    assertEquals(2, errors2.size());
  }

  @Test
  public void testValidation複数エリア() {
    final String ABのみ = "AB";
    final SjisValidator.ValidArea AのみArea = new SjisValidator.ValidArea(0x0041, 0x0041);
    final SjisValidator.ValidArea BのみArea = new SjisValidator.ValidArea(0x0042, 0x0042);

    var errors = SjisValidator.validate(ABのみ, AのみArea, BのみArea);

    assertEquals(0, errors.size());

    final String ABのみNG = " C";

    var errors2 = SjisValidator.validate(ABのみNG, AのみArea, BのみArea);

    assertEquals(2, errors2.size());
  }
}
