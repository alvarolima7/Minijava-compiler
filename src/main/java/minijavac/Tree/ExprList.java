package minijavac.Tree;

import java.util.List;

public class ExprList {
    public Expr head;
    public ExprList tail;

    public ExprList(List<Expr> exprs) {
        head = exprs.isEmpty() ? null : exprs.getFirst();
        tail = exprs.size() < 2 ? null : new ExprList(exprs.get(1), null);

        if (exprs.size() < 2)
            return;

        tail = new ExprList(exprs.getLast(), null);
        exprs
                .stream()
                .skip(2)
                .map(expr -> new ExprList(expr, null))
                .reduce(tail, (acc, exprList) -> {
                    acc.tail = exprList;
                    return exprList;
                });
    }

    public ExprList(Expr h, ExprList t) {
        head = h;
        tail = t;
    }
}


