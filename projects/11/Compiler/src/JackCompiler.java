import java.io.File;
import java.util.ArrayList;

public class JackCompiler {
    private static ArrayList<String> voidSubroutine(String filePath) {
        ArrayList<String> voidSubroutine = new ArrayList<>();
        voidSubroutine.add("String.setCharAt");
        voidSubroutine.add("String.eraseLastChar");
        voidSubroutine.add("String.setInt");
        voidSubroutine.add("Array.dispose");
        voidSubroutine.add("Output.moveCursor");
        voidSubroutine.add("Output.printChar");
        voidSubroutine.add("Output.printString");
        voidSubroutine.add("Output.d printInt");
        voidSubroutine.add("Output.println");
        voidSubroutine.add("Output.backSpace");
        voidSubroutine.add("Screen.clearScreen");
        voidSubroutine.add("Screen.setColor");
        voidSubroutine.add("Screen.drawPixel");
        voidSubroutine.add("Screen.drawLine");
        voidSubroutine.add("Screen.drawRectangle");
        voidSubroutine.add("Screen.drawCircle");
        voidSubroutine.add("Memory.poke");
        voidSubroutine.add("Memory.deAlloc");
        voidSubroutine.add("Sys.halt");
        voidSubroutine.add("Sys.error");
        voidSubroutine.add("Sys.wait");
        if (filePath.contains(".jack")) {
            JackTokenizer jackTokenizer = new JackTokenizer(filePath);
            String className = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.lastIndexOf("."));
            while (jackTokenizer.hasMoreTokens()) {
                jackTokenizer.advance();
                if (jackTokenizer.tokenType().equals("keyword") && jackTokenizer.keyword().equals("void")) {
                    jackTokenizer.advance();
                    voidSubroutine.add(className + "." + jackTokenizer.identifier());
                }
            }
        } else {
            File file = new File(filePath);
            String[] allFileName = file.list();
            int len = 0;
            if (allFileName != null) {
                len = allFileName.length;
            }
            for (int i = 0; i < len; i++) {
                if (allFileName[i].contains(".jack")) {
                    String className = allFileName[i].substring(0, allFileName[i].lastIndexOf("."));
                    JackTokenizer jackTokenizer = new JackTokenizer(filePath + "/" + allFileName[i]);
                    while (jackTokenizer.hasMoreTokens()) {
                        jackTokenizer.advance();
                        if (jackTokenizer.tokenType().equals("keyword") && jackTokenizer.keyword().equals("void")) {
                            jackTokenizer.advance();
                            voidSubroutine.add(className + "." + jackTokenizer.identifier());
                        }
                    }
                }
            }

        }
        return voidSubroutine;
    }

    public static void main(String args[]) {
//        ArrayList<String> voidSubroutine = voidSubroutine(args[0]);
        if (args[0].contains(".jack")) {
//            new CompilationEngine(args[0], voidSubroutine);
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
//                    new CompilationEngine(args[0] + "/" + allFileName[i], voidSubroutine);
                    new CompilationEngine(args[0] + "/" + allFileName[i]);
                }
            }

        }
    }
}
