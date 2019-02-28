public class VMTranslator {
    public static void main(String[] args) {
        String name = args[0].substring(0, args[0].lastIndexOf('.'));
        Parser parser = new Parser(args[0]);
        CodeWriter codeWriter = new CodeWriter(name);
        while (parser.hasMoreCommands()) {
            parser.advance();
            if (parser.commandType().equals("C_ARITHMETIC")) {
                codeWriter.writeArithmetic(parser.arg1());
            } else if (parser.commandType().equals("C_PUSH") || parser.commandType().equals("C_POP")) {
                codeWriter.writePushPop(parser.commandType(), parser.arg1(), parser.arg2());
            }
        }
        codeWriter.close();
    }
}
