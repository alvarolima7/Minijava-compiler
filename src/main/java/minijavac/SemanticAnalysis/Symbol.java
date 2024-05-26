package minijavac.SemanticAnalysis;

import java.util.HashMap;

public class Symbol {
    String name;
    static HashMap<String, Symbol> symbols = new HashMap<>();

    Symbol(String name) {
        this.name = name;
    }

    public static Symbol symbol(String name) {
        String u = name.intern();

        return symbols.computeIfAbsent(u, Symbol::new);
    }

    public static Symbol get(String name) {
        return symbols.get(name);
    }

    public String toString() {
        return name;
    }
}
