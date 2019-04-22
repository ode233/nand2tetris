import java.util.regex.Pattern;

public class Test {
    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\p{javaWhitespace}+");
    private static final Pattern EMPTY_PATTERN = Pattern.compile("");

    private static void test(int a) {
        int i = a;
        i--;
        if (i > 0) {
            test(i);
        }
        System.out.println(i);
    }

    public static void main(String[] args) {
        test(2);
    }
}


