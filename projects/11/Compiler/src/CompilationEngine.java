import java.util.ArrayList;

public class CompilationEngine {
    private JackTokenizer jackTokenizer;
    private VMWriter vmWriter;
    private SymbolTable symbolTable;
    private String className;
    private int whileCount = 0;
    private int ifCount = 0;
    private ArrayList<String> voidSubroutine;

    // if you want ignore the void return value, use this constructor
    public CompilationEngine(String jackFileName, ArrayList<String> voidSubroutine) {
        this.voidSubroutine = voidSubroutine;
        jackTokenizer = new JackTokenizer(jackFileName);
        vmWriter = new VMWriter(jackFileName);
        symbolTable = new SymbolTable();
        jackTokenizer.advance();
        compileClass();
        vmWriter.close();
    }

    public CompilationEngine(String jackFileName) {
        jackTokenizer = new JackTokenizer(jackFileName);
        vmWriter = new VMWriter(jackFileName);
        symbolTable = new SymbolTable();
        jackTokenizer.advance();
        compileClass();
        vmWriter.close();
    }

    private void eatKeyword() {
        if (!jackTokenizer.tokenType().equals("keyword")) {
            throw new IllegalAccessError("need a keyword");
        } else jackTokenizer.advance();
    }

    private void eatSymbol() {
        if (!jackTokenizer.tokenType().equals("symbol")) {
            throw new IllegalAccessError("need a symbol");
        } else jackTokenizer.advance();
    }

    private void eatIntegerConstant() {
        if (!jackTokenizer.tokenType().equals("integerConstant")) {
            throw new IllegalAccessError("need a IntegerConstant");
        } else jackTokenizer.advance();
    }

    private void eatStringConstant() {
        if (!jackTokenizer.tokenType().equals("stringConstant")) {
            throw new IllegalAccessError("need a StringConstant");
        } else jackTokenizer.advance();
    }

    private void eatIdentifier() {
        if (!jackTokenizer.tokenType().equals("identifier")) {
            throw new IllegalAccessError("need a Identifier");
        } else jackTokenizer.advance();
    }

    private void eatType() {
        if (!jackTokenizer.tokenType().equals("identifier") && !jackTokenizer.tokenType().equals("keyword")) {
            throw new IllegalAccessError("need a type");
        } else jackTokenizer.advance();
    }

    private String tokenTypeValue() {
        String type;
        if (jackTokenizer.tokenType().equals("identifier")) {
            type = jackTokenizer.identifier();
        } else type = jackTokenizer.keyword();
        return type;
    }

    private void compileClass() {
        eatKeyword();                   // eat 'class'
        className = jackTokenizer.identifier();
        eatIdentifier();                // eat class name
        eatSymbol();                    // eat  '{'
        while (jackTokenizer.keyword().equals("static") || jackTokenizer.keyword().equals("field")) {
            compileClassVarDec();
        }
        while (jackTokenizer.keyword().equals("constructor") || jackTokenizer.keyword().equals("function") ||
                jackTokenizer.keyword().equals("method")) {
            compileSubroutine();
            symbolTable.startSubroutine();
            whileCount = 0;
            ifCount = 0;
        }
        if (jackTokenizer.symbol() != '}') {
            throw new IllegalAccessError();
        }
    }

    private void compileClassVarDec() {
        String kind;
        if (jackTokenizer.keyword().equals("field")) {
            kind = "this";
        } else kind = "static";
        eatKeyword();                           // eat 'static' or 'field'
        String type = tokenTypeValue();
        eatType();                              // eat type
        String name = jackTokenizer.identifier();
        eatIdentifier();                        // eat varName
        symbolTable.define(name, type, kind);
        while (jackTokenizer.symbol() == ',') {
            eatSymbol();                        // eat ','
            symbolTable.define(jackTokenizer.identifier(), type, kind);
            eatIdentifier();                    // eat varName
        }
        eatSymbol();                            // eat ';'
    }

