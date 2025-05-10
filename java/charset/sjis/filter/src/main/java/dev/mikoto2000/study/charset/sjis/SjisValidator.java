package dev.mikoto2000.study.charset.sjis;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * SjisValidator
 *
 * See: http://charset.7jp.net/sjis.html
 * See: https://www.asahi-net.or.jp/~ax2s-kmtn/ref/jisx0208.html
 */
public class SjisValidator {

  public static final ValidArea VALID_AREA_HANKAKU = new ValidArea(0x0020, 0x00DF);

  public static final ValidArea VALID_AREA_全角記号 = new ValidArea(0x8140, 0x81FC);
  public static final ValidArea VALID_AREA_全角英数 = new ValidArea(0x824F, 0x829A);
  public static final ValidArea VALID_AREA_全角ひらがな = new ValidArea(0x829F, 0x82F2);
  public static final ValidArea VALID_AREA_全角カタカナ = new ValidArea(0x8340, 0x8396);
  public static final ValidArea VALID_AREA_第一水準漢字 = new ValidArea(0x889F, 0x9872);
  public static final ValidArea VALID_AREA_第二水準漢字 = new ValidArea(0x989F, 0xEAA4);

  private static final CharsetEncoder encoder = Charset.forName("SJIS").newEncoder();

  private SjisValidator() {
  };

  public static List<ValidationError> validate(String string, ValidArea... validAreas) {
    List<ValidationError> errors = new ArrayList<>();

    if (!encoder.canEncode(string)) {
      throw new RuntimeException("SJIS でない文字列です。");
    }

    char[] chars = string.toCharArray();

    for (int i = 0; i < chars.length; i++) {
      if (!isValid(chars[i], validAreas)) {
        errors.add(new ValidationError(i, String.format("使用できない文字です(%s)", chars[i])));
      }
    }

    return errors;
  }

  private static boolean isValid(char c, ValidArea... validAreas) {

    try {
      byte[] sjisBytes = String.valueOf(c).getBytes("SJIS");

      int sjisValue = -1;
      if (sjisBytes.length == 1) {
        sjisValue = sjisBytes[0] & 0x00FF;
      } else {
        sjisValue = ((sjisBytes[0] & 0x00FF) << 8) | (sjisBytes[1] & 0x00FF);
      }

      for (ValidArea validArea : validAreas) {
        if (sjisValue <= validArea.max && sjisValue >= validArea.min) {
          return true;
        }
      }

      return false;
    } catch (UnsupportedEncodingException e) {
      return false;
    }
  }

  public static record ValidArea(int min, int max) {
  }

  public static record ValidationError(long position, String message) {
  }
}
