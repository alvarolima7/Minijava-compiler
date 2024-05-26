package minijavac.Tree;

import java.util.List;

public class CALL extends Expr {
    public Expr func;
    public ExprList args;

    public CALL(Expr f, ExprList a) {
        func = f;
        args = a;
    }

    public CALL(Expr f, List<Expr> a) {
        if (a == null || a.isEmpty())
            return;

        func = f;
        args = new ExprList(a);
    }

    public ExprList kids() {
        return new ExprList(func, args);
    }

    public Expr build(ExprList kids) {
        return new CALL(kids.head, kids.tail);
    }

}