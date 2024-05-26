package minijavac.Tree;

public class MEM extends Expr {
    public Expr exp;

    public MEM(Expr e) {
        exp = e;
    }

    public ExprList kids() {
        return new ExprList(exp, null);
    }

    public Expr build(ExprList kids) {
        return new MEM(kids.head);
    }
}

