class VMWriter {
    Out out;

    VMWriter(String jackFileName) {
        out = new Out(jackFileName.substring(0, jackFileName.lastIndexOf(".")) + ".vm");
    }

    void writePush(String segment, int index) {
        out.println("push " + segment + " " + index);
    }

    void writePop(String segment, int index) {
        out.println("pop " + segment + " " + index);
    }

    void writeArithmetic(String command) {
        out.println(command);
    }

    void writeLabel(String label) {
        out.println("label " + label);
    }

    void writeGoTo(String label) {
        out.println("goto " + label);
    }

    void writeIf(String label) {
        out.println("if-goto " + label);
    }

    void writeCall(String name, int nArgs) {
        out.println("call " + name + " " + nArgs);
    }

    void writeFunction(String name, int nArgs) {
        out.println("function " + name + " " + nArgs);
    }

    void writeReturn() {
        out.println("return");
    }

    void close() {
        out.close();
    }
}
