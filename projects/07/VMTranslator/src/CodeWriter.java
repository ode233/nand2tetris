
public class CodeWriter {
    private Out out;
    private String vmFileName;
    private int specil = 0;

    public CodeWriter(String vm) {
        out = new Out(vm + ".asm");
        vmFileName = vm;
        if (vmFileName.contains("/")) {
            vmFileName = vmFileName.substring(vmFileName.lastIndexOf('/') + 1);
        }
    }

    public void writeArithmetic(String command) {
        out.println("// " + command);
        if (command.equals("add")) {
            out.println("@SP");
            out.println("M=M-1");
            out.println("A=M");
            out.println("D=M");
            out.println("A=A-1");
            out.println("D=D+M");
            out.println("M=D");
        } else if (command.equals("sub")) {
            out.println("@SP");
            out.println("M=M-1");
            out.println("A=M");
            out.println("D=M");
            out.println("A=A-1");
            out.println("D=M-D");
            out.println("M=D");
        } else if (command.equals("neg")) {
            out.println("@SP");
            out.println("A=M-1");
            out.println("M=-M");
        } else if (command.equals("eq")) {
            String eq = "EQ_" + specil;
            String eqEnd = "EQ_END_" + specil;
            out.println("@SP");
            out.println("M=M-1");
            out.println("A=M");
            out.println("D=M");
            out.println("A=A-1");
            out.println("D=M-D");
            out.println("M=0");
            out.println("@" + eq);
            out.println("D;JEQ");
            out.println("@" + eqEnd);
            out.println("0;JEQ");
            out.println("(" + eq + ")");
            out.println("@SP");
            out.println("A=M-1");
            out.println("M=-1");
            out.println("(" + eqEnd + ")");
        } else if (command.equals("gt")) {
            String gt = "GT_" + specil;
            String gtEnd = "GT_END_" + specil;
            out.println("@SP");
            out.println("M=M-1");
            out.println("A=M");
            out.println("D=M");
            out.println("A=A-1");
            out.println("D=M-D");
            out.println("M=0");
            out.println("@" + gt);
            out.println("D;JGT");
            out.println("@" + gtEnd);
            out.println("0;JEQ");
            out.println("(" + gt + ")");
            out.println("@SP");
            out.println("A=M-1");
            out.println("M=-1");
            out.println("(" + gtEnd + ")");
        } else if (command.equals("lt")) {
            String lt = "LT_" + specil;
            String ltEnd = "LT_END_" + specil;
            out.println("@SP");
            out.println("M=M-1");
            out.println("A=M");
            out.println("D=M");
            out.println("A=A-1");
            out.println("D=M-D");
            out.println("M=0");
            out.println("@" + lt);
            out.println("D;JLT");
            out.println("@" + ltEnd);
            out.println("0;JEQ");
            out.println("(" + lt + ")");
            out.println("@SP");
            out.println("A=M-1");
            out.println("M=-1");
            out.println("(" + ltEnd + ")");
        } else if (command.equals("and")) {
            out.println("@SP");
            out.println("M=M-1");
            out.println("A=M");
            out.println("D=M");
            out.println("A=A-1");
            out.println("D=D&M");
            out.println("M=D");
        } else if (command.equals("or")) {
            out.println("@SP");
            out.println("M=M-1");
            out.println("A=M");
            out.println("D=M");
            out.println("A=A-1");
            out.println("D=D|M");
            out.println("M=D");
        } else if (command.equals("not")) {
            out.println("@SP");
            out.println("A=M-1");
            out.println("M=!M");
        } else throw new IllegalArgumentException("it's not a Arithmetic command");
        specil++;
    }

