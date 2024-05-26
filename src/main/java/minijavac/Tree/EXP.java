package minijavac.Tree;

public class EXP extends Stm {
    public Expr exp;

    public EXP(Expr e) {
        exp = e;
    }

    public ExprList kids() {
        return new ExprList(exp, null);
    }

    public Stm build(ExprList kids) {
        return new EXP(kids.head);
    }
}
