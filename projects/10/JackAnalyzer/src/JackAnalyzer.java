import java.io.File;

public class JackAnalyzer {

    private static void writeToken(String jackName) {
        Out out = new Out(jackName.substring(0, jackName.lastIndexOf(".")) + "T.xml");
        JackTokenizer jackTokenizer = new JackTokenizer(jackName);
        out.println("<tokens>");
        while (jackTokenizer.hasMoreTokens()) {
            jackTokenizer.advance();
            if (jackTokenizer.tokenType().equals("keyword")) {
                out.println("<keyword> " + jackTokenizer.keyword() + " </keyword>");
            } else if (jackTokenizer.tokenType().equals("symbol")) {
                if (jackTokenizer.symbol() == '<') {
                    out.println("<symbol> &lt; </symbol>");
                } else if (jackTokenizer.symbol() == '>') {
                    out.println("<symbol> &gt; </symbol>");
                } else if (jackTokenizer.symbol() == '&') {
                    out.println("<symbol> &amp; </symbol>");
                } else out.println("<symbol> " + jackTokenizer.symbol() + " </symbol>");
            } else if (jackTokenizer.tokenType().equals("integerConstant")) {
                out.println("<integerConstant> " + jackTokenizer.intVal() + " </integerConstant>");
            } else if (jackTokenizer.tokenType().equals("stringConstant")) {
                out.println("<stringConstant> " + jackTokenizer.stringVal() + " </stringConstant>");
            } else if (jackTokenizer.tokenType().equals("identifier")) {
                out.println("<identifier> " + jackTokenizer.identifier() + " </identifier>");
            } else throw new IllegalAccessError("condition wrong");
        }
        out.println("</tokens>");
        out.close();
    }

    public static void main(String[] args) {
        if (args[0].contains(".jack")) {
            // generate tXml
            writeToken(args[0]);
            // generate xml
            new CompilationEngine(args[0]);
        } else {
            File file = new File(args[0]);
            String[] allFileName = file.list();
            int len = 0;
            if (allFileName != null) {
                len = allFileName.length;
            }
            for (int i = 0; i < len; i++) {
                if (allFileName[i].contains(".jack")) {
                    // generate tXml
                    //writeToken(args[0] + "/" + allFileName[i]);
                    // generate xml
                    new CompilationEngine(args[0] + "/" + allFileName[i]);
                }
            }
        }
    }
}
