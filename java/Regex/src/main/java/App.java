import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * 
 */
public class App {
    public static void main(String[] args) {

        System.out.println("==== Pattern#matches ====");

        executeMatcheUsePattern_Matches(".", "aaa");
        executeMatcheUsePattern_Matches("..", "aaa");
        executeMatcheUsePattern_Matches("...", "aaa");
        executeMatcheUsePattern_Matches(".*", "aaa");
        executeMatcheUsePattern_Matches("te", "testing");
        executeMatcheUsePattern_Matches("te.*", "testing");
        executeMatcheUsePattern_Matches(".*56.*", "1234567890");

        System.out.println("\n\n==== Pattern#compile -> Pattern#matcher -> Matcher#matches ====");

        String[] strs = {
                "aaa"
                , "testing"
                , "1"
                , "12"
                , "123"
                , "1234"
                , "12345"
                , "123456"
                , "1234567"
                , "12345678"
                , "123456789"
                , "1234567890"};

        List<String> strings = Arrays.asList(strs);
        executeMatcheUsePattern_Matches(".*56.*", strings);
    }

    // boolean is_match = Pattern.matches("pattern", "target");
    private static void executeMatcheUsePattern_Matches(String regex, String target) {

        // one time(Generate Pattern, Pattern compile, generate Matcher and test).
        boolean is_match = Pattern.matches(regex, target);
        if (is_match) {
            System.out.println(regex + " is contained " + target + ".");
        } else {
            System.out.println(regex + " is not contained " + target + ".");
        }
    }

    // Pattern p = Pattern.compile(regex);
    // Matcher m = p.matcher();
    // boolean is_match = m.matches(target);
    private static void executeMatcheUsePattern_Matches(String regex, List<String> targets) {
        // Generate Pattern and Pattern compile.
        Pattern p = Pattern.compile(regex);

        for (String target : targets) {
            // Generate Matcher
            Matcher m = p.matcher(target);

            // test.
            boolean is_match = m.matches();
            if (is_match) {
                System.out.println(regex + " is contained " + target + ".");
            } else {
                System.out.println(regex + " is not contained " + target + ".");
            }
        }
    }
}
