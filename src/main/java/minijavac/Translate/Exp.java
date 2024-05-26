package minijavac.Translate;

import minijavac.Tree.*;

public class Exp {
    Expr expr;

    public Exp(Expr expr) {
        this.expr = expr;
    }

    public Expr unEx() {
        return expr;
    }

    public Stm unNx() {
        return ((ESEQ) expr).stm;
    }
}