    public void writePushPop(String type, String segment, int index) {
        out.println("// " + type + " " + segment + " " + index);
        if (type.equals("C_PUSH")) {
            if (segment.equals("local")) {
                out.println("@" + index);
                out.println("D=A");
                out.println("@LCL");
                out.println("A=D+M");
                out.println("D=M");
                out.println("@SP");
                out.println("M=M+1");
                out.println("A=M-1");
                out.println("M=D");
            } else if (segment.equals("argument")) {
                out.println("@" + index);
                out.println("D=A");
                out.println("@ARG");
                out.println("A=D+M");
                out.println("D=M");
                out.println("@SP");
                out.println("M=M+1");
                out.println("A=M-1");
                out.println("M=D");
            } else if (segment.equals("this")) {
                out.println("@" + index);
                out.println("D=A");
                out.println("@THIS");
                out.println("A=D+M");
                out.println("D=M");
                out.println("@SP");
                out.println("M=M+1");
                out.println("A=M-1");
                out.println("M=D");
            } else if (segment.equals("that")) {
                out.println("@" + index);
                out.println("D=A");
                out.println("@THAT");
                out.println("A=D+M");
                out.println("D=M");
                out.println("@SP");
                out.println("M=M+1");
                out.println("A=M-1");
                out.println("M=D");
            } else if (segment.equals("constant")) {
                out.println("@" + index);
                out.println("D=A");
                out.println("@SP");
                out.println("M=M+1");
                out.println("A=M-1");
                out.println("M=D");
            } else if (segment.equals("static")) {
                out.println("@" + vmFileName + "." + index);
                out.println("D=M");
                out.println("@SP");
                out.println("M=M+1");
                out.println("A=M-1");
                out.println("M=D");
            } else if (segment.equals("temp")) {
                out.println("@" + (index + 5));
                out.println("D=M");
                out.println("@SP");
                out.println("M=M+1");
                out.println("A=M-1");
                out.println("M=D");
            } else if (segment.equals("pointer")) {
                if (index == 0) {
                    out.println("@THIS");
                    out.println("D=M");
                    out.println("@SP");
                    out.println("M=M+1");
                    out.println("A=M-1");
                    out.println("M=D");
                } else if (index == 1) {
                    out.println("@THAT");
                    out.println("D=M");
                    out.println("@SP");
                    out.println("M=M+1");
                    out.println("A=M-1");
                    out.println("M=D");
                } else throw new IllegalArgumentException("index must be 0 or 1");
            } else throw new IllegalArgumentException("segment is exception");
        } else if (type.equals("C_POP")) {
            if (segment.equals("local")) {
                out.println("@" + index);
                out.println("D=A");
                out.println("@LCL");
                out.println("D=D+M");
                out.println("@temp");
                out.println("M=D");
                out.println("@SP");
                out.println("M=M-1");
                out.println("A=M");
                out.println("D=M");
                out.println("@temp");
                out.println("A=M");
                out.println("M=D");
            } else if (segment.equals("argument")) {
                out.println("@" + index);
                out.println("D=A");
                out.println("@ARG");
                out.println("D=D+M");
                out.println("@temp");
                out.println("M=D");
                out.println("@SP");
                out.println("M=M-1");
                out.println("A=M");
                out.println("D=M");
                out.println("@temp");
                out.println("A=M");
                out.println("M=D");
            } else if (segment.equals("this")) {
                out.println("@" + index);
                out.println("D=A");
                out.println("@THIS");
                out.println("D=D+M");
                out.println("@temp");
                out.println("M=D");
                out.println("@SP");
                out.println("M=M-1");
                out.println("A=M");
                out.println("D=M");
                out.println("@temp");
                out.println("A=M");
                out.println("M=D");
            } else if (segment.equals("that")) {
                out.println("@" + index);
                out.println("D=A");
                out.println("@THAT");
                out.println("D=D+M");
                out.println("@temp");
                out.println("M=D");
                out.println("@SP");
                out.println("M=M-1");
                out.println("A=M");
                out.println("D=M");
                out.println("@temp");
                out.println("A=M");
                out.println("M=D");
            } else if (segment.equals("static")) {
                out.println("@SP");
                out.println("M=M-1");
                out.println("A=M");
                out.println("D=M");
                out.println("@" + vmFileName + "." + index);
                out.println("M=D");
            } else if (segment.equals("temp")) {
                out.println("@SP");
                out.println("M=M-1");
                out.println("A=M");
                out.println("D=M");
                out.println("@" + (index + 5));
                out.println("M=D");
            } else if (segment.equals("pointer")) {
                if (index == 0) {
                    out.println("@SP");
                    out.println("M=M-1");
                    out.println("A=M");
                    out.println("D=M");
                    out.println("@THIS");
                    out.println("M=D");
                } else if (index == 1) {
                    out.println("@SP");
                    out.println("M=M-1");
                    out.println("A=M");
                    out.println("D=M");
                    out.println("@THAT");
                    out.println("M=D");
                } else throw new IllegalArgumentException("index must be 0 or 1");
            } else throw new IllegalArgumentException("segment is exception");
        } else throw new IllegalArgumentException("type is exception");
    }

    public void close() {
        out.close();
    }
}
