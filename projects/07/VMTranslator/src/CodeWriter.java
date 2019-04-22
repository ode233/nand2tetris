
public class CodeWriter {
    Out out;
    private String vmFileName;
    private String functionName = "Bootstrap";
    private int returnNum = 0;
    private int special = 0;

    public CodeWriter(String vm) {
        out = new Out(vm + ".asm");
        vmFileName = vm;
        if (vmFileName.contains("/")) {
            vmFileName = vmFileName.substring(vmFileName.lastIndexOf('/') + 1);
        }
    }

    public void writeArithmetic(String command) {
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
            String eq = "EQ_" + special;
            String eqEnd = "EQ_END_" + special;
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
            String gt = "GT_" + special;
            String gtEnd = "GT_END_" + special;
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
            String lt = "LT_" + special;
            String ltEnd = "LT_END_" + special;
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
        special++;
    }

    public void writePushPop(String type, String segment, int index) {
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

    public void setFileName(String fileName) {
        vmFileName = fileName;
        if (vmFileName.contains("/")) {
            vmFileName = vmFileName.substring(vmFileName.lastIndexOf('/') + 1);
        }
    }

    public void writeInit() {
        out.println("// init");
        out.println("@256");
        out.println("D=A");
        out.println("@SP");
        out.println("M=D");
        writeCall("Sys.init", 0);
    }

    public void writeLabel(String label) {
        out.println("(" + functionName + "$" + label + ")");
    }

    public void writeGoTo(String label) {
        out.println("@" + functionName + "$" + label);
        out.println("0;JMP");
    }

    public void writeIf(String label) {
        out.println("@SP");
        out.println("M=M-1");
        out.println("A=M");
        out.println("D=M");
        out.println("@" + functionName + "$" + label);
        out.println("D;JNE");
    }

    public void writeFunction(String functionName, int numVars) {
        this.functionName = functionName;
        returnNum = 0;
        out.println("(" + functionName + ")");
        for (int i = 0; i < numVars; i++) {
            writePushPop("C_PUSH", "constant", 0);
        }
    }

    public void writeCall(String callFunctionName, int numArgs) {
        // push returnAddress
        String functionReturn = functionName + "$ret." + returnNum;
        out.println("@" + functionReturn);
        out.println("D=A");
        out.println("@SP");
        out.println("A=M");
        out.println("M=D");
        // push LCL
        out.println("@LCL");
        out.println("D=M");
        out.println("@SP");
        out.println("M=M+1");
        out.println("A=M");
        out.println("M=D");
        // push ARG
        out.println("@ARG");
        out.println("D=M");
        out.println("@SP");
        out.println("M=M+1");
        out.println("A=M");
        out.println("M=D");
        // push THIS
        out.println("@THIS");
        out.println("D=M");
        out.println("@SP");
        out.println("M=M+1");
        out.println("A=M");
        out.println("M=D");
        // push THAT
        out.println("@THAT");
        out.println("D=M");
        out.println("@SP");
        out.println("M=M+1");
        out.println("A=M");
        out.println("M=D");
        out.println("@SP");
        out.println("M=M+1");
        // ARG=SP-5-nArgs
        out.println("@5");
        out.println("D=A");
        out.println("@SP");
        out.println("D=M-D");
        out.println("@" + numArgs);
        out.println("D=D-A");
        out.println("@ARG");
        out.println("M=D");
        // LCL=SP
        out.println("@SP");
        out.println("D=M");
        out.println("@LCL");
        out.println("M=D");
        // goto functionName
        out.println("@" + callFunctionName);
        out.println("0;JMP");
        // (returnAddress)
        out.println("(" + functionReturn + ")");
        returnNum++;
    }

    public void writeReturn() {
        // endFrame=LCL,endFrame is a temporary variable
        // retAddr=*(endFrame-5)
        out.println("@LCL");
        out.println("D=M");
        out.println("@5");
        out.println("A=D-A");
        out.println("D=M");
        out.println("@temp");                     // retAddr store in temp
        out.println("M=D");
        // *ARG=pop()
        out.println("@SP");
        out.println("A=M-1");
        out.println("D=M");
        out.println("@ARG");
        out.println("A=M");
        out.println("M=D");
        // SP=ARG+1
        out.println("@ARG");
        out.println("D=M+1");
        out.println("@SP");
        out.println("M=D");
        // THAT=*(endFrame-1)
        out.println("@LCL");
        out.println("A=M-1");
        out.println("D=M");
        out.println("@THAT");
        out.println("M=D");
        // THIS=*(endFrame-2)
        out.println("@LCL");
        out.println("D=M");
        out.println("@2");
        out.println("A=D-A");
        out.println("D=M");
        out.println("@THIS");
        out.println("M=D");
        // ARG=*(endFrame-3)
        out.println("@LCL");
        out.println("D=M");
        out.println("@3");
        out.println("A=D-A");
        out.println("D=M");
        out.println("@ARG");
        out.println("M=D");
        // LCL=*(endFrame-4)
        out.println("@LCL");
        out.println("D=M");
        out.println("@4");
        out.println("A=D-A");
        out.println("D=M");
        out.println("@LCL");
        out.println("M=D");
        // goto retAddr
        out.println("@temp");
        out.println("A=M");
        out.println("0;JMP");
    }

    public void close() {
        out.close();
    }
}
