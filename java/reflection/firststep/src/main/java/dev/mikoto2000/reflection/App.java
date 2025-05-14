package dev.mikoto2000.reflection;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import dev.mikoto2000.reflection.model.User;

/**
 * Hello world!
 */
public class App {
  public static void main(String[] args)
      throws IntrospectionException, IllegalAccessException, InvocationTargetException {
    User user = new User(
        "mikoto2000",
        2000,
        "mikoto2000@gmail.com",
        LocalDate.of(2025, 5, 5));

    String[] 表示順序 = new String[] { "tanjyoubi", "email", "age", "name" };

    for (var プロパティ名 : 表示順序) {

      var pd = new PropertyDescriptor(プロパティ名, user.getClass());
      var getter = pd.getReadMethod();
      var obj = getter.invoke(user);

      System.out.println(format(obj));
    }
  }

  public static String format(Object value) {
    if (value == null) {
      return "";
    }

    if (value instanceof String) {
      return "\"" + value + "\"";
    }

    if (value instanceof Number) {
      return value.toString();
    }

    if (value instanceof TemporalAccessor) {
      // LocalDate, LocalDateTime, ZonedDateTime など
      return DateTimeFormatter.ofPattern("yyyyMMdd").format((TemporalAccessor) value);
    }

    throw new RuntimeException("予期せぬ型です");
  }
}
