package minijavac.Tree;

public class CONST extends Expr {
    public int value;

    public CONST(int v) {
        value = v;
    }

    public ExprList kids() {
        return null;
    }

    public Expr build(ExprList kids) {
        return this;
    }
}
