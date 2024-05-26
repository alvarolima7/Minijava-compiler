package minijavac.SemanticAnalysis;

public class SemanticException extends Exception {
    public SemanticException() {
        super("Semantic error(s) occurred.");
    }
}
