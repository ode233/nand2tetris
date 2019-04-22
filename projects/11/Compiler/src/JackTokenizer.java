import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

class JackTokenizer {
    private Scanner scanner;
    private String token;
    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\p{javaWhitespace}+");
    private static final Pattern EMPTY_PATTERN = Pattern.compile("");

    JackTokenizer(String jackName) {
        File file = new File(jackName);
        try {
            if (file.exists()) {
                scanner = new Scanner(file, "UTF-8");
            }
        } catch (IOException ioe) {
            throw new IllegalArgumentException("Could not open " + jackName, ioe);
        }
    }

    boolean hasMoreTokens() {
        return scanner.hasNext();
    }

    void advance() {
        while (scanner.hasNext("//.*|/\\*.*")) {
            scanner.nextLine();
            while (scanner.hasNext("\\*.*")) {
                scanner.nextLine();
            }
        }
        scanner.useDelimiter(EMPTY_PATTERN);
        if (scanner.hasNext(WHITESPACE_PATTERN)) {
            scanner.skip(WHITESPACE_PATTERN);
        }
        if (scanner.hasNext("\\{|\\}|\\(|\\)|\\[|\\]|\\.|\\,|\\;|\\+|\\-|\\*|\\/|\\&|\\||\\<|\\>|\\=|\\~")) {
            token = scanner.next();
        } else {
            token = scanner.next();
            if (token.equals("\"")) {
                while (!scanner.hasNext("\"")) {            //can't have "" in string.
                    token = token.concat(scanner.next());
                }
                token = token.concat(scanner.next());
            } else {
                while (!scanner.hasNext("\\p{javaWhitespace}|\\{|\\}|\\(|\\)|\\[|\\]|\\.|\\,|\\;|\\+|\\-|\\*|\\/|\\&|\\||\\<|\\>|\\=|\\~")) {
                    token = token.concat(scanner.next());
                }
            }
        }
        scanner.useDelimiter(WHITESPACE_PATTERN);
        while (scanner.hasNext("//.*|/\\*.*")) {
            scanner.nextLine();
            while (scanner.hasNext("\\*.*")) {
                scanner.nextLine();
            }
        }
    }

    String tokenType() {
        // keyword, symbol, integerConstant, stringConstant, identifier.
        if (token.matches("class|constructor|function|method|field|static|var|int|char|boolean|void|true|false|null|this|let|do|if|else|while|return")) {
            return "keyword";
        } else if (token.matches("\\{|\\}|\\(|\\)|\\[|\\]|\\.|\\,|\\;|\\+|\\-|\\*|/|\\&|\\||\\<|\\>|\\=|\\~")) {
            return "symbol";
        } else if (token.matches("^0*([0-2]\\d{4}|3[0-1]\\d{3}|32[0-6]\\d{2}|327[0-5]\\d|3276[0-7]|\\d{1,4})$")) {
            return "integerConstant";
        } else if (token.matches("\".*\"")) {        // the '.' symbol can appear 0 time?
            return "stringConstant";
        } else if (token.matches("(?![0-9])\\w+$")) {
            return "identifier";
        } else throw new IllegalAccessError("no such token type");
    }

    String keyword() {
        return token;
    }

    char symbol() {
        return token.charAt(0);
    }

    String identifier() {
        return token;
    }

    int intVal() {
        return Integer.parseInt(token);
    }

    String stringVal() {
        return token.substring(1, token.lastIndexOf("\""));
    }

}
