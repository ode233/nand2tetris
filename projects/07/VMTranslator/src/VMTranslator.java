import java.io.File;

public class VMTranslator {
    public static void main(String[] args) {
        if (args[0].contains(".vm")) {
            String pathName = args[0].substring(0, args[0].lastIndexOf('.'));
            Parser parser = new Parser(args[0]);
            CodeWriter codeWriter = new CodeWriter(pathName);
            while (parser.hasMoreCommands()) {
                parser.advance();
                codeWriter.out.println("// " + parser.outputCommand);
                if (parser.commandType().equals("C_ARITHMETIC")) {
                    codeWriter.writeArithmetic(parser.arg1());
                } else if (parser.commandType().equals("C_PUSH") || parser.commandType().equals("C_POP")) {
                    codeWriter.writePushPop(parser.commandType(), parser.arg1(), parser.arg2());
                } else if (parser.commandType().equals("C_LABEL")) {
                    codeWriter.writeLabel(parser.arg1());
                } else if (parser.commandType().equals("C_GOTO")) {
                    codeWriter.writeGoTo(parser.arg1());
                } else if (parser.commandType().equals("C_IF")) {
                    codeWriter.writeIf(parser.arg1());
                } else if (parser.commandType().equals("C_FUNCTION")) {
                    codeWriter.writeFunction(parser.arg1(), parser.arg2());
                } else if (parser.commandType().equals("C_CALL")) {
                    codeWriter.writeCall(parser.arg1(), parser.arg2());
                } else if (parser.commandType().equals("C_RETURN")) {
                    codeWriter.writeReturn();
                }
            }
            codeWriter.close();
        } else {
            String pathName;
            if (args[0].contains("/")) {
                if (args[0].endsWith("/")) {
                    args[0] = args[0].substring(0, args[0].lastIndexOf("/"));
                }
                String fileName = args[0].substring(args[0].lastIndexOf('/'));
                pathName = args[0].concat(fileName);
            } else {
                pathName = args[0] + "/" + args[0];
            }
            CodeWriter codeWriter = new CodeWriter(pathName);
            codeWriter.writeInit();
            File file = new File(args[0]);
            String[] allFileName = file.list();
            int len = 0;
            if (allFileName != null) {
                len = allFileName.length;
            }
            for (int i = 0; i < len; i++) {
                if (allFileName[i].contains(".vm")) {
                    Parser parser = new Parser(args[0] + "/" + allFileName[i]);
                    codeWriter.setFileName(allFileName[i].substring(0, allFileName[i].lastIndexOf('.')));
                    while (parser.hasMoreCommands()) {
                        parser.advance();
                        codeWriter.out.println("// " + parser.outputCommand);
                        if (parser.commandType().equals("C_ARITHMETIC")) {
                            codeWriter.writeArithmetic(parser.arg1());
                        } else if (parser.commandType().equals("C_PUSH") || parser.commandType().equals("C_POP")) {
                            codeWriter.writePushPop(parser.commandType(), parser.arg1(), parser.arg2());
                        } else if (parser.commandType().equals("C_LABEL")) {
                            codeWriter.writeLabel(parser.arg1());
                        } else if (parser.commandType().equals("C_GOTO")) {
                            codeWriter.writeGoTo(parser.arg1());
                        } else if (parser.commandType().equals("C_IF")) {
                            codeWriter.writeIf(parser.arg1());
                        } else if (parser.commandType().equals("C_FUNCTION")) {
                            codeWriter.writeFunction(parser.arg1(), parser.arg2());
                        } else if (parser.commandType().equals("C_CALL")) {
                            codeWriter.writeCall(parser.arg1(), parser.arg2());
                        } else if (parser.commandType().equals("C_RETURN")) {
                            codeWriter.writeReturn();
                        }
                    }

                }
            }
            codeWriter.close();
        }
    }
}
