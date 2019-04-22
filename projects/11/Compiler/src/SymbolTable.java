import java.util.Enumeration;
import java.util.Hashtable;

class SymbolTable {
    private Hashtable<String, SymbolValue> hashtableClass;
    private Hashtable<String, SymbolValue> hashtableSubroutine;

    SymbolTable() {
        hashtableClass = new Hashtable<>();
        hashtableSubroutine = new Hashtable<>();
    }

    void startSubroutine() {
        hashtableSubroutine.clear();
    }

    void define(String name, String type, String kind) {
        // "static" corresponding "STATIC"
        // "this" corresponding "FIELD"
        if (kind.matches("static|this")) {
            hashtableClass.put(name, new SymbolValue(type, kind, varCount(kind)));
        }
        // "argument" corresponding "ARG"
        // "local" corresponding "VAR"
        else if (kind.matches("argument|local")) {
            hashtableSubroutine.put(name, new SymbolValue(type, kind, varCount(kind)));
        } else throw new IllegalArgumentException("no such kind");
    }

    int varCount(String kind) {

        if (kind.matches("static|this")) {
            int size = hashtableClass.size();
            int staticNum = 0;
            Enumeration<SymbolValue> enumeration = hashtableClass.elements();
            while (enumeration.hasMoreElements()) {
                if (enumeration.nextElement().kind.equals("static")) {
                    staticNum++;
                }
            }
            if (kind.equals("static")) {
                return staticNum;
            } else {
                return size - staticNum;
            }
        } else if (kind.matches("argument|local")) {
            int size = hashtableSubroutine.size();
            int argNum = 0;
            Enumeration<SymbolValue> enumeration = hashtableSubroutine.elements();
            while (enumeration.hasMoreElements()) {
                if (enumeration.nextElement().kind.equals("argument")) {
                    argNum++;
                }
            }
            if (kind.equals("argument")) {
                return argNum;
            } else {
                return size - argNum;
            }
        } else throw new IllegalArgumentException("no such kind");
    }

    String kindOf(String name) {
        if (hashtableClass.containsKey(name)) {
            return hashtableClass.get(name).kind;
        } else if (hashtableSubroutine.containsKey(name)) {
            return hashtableSubroutine.get(name).kind;
        } else return "NONE";
    }

    String typeOf(String name) {
        if (hashtableClass.containsKey(name)) {
            return hashtableClass.get(name).type;
        } else if (hashtableSubroutine.containsKey(name)) {
            return hashtableSubroutine.get(name).type;
        } else throw new IllegalArgumentException("no such variable name");
    }

    int indexOf(String name) {
        if (hashtableClass.containsKey(name)) {
            return hashtableClass.get(name).index;
        } else if (hashtableSubroutine.containsKey(name)) {
            return hashtableSubroutine.get(name).index;
        } else throw new IllegalArgumentException("no such variable name");
    }

    static class SymbolValue {
        private String type;
        private String kind;
        private int index;

        SymbolValue(String type, String kind, int index) {
            this.type = type;
            this.kind = kind;
            this.index = index;
        }
    }
}
