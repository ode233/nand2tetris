public class CompilationEngine {
    private JackTokenizer jackTokenizer;
    private Out out;
    private String statementsSpace = "";

    public CompilationEngine(String jackFileName) {
        jackTokenizer = new JackTokenizer(jackFileName);
        out = new Out(jackFileName.substring(0, jackFileName.lastIndexOf(".")) + ".xml");
        jackTokenizer.advance();
        compileClass();
        out.close();
    }

    private void eatKeyword(String space) {
        if (!jackTokenizer.tokenType().equals("keyword")) {
            throw new IllegalAccessError("need a keyword");
        } else {
            out.println(space + "<keyword> " + jackTokenizer.keyword() + " </keyword>");
            jackTokenizer.advance();
        }
    }

    private void eatSymbol(String space) {
        if (!jackTokenizer.tokenType().equals("symbol")) {
            throw new IllegalAccessError("need a symbol");
        } else {
            if (jackTokenizer.symbol() == '<') {
                out.println(space + "<symbol> &lt; </symbol>");
            } else if (jackTokenizer.symbol() == '>') {
                out.println(space + "<symbol> &gt; </symbol>");
            } else if (jackTokenizer.symbol() == '&') {
                out.println(space + "<symbol> &amp; </symbol>");
            } else out.println(space + "<symbol> " + jackTokenizer.symbol() + " </symbol>");
            jackTokenizer.advance();
        }
    }

    private void eatIntegerConstant(String space) {
        if (!jackTokenizer.tokenType().equals("integerConstant")) {
            throw new IllegalAccessError("need a IntegerConstant");
        } else {
            out.println(space + "<integerConstant> " + jackTokenizer.intVal() + " </integerConstant>");
            jackTokenizer.advance();
        }
    }

    private void eatStringConstant(String space) {
        if (!jackTokenizer.tokenType().equals("stringConstant")) {
            throw new IllegalAccessError("need a StringConstant");
        } else {
            out.println(space + "<stringConstant> " + jackTokenizer.stringVal() + " </stringConstant>");
            jackTokenizer.advance();
        }
    }

    private void eatIdentifier(String space) {
        if (!jackTokenizer.tokenType().equals("identifier")) {
            throw new IllegalAccessError("need a Identifier");
        } else {
            out.println(space + "<identifier> " + jackTokenizer.identifier() + " </identifier>");
            jackTokenizer.advance();
        }
    }

    private void eatType(String space) {
        if (!jackTokenizer.tokenType().equals("identifier") && !jackTokenizer.tokenType().equals("keyword")) {
            throw new IllegalAccessError("need a type");
        } else {
            if (jackTokenizer.tokenType().equals("identifier")) {
                out.println(space + "<identifier> " + jackTokenizer.identifier() + " </identifier>");
                jackTokenizer.advance();
            } else {
                out.println(space + "<keyword> " + jackTokenizer.keyword() + " </keyword>");
                jackTokenizer.advance();
            }

        }
    }

    private void compileClass() {
        out.println("<class>");
        eatKeyword("  ");                   // eat 'class'
        eatIdentifier("  ");                // eat class name
        eatSymbol("  ");                    // eat  '{'
        while (jackTokenizer.keyword().equals("static") || jackTokenizer.keyword().equals("field")) {
            compileClassVarDec();
        }
        while (jackTokenizer.keyword().equals("constructor") || jackTokenizer.keyword().equals("function") ||
                jackTokenizer.keyword().equals("method")) {
            compileSubroutine();
        }
        if (jackTokenizer.symbol() != '}') {
            throw new IllegalAccessError();
        } else {
            out.println("  <symbol> } </symbol>");
        }
        out.println("</class>");
    }

    private void compileClassVarDec() {
        out.println("  <classVarDec>");
        eatKeyword("    ");                           // eat 'static' or 'field'
        eatType("    ");                              // eat type
        eatIdentifier("    ");                        // eat varName
        while (jackTokenizer.symbol() == ',') {
            eatSymbol("    ");                        // eat ','
            eatIdentifier("    ");                    // eat varName
        }
        eatSymbol("    ");                            // eat ';'
        out.println("  </classVarDec>");
    }

    private void compileSubroutine() {
        out.println("  <subroutineDec>");
        eatKeyword("    ");                           // eat 'constructor' or 'function' or 'method'
        eatType("    ");                              // eat void or type
        eatIdentifier("    ");                        // eat subroutineName
        eatSymbol("    ");                            // eat '('
        compileParameterList();
        eatSymbol("    ");                            // eat ')'
        out.println("    <subroutineBody>");
        eatSymbol("      ");                          // eat '{'
        while (jackTokenizer.keyword().equals("var")) {
            compileVarDec();
        }
        compileStatements();
        eatSymbol("      ");                          // eat '}'
        out.println("    </subroutineBody>");
        out.println("  </subroutineDec>");
    }

    private void compileParameterList() {
        out.println("    <parameterList>");
        if (jackTokenizer.tokenType().equals("identifier") || jackTokenizer.tokenType().equals("keyword")) {          // if token is type
            eatType("      ");                  // eat type
            eatIdentifier("      ");            // eat varName
            while (jackTokenizer.symbol() == ',') {
                eatSymbol("      ");            // eat ','
                eatType("      ");              // eat type
                eatIdentifier("      ");        // eat varName
            }
        }
        out.println("    </parameterList>");
    }

    private void compileVarDec() {
        out.println("      <varDec>");
        eatKeyword("        ");             // eat 'var'
        eatType("        ");                // eat type
        eatIdentifier("        ");          // eat varName
        while (jackTokenizer.symbol() == ',') {
            eatSymbol("        ");          // eat ','
            eatIdentifier("        ");      // eat varName
        }
        eatSymbol("        ");              // eat ';'
        out.println("      </varDec>");
    }

    private void compileStatements() {
        out.println(statementsSpace + "      <statements>");
        while (jackTokenizer.keyword().matches("let|if|while|do|return")) {
            if (jackTokenizer.keyword().equals("let")) {
                statementsSpace = statementsSpace.concat("      ");
                compileLet();
                statementsSpace = statementsSpace.replaceFirst("      ", "");
            } else if (jackTokenizer.keyword().equals("if")) {
                statementsSpace = statementsSpace.concat("      ");
                compileIf();
                statementsSpace = statementsSpace.replaceFirst("      ", "");
            } else if (jackTokenizer.keyword().equals("while")) {
                statementsSpace = statementsSpace.concat("      ");
                compileWhile();
                statementsSpace = statementsSpace.replaceFirst("      ", "");
            } else if (jackTokenizer.keyword().equals("do")) {
                statementsSpace = statementsSpace.concat("      ");
                compileDo();
                statementsSpace = statementsSpace.replaceFirst("      ", "");
            } else if (jackTokenizer.keyword().equals("return")) {
                statementsSpace = statementsSpace.concat("      ");
                compileReturn();
                statementsSpace = statementsSpace.replaceFirst("      ", "");
            }
        }
        out.println(statementsSpace + "      </statements>");
    }

    private void compileDo() {
        out.println(statementsSpace + "  <doStatement>");
        eatKeyword(statementsSpace + "    ");                     // eat do
        // compile subroutineCall
        eatIdentifier(statementsSpace + "    ");                  // eat subroutineName | className | varName
        if (jackTokenizer.symbol() == '(') {
            eatSymbol(statementsSpace + "    ");                  // eat '('
            compileExpressionList();
            eatSymbol(statementsSpace + "    ");                  // eat ')'
        } else {
            eatSymbol(statementsSpace + "    ");                  // eat '.'
            eatIdentifier(statementsSpace + "    ");              // eat subroutineName
            eatSymbol(statementsSpace + "    ");                  // eat '('
            compileExpressionList();
            eatSymbol(statementsSpace + "    ");                  // eat ')'
        }
        eatSymbol(statementsSpace + "    ");                      // eat ';'
        out.println(statementsSpace + "  </doStatement>");

    }

    private void compileLet() {
        out.println(statementsSpace + "  <letStatement>");
        eatKeyword(statementsSpace + "    ");                     // eat let
        eatIdentifier(statementsSpace + "    ");                  // eat varName
        if (jackTokenizer.symbol() == '[') {
            eatSymbol(statementsSpace + "    ");                  // eat '['
            compileExpression();
            eatSymbol(statementsSpace + "    ");                  // eat ']'
        }
        eatSymbol(statementsSpace + "    ");                        // eat '='
        compileExpression();
        eatSymbol(statementsSpace + "    ");                        // eat ';'
        out.println(statementsSpace + "  </letStatement>");
    }

    private void compileWhile() {
        out.println(statementsSpace + "  <whileStatement>");
        eatKeyword(statementsSpace + "    ");                     // eat while
        eatSymbol(statementsSpace + "    ");                      // eat '('
        compileExpression();
        eatSymbol(statementsSpace + "    ");                      // eat ')'
        eatSymbol(statementsSpace + "    ");                      // eat '{'
        statementsSpace += "    ";
        compileStatements();
        statementsSpace = statementsSpace.replaceFirst("    ", "");
        eatSymbol(statementsSpace + "    ");                      // eat '}'
        out.println(statementsSpace + "  </whileStatement>");
    }

    private void compileReturn() {
        out.println(statementsSpace + "  <returnStatement>");
        eatKeyword(statementsSpace + "    ");                     // eat return
        if (!((jackTokenizer.tokenType().equals("symbol")) && jackTokenizer.symbol() == ';')) {
            compileExpression();
        }
        eatSymbol(statementsSpace + "    ");                  // eat ';'
        out.println(statementsSpace + "  </returnStatement>");

    }

    private void compileIf() {
        out.println(statementsSpace + "  <ifStatement>");
        eatKeyword(statementsSpace + "    ");                     // eat if
        eatSymbol(statementsSpace + "    ");                      // eat '('
        compileExpression();
        eatSymbol(statementsSpace + "    ");                      // eat ')'
        eatSymbol(statementsSpace + "    ");                      // eat '{'
        statementsSpace += "    ";
        compileStatements();
        statementsSpace = statementsSpace.replaceFirst("    ", "");
        eatSymbol(statementsSpace + "    ");                      // eat '}'
        if (jackTokenizer.keyword().equals("else")) {
            eatKeyword(statementsSpace + "    ");                  // eat else
            eatSymbol(statementsSpace + "    ");                  // eat '{'
            statementsSpace += "    ";
            compileStatements();
            statementsSpace = statementsSpace.replaceFirst("    ", "");
            eatSymbol(statementsSpace + "    ");                  // eat '{'
        }
        out.println(statementsSpace + "  </ifStatement>");
    }

    private void compileExpression() {
        out.println(statementsSpace + "    <expression>");
        compileTerm();
        while (String.valueOf(jackTokenizer.symbol()).matches("\\+|\\-|\\*|/|\\&||\\||<|>|=")) {
            eatSymbol(statementsSpace + "      ");            // eat op
            compileTerm();
        }
        out.println(statementsSpace + "    </expression>");
    }

    private void compileTerm() {
        out.println(statementsSpace + "      <term>");
        if (jackTokenizer.tokenType().equals("integerConstant")) {
            eatIntegerConstant(statementsSpace + "        ");           // eat IntegerConstant
        } else if (jackTokenizer.tokenType().equals("stringConstant")) {
            eatStringConstant(statementsSpace + "        ");            // eat StringConstant
        } else if (jackTokenizer.tokenType().equals("keyword") && jackTokenizer.keyword().matches("true|false|null|this")) {
            eatKeyword(statementsSpace + "        ");                   // eat true|false|null|this
        } else if (jackTokenizer.tokenType().equals("identifier")) {    // maybe varName|varName[expression]|subroutineCall
            eatIdentifier(statementsSpace + "        ");                // eat identifier
            if (jackTokenizer.tokenType().equals("symbol")) {
                if (jackTokenizer.symbol() == '[') {
                    eatSymbol(statementsSpace + "        ");                // eat '['
                    statementsSpace += "    ";
                    compileExpression();
                    statementsSpace = statementsSpace.replaceFirst("    ", "");
                    eatSymbol(statementsSpace + "        ");                // eat ']'
                } else if (jackTokenizer.symbol() == '(') {
                    eatSymbol(statementsSpace + "        ");                  // eat '('
                    statementsSpace += "    ";
                    compileExpressionList();
                    statementsSpace = statementsSpace.replaceFirst("    ", "");
                    eatSymbol(statementsSpace + "        ");                  // eat ')'
                } else if (jackTokenizer.symbol() == '.') {
                    eatSymbol(statementsSpace + "        ");                  // eat '.'
                    eatIdentifier(statementsSpace + "        ");              // eat subroutineName
                    eatSymbol(statementsSpace + "        ");                  // eat '('
                    statementsSpace += "    ";
                    compileExpressionList();
                    statementsSpace = statementsSpace.replaceFirst("    ", "");
                    eatSymbol(statementsSpace + "        ");                  // eat ')'
                }
            }
        } else if (jackTokenizer.tokenType().equals("symbol") && jackTokenizer.symbol() == '(') {
            eatSymbol(statementsSpace + "        ");        // eat '('
            statementsSpace += "    ";
            compileExpression();
            statementsSpace = statementsSpace.replaceFirst("    ", "");
            eatSymbol(statementsSpace + "        ");        // eat ')'
        } else if (jackTokenizer.tokenType().equals("symbol") && (jackTokenizer.symbol() == '-' || jackTokenizer.symbol() == '~')) {
            eatSymbol(statementsSpace + "        ");        // eat -|~
            statementsSpace += "  ";
            compileTerm();
            statementsSpace = statementsSpace.replaceFirst("  ", "");
        } else throw new IllegalAccessError("not a term");
        out.println(statementsSpace + "      </term>");
    }

    private void compileExpressionList() {
        out.println(statementsSpace + "    <expressionList>");
        if (!(jackTokenizer.tokenType().equals("symbol") && jackTokenizer.symbol() == ')')) {
            statementsSpace += "  ";
            compileExpression();
            while (jackTokenizer.symbol() == ',') {
                eatSymbol(statementsSpace + "    ");                // eat ','
                compileExpression();
            }
            statementsSpace = statementsSpace.replaceFirst("    ", "");
        }
        out.println(statementsSpace + "    </expressionList>");
    }


}
