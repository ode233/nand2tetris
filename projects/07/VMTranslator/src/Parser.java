public class Parser {
    private In in;
    private String[] command;
    String outputCommand;

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
        outputCommand = s;
        String s1 = s.replace("\t", " ");
        command = s1.split(" ");
    }

    public String commandType() {
        if (command[0].equals("add") || command[0].equals("sub") || command[0].equals("neg") || command[0].equals("eq")
                || command[0].equals("gt") || command[0].equals("lt") || command[0].equals("and") || command[0].equals("or")
                || command[0].equals("not")) {
            return "C_ARITHMETIC";
        } else if (command[0].equals("push")) {
            return "C_PUSH";
        } else if (command[0].equals("pop")) {
            return "C_POP";
        } else if (command[0].equals("label")) {
            return "C_LABEL";
        } else if (command[0].equals("goto")) {
            return "C_GOTO";
        } else if (command[0].equals("if-goto")) {
            return "C_IF";
        } else if (command[0].equals("function")) {
            return "C_FUNCTION";
        } else if (command[0].equals("return")) {
            return "C_RETURN";
        } else if (command[0].equals("call")) {
            return "C_CALL";
        } else throw new IllegalAccessError("no such command");
    }

    public String arg1() {
        if (commandType().equals("C_ARITHMETIC")) {
            return command[0];
        } else if (commandType().equals("C_PUSH") || commandType().equals("C_POP")) {
            return command[1];
        } else if (commandType().equals("C_LABEL")) {
            return command[1];
        } else if (commandType().equals("C_GOTO")) {
            return command[1];
        } else if (commandType().equals("C_IF")) {
            return command[1];
        } else if (commandType().equals("C_FUNCTION")) {
            return command[1];
        } else if (commandType().equals("C_CALL")) {
            return command[1];
        } else throw new IllegalAccessError("no such arg1");
    }

    public int arg2() {
        if (commandType().equals("C_PUSH") || commandType().equals("C_POP")) {
            return Integer.parseInt(command[2]);
        } else if (commandType().equals("C_FUNCTION")) {
            return Integer.parseInt(command[2]);
        } else if (commandType().equals("C_CALL")) {
            return Integer.parseInt(command[2]);
        } else throw new IllegalAccessError("no such arg2");
    }

}