    private void compileSubroutine() {
        String subroutineType = jackTokenizer.keyword();
        if (subroutineType.equals("method")) {
            symbolTable.define("this", className, "argument");
        }
        eatKeyword();                           // eat 'constructor' or 'function' or 'method'
        eatType();                              // eat void or type
        String subroutineName = jackTokenizer.identifier();
        eatIdentifier();                        // eat subroutineName
        eatSymbol();                            // eat '('
        compileParameterList();
        eatSymbol();                            // eat ')'
        eatSymbol();                          // eat '{'
        while (jackTokenizer.keyword().equals("var")) {
            compileVarDec();
        }
        vmWriter.writeFunction(className + "." + subroutineName, symbolTable.varCount("local"));
        if (subroutineType.equals("method")) {
            vmWriter.writePush("argument", 0);
            vmWriter.writePop("pointer", 0);
        } else if (subroutineType.equals("constructor")) {
            vmWriter.writePush("constant", symbolTable.varCount("this"));
            vmWriter.writeCall("Memory.alloc", 1);
            vmWriter.writePop("pointer", 0);
        }
        compileStatements();
        eatSymbol();                          // eat '}'
    }

    private void compileParameterList() {
        if (jackTokenizer.tokenType().equals("identifier") || jackTokenizer.tokenType().equals("keyword")) {
            String type = tokenTypeValue();
            eatType();                  // eat type
            String name = jackTokenizer.identifier();
            symbolTable.define(name, type, "argument");
            eatIdentifier();            // eat varName
            while (jackTokenizer.symbol() == ',') {
                eatSymbol();            // eat ','
                type = tokenTypeValue();
                eatType();              // eat type
                name = jackTokenizer.identifier();
                symbolTable.define(name, type, "argument");
                eatIdentifier();        // eat varName
            }
        }
    }

    private void compileVarDec() {
        eatKeyword();             // eat 'var'
        String type = tokenTypeValue();
        eatType();                // eat type
        String name = jackTokenizer.identifier();
        symbolTable.define(name, type, "local");
        eatIdentifier();          // eat varName
        while (jackTokenizer.symbol() == ',') {
            eatSymbol();          // eat ','
            symbolTable.define(jackTokenizer.identifier(), type, "local");
            eatIdentifier();      // eat varName
        }
        eatSymbol();              // eat ';'
    }

    private void compileStatements() {
        while (jackTokenizer.keyword().matches("let|if|while|do|return")) {
            if (jackTokenizer.keyword().equals("let")) {
                compileLet();
            } else if (jackTokenizer.keyword().equals("if")) {
                ifCount++;
                compileIf(ifCount);
            } else if (jackTokenizer.keyword().equals("while")) {
                whileCount++;
                compileWhile(whileCount);
            } else if (jackTokenizer.keyword().equals("do")) {
                compileDo();
            } else if (jackTokenizer.keyword().equals("return")) {
                compileReturn();
            }
        }
    }

    private void compileDo() {
        eatKeyword();                     // eat do
        // compile subroutineCall
        String firstName = jackTokenizer.identifier();
        eatIdentifier();                  // eat subroutineName | className | varName
        int argNum = 0;
        if (jackTokenizer.symbol() == '(') {
            vmWriter.writePush("pointer", 0);
            argNum++;
            eatSymbol();                  // eat '('
            argNum += compileExpressionList();
            eatSymbol();                  // eat ')'
            vmWriter.writeCall(className + "." + firstName, argNum);
//            if (voidSubroutine.contains(className + "." + firstName)) {
//                vmWriter.writePop("temp", 0);
//            }
        } else {
            if (!firstName.matches("[A-Z].*")) {
                vmWriter.writePush(symbolTable.kindOf(firstName), symbolTable.indexOf(firstName));
                argNum++;
                firstName = symbolTable.typeOf(firstName);
            }
            eatSymbol();                  // eat '.'
            String subroutineName = jackTokenizer.identifier();
            eatIdentifier();              // eat subroutineName
            eatSymbol();                  // eat '('
            argNum += compileExpressionList();
            eatSymbol();                  // eat ')'
            vmWriter.writeCall(firstName + "." + subroutineName, argNum);
//            if (voidSubroutine.contains(firstName + "." + subroutineName)) {
//                vmWriter.writePop("temp", 0);
//            }
        }
        eatSymbol();                      // eat ';'

    }

