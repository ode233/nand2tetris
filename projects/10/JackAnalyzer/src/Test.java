import java.util.Scanner;
import java.util.regex.Pattern;

public class Test {
    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\p{javaWhitespace}+");
    private static final Pattern EMPTY_PATTERN = Pattern.compile("");

    public static void main(String[] args) {
        Scanner scanner = new Scanner("asd");
        System.out.println(scanner.next());
        System.out.println(scanner.hasNext());
    }


}
