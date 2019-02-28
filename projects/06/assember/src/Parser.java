import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Scanner;

public class Parser {
    private In in;
    private String command;

    public Parser(String name) {
        in = new In(name);
    }

    public boolean hasMoreCommands() {
        return in.hasNextLine();
    }

    public void advance() {
        String s = in.readLine();
        while (s.isEmpty() || s.startsWith("//")) {
            s = in.readLine();
        }
        Scanner line = new Scanner(s);
        command = line.next();
    }

    public String commandType() {
        char c = command.charAt(0);
        if (c == '@') {
            return "A_COMMAND";
        } else if (c == '(') {
            return "L_COMMAND";
        } else return "C_COMMAND";
    }

    public String symbol() {
        if (commandType().equals("A_COMMAND")) {
            return command.substring(1);
        } else if (commandType().equals("L_COMMAND")) {
            return command.substring(1, command.lastIndexOf(')'));
        } else throw new IllegalArgumentException("can't call this method");
    }

    public String dest() {
        if (commandType().equals("C_COMMAND")) {
            if (command.contains("=")) {
                return command.substring(0, command.lastIndexOf('='));
            } else return "";
        } else throw new IllegalArgumentException("can't call this method");
    }

    public String comp() {
        if (commandType().equals("C_COMMAND")) {
            if (command.contains(";")) {
                if (command.contains("=")) {
                    return command.substring(command.lastIndexOf('=') + 1, command.lastIndexOf(';'));
                } else return command.substring(0, command.lastIndexOf(';'));
            } else
                return command.substring(command.lastIndexOf('=') + 1);
        } else throw new IllegalArgumentException("can't call this method");
    }

    public String jump() {
        if (commandType().equals("C_COMMAND")) {
            if (command.contains(";")) {
                return command.substring(command.lastIndexOf(';') + 1);
            } else
                return "";
        } else throw new IllegalArgumentException("can't call this method");
    }

    public static void main(String[] args) {
        /*Parser parser = new Parser(args[0]);
        while (parser.hasMoreCommands()) {
            parser.advance();
            StdOut.println(parser.command);
        }*/
        String s = "D=M";
        if (s.contains("=")) {
            StdOut.println(Integer.toString(s.lastIndexOf('=') - 1));
            StdOut.println(s.substring(0, 1));
        } else StdOut.println();
    }
}