    private void compileLet() {
        eatKeyword();                     // eat let
        String id = jackTokenizer.identifier();
        eatIdentifier();                  // eat varName
        boolean isArrayElement = false;
        if (jackTokenizer.symbol() == '[') {
            isArrayElement = true;
            eatSymbol();                  // eat '['
            compileExpression();
            eatSymbol();                  // eat ']'
        }
        eatSymbol();                        // eat '='
        compileExpression();
        eatSymbol();                        // eat ';'
        if (isArrayElement) {
            vmWriter.writePop("temp", 0);
            vmWriter.writePush(symbolTable.kindOf(id), symbolTable.indexOf(id));
            vmWriter.writeArithmetic("add");
            vmWriter.writePop("pointer", 1);
            vmWriter.writePush("temp", 0);
            vmWriter.writePop("that", 0);
        } else {
            vmWriter.writePop(symbolTable.kindOf(id), symbolTable.indexOf(id));
        }
    }

    private void compileWhile(int whileCount) {
        vmWriter.writeLabel("whileBegin" + whileCount);
        eatKeyword();                     // eat while
        eatSymbol();                      // eat '('
        compileExpression();
        vmWriter.writeArithmetic("not");
        vmWriter.writeIf("whileEnd" + whileCount);
        eatSymbol();                      // eat ')'
        eatSymbol();                      // eat '{'
        compileStatements();
        eatSymbol();                      // eat '}'
        vmWriter.writeGoTo("whileBegin" + whileCount);
        vmWriter.writeLabel("whileEnd" + whileCount);
    }

    private void compileReturn() {
        eatKeyword();                     // eat return
        if (!((jackTokenizer.tokenType().equals("symbol")) && jackTokenizer.symbol() == ';')) {
            compileExpression();
            vmWriter.writeReturn();
        } else {
            vmWriter.writePush("constant", 0);
            vmWriter.writeReturn();
        }
        eatSymbol();                  // eat ';'

    }

    private void compileIf(int ifCount) {
        eatKeyword();                     // eat if
        eatSymbol();                      // eat '('
        compileExpression();
        vmWriter.writeArithmetic("not");
        vmWriter.writeIf("ifElse" + ifCount);
        eatSymbol();                      // eat ')'
        eatSymbol();                      // eat '{'
        compileStatements();
        eatSymbol();                      // eat '}'
        vmWriter.writeGoTo("ifEnd" + ifCount);
        vmWriter.writeLabel("ifElse" + ifCount);
        if (jackTokenizer.keyword().equals("else")) {
            eatKeyword();                  // eat else
            eatSymbol();                  // eat '{'
            compileStatements();
            eatSymbol();                  // eat '{'
        }
        vmWriter.writeLabel("ifEnd" + ifCount);
    }

    private void compileExpression() {
        //push the term to stack;
        compileTerm();
        while (String.valueOf(jackTokenizer.symbol()).matches("\\+|\\-|\\*|/|\\&||\\||<|>|=")) {
            char op = jackTokenizer.symbol();
            eatSymbol();            // eat op
            compileTerm();
            if (op == '+') {
                vmWriter.writeArithmetic("add");
            } else if (op == '-') {
                vmWriter.writeArithmetic("sub");
            } else if (op == '*') {
                vmWriter.writeCall("Math.multiply", 2);
            } else if (op == '/') {
                vmWriter.writeCall("Math.divide", 2);
            } else if (op == '&') {
                vmWriter.writeArithmetic("and");
            } else if (op == '|') {
                vmWriter.writeArithmetic("or");
            } else if (op == '<') {
                vmWriter.writeArithmetic("lt");
            } else if (op == '>') {
                vmWriter.writeArithmetic("gt");
            } else if (op == '=') {
                vmWriter.writeArithmetic("eq");
            } else throw new IllegalArgumentException("symbol error");
        }
    }

