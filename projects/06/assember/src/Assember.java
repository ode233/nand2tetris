// There must be no blank line in the last line of the ***.asm file (This is the bug in my Assember)

import edu.princeton.cs.algs4.Out;

public class Assember {
    public static void main(String[] args) {
        Parser parser = new Parser(args[0]);
        Out out = new Out(args[0].replace("asm", "hack"));
        SymbolTable symbolTable = new SymbolTable();
        int numOfLine = -1;
        while (parser.hasMoreCommands()) {
            parser.advance();
            numOfLine++;
            if (parser.commandType().equals("L_COMMAND")) {
                symbolTable.addEntry(parser.symbol(), numOfLine);
                numOfLine--;
            }
        }
        parser = new Parser(args[0]);
        int ramAddress = 16;
        while (parser.hasMoreCommands()) {
            parser.advance();
            if (parser.commandType().equals("A_COMMAND")) {
                if (parser.symbol().matches("-?[0-9]+\\.?[0-9]*")) {
                    int symbolInt = Integer.parseInt(parser.symbol());
                    String binaryString = Integer.toBinaryString(symbolInt);
                    StringBuilder zero = new StringBuilder();
                    for (int i = 0; i < 15 - binaryString.length(); i++) {
                        zero.append('0');
                    }
                    binaryString = zero + binaryString;
                    out.println("0" + binaryString);
                } else if (symbolTable.contains(parser.symbol())) {
                    int symbolInt = symbolTable.getAddress(parser.symbol());
                    String binaryString = Integer.toBinaryString(symbolInt);
                    StringBuilder zero = new StringBuilder();
                    for (int i = 0; i < 15 - binaryString.length(); i++) {
                        zero.append('0');
                    }
                    binaryString = zero + binaryString;
                    out.println("0" + binaryString);
                } else if (!symbolTable.contains(parser.symbol())) {
                    symbolTable.addEntry(parser.symbol(), ramAddress);
                    String binaryString = Integer.toBinaryString(ramAddress);
                    StringBuilder zero = new StringBuilder();
                    for (int i = 0; i < 15 - binaryString.length(); i++) {
                        zero.append('0');
                    }
                    binaryString = zero + binaryString;
                    out.println("0" + binaryString);
                    ramAddress++;
                }
            } else if (parser.commandType().equals("C_COMMAND")) {
                out.println("111" + Code.comp(parser.comp()) + Code.dest(parser.dest()) + Code.jump(parser.jump()));
            }
        }
    }
}
