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
        command = s;
    }

    public String commandType() {
        if (command.equals("add") || command.equals("sub") || command.equals("neg") || command.equals("eq")
                || command.equals("gt") || command.equals("lt") || command.equals("and") || command.equals("or")
                || command.equals("not")) {
            return "C_ARITHMETIC";
        } else if (command.startsWith("push")) {
            return "C_PUSH";
        } else if (command.startsWith("pop")) {
            return "C_POP";
        } else throw new IllegalAccessError("no such command");
    }

    public String arg1() {
        if (commandType().equals("C_ARITHMETIC")) {
            return command;
        } else if (commandType().equals("C_PUSH") || commandType().equals("C_POP")) {
            return command.split(" ")[1];
        } else throw new IllegalAccessError("no such arg1");
    }

    public int arg2() {
        if (commandType().equals("C_PUSH") || commandType().equals("C_POP")) {
            return Integer.parseInt(command.split(" ")[2]);
        } else throw new IllegalAccessError("no such arg2");
    }

}

