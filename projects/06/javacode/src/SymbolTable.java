import edu.princeton.cs.algs4.ST;

public class SymbolTable {
    private ST<String, Integer> st;

    public SymbolTable() {
        st = new ST<String, Integer>();
        st.put("SP", 0);
        st.put("LCL", 1);
        st.put("ARG", 2);
        st.put("THIS", 3);
        st.put("THAT", 4);
        st.put("R0", 0);
        st.put("R1", 1);
        st.put("R2", 2);
        st.put("R3", 3);
        st.put("R4", 4);
        st.put("R5", 5);
        st.put("R6", 6);
        st.put("R7", 7);
        st.put("R8", 8);
        st.put("R9", 9);
        st.put("R10", 10);
        st.put("R11", 11);
        st.put("R12", 12);
        st.put("R13", 13);
        st.put("R14", 14);
        st.put("R15", 15);
        st.put("SCREEN", 16384);
        st.put("KBD", 24576);
    }

    public void addEntry(String symbol, Integer address) {
        st.put(symbol, address);
    }

    public boolean contains(String symbol) {
        return st.contains(symbol);
    }

    public Integer getAddress(String symbol) {
        return st.get(symbol);
    }
}