    private void compileTerm() {
        if (jackTokenizer.tokenType().equals("integerConstant")) {
            vmWriter.writePush("constant", jackTokenizer.intVal());
            eatIntegerConstant();           // eat IntegerConstant
        } else if (jackTokenizer.tokenType().equals("stringConstant")) {
            String string = jackTokenizer.stringVal();
            vmWriter.writePush("constant", string.length());
            vmWriter.writeCall("String.new", 1);
            for (int i = 0; i < string.length(); i++) {
                char c = string.charAt(i);
                if (c == '\b') {
                    vmWriter.writeCall("String.backSpace", 0);
                } else if (c == '"') {
                    vmWriter.writeCall("String.doubleQuote", 0);
                } else if (c == '\n') {
                    vmWriter.writeCall("String.newLine", 0);
                } else vmWriter.writePush("constant", c);
                vmWriter.writeCall("String.appendChar", 2);
            }
            eatStringConstant();            // eat StringConstant
        } else if (jackTokenizer.tokenType().equals("keyword") && jackTokenizer.keyword().matches("true|false|null|this")) {
            if (jackTokenizer.keyword().equals("true")) {
                vmWriter.writePush("constant", 1);
                vmWriter.writeArithmetic("neg");
            } else if (jackTokenizer.keyword().equals("this")) {
                vmWriter.writePush("pointer", 0);
            } else vmWriter.writePush("constant", 0);
            eatKeyword();                   // eat true|false|null|this
        } else if (jackTokenizer.tokenType().equals("identifier")) {    // maybe varName|varName[expression]|subroutineCall
            String id = jackTokenizer.identifier();
            eatIdentifier();                // eat identifier
            if (jackTokenizer.tokenType().equals("symbol")) {
                if (jackTokenizer.symbol() == '[') {
                    eatSymbol();                // eat '['
                    compileExpression();
                    eatSymbol();                // eat ']'
                    vmWriter.writePush(symbolTable.kindOf(id), symbolTable.indexOf(id));
                    vmWriter.writeArithmetic("add");
                    vmWriter.writePop("pointer", 1);
                    vmWriter.writePush("that", 0);
                } else if (jackTokenizer.symbol() == '(') {
                    int argNum = 0;
                    vmWriter.writePush("pointer", 0);
                    argNum++;
                    eatSymbol();                  // eat '('
                    argNum += compileExpressionList();
                    eatSymbol();                  // eat ')'
                    vmWriter.writeCall(className + "." + id, argNum);
                } else if (jackTokenizer.symbol() == '.') {
                    int argNum = 0;
                    if (!id.matches("[A-Z].*")) {
                        vmWriter.writePush(symbolTable.kindOf(id), symbolTable.indexOf(id));
                        argNum++;
                        id = symbolTable.typeOf(id);
                    }
                    eatSymbol();                  // eat '.'
                    String subroutineName = jackTokenizer.identifier();
                    eatIdentifier();              // eat subroutineName
                    eatSymbol();                  // eat '('
                    argNum += compileExpressionList();
                    eatSymbol();                  // eat ')'
                    vmWriter.writeCall(id + "." + subroutineName, argNum);
                } else vmWriter.writePush(symbolTable.kindOf(id), symbolTable.indexOf(id));
            } else vmWriter.writePush(symbolTable.kindOf(id), symbolTable.indexOf(id));
        } else if (jackTokenizer.tokenType().equals("symbol") && jackTokenizer.symbol() == '(') {
            eatSymbol();        // eat '('
            compileExpression();
            eatSymbol();        // eat ')'
        } else if (jackTokenizer.tokenType().equals("symbol") && (jackTokenizer.symbol() == '-' || jackTokenizer.symbol() == '~')) {
            char singleOp = jackTokenizer.symbol();
            eatSymbol();        // eat -|~
            compileTerm();
            if (singleOp == '-') {
                vmWriter.writeArithmetic("neg");
            } else if (singleOp == '~') {
                vmWriter.writeArithmetic("not");
            }
        } else throw new IllegalAccessError("not a term");
    }

    private int compileExpressionList() {
        int count = 0;
        if (!(jackTokenizer.tokenType().equals("symbol") && jackTokenizer.symbol() == ')')) {
            // every time of compileExpression,push the value to stack.
            compileExpression();
            count++;
            while (jackTokenizer.symbol() == ',') {
                eatSymbol();                // eat ','
                compileExpression();
                count++;
            }
        }
        return count;
    }


}
