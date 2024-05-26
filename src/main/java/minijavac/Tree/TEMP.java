package minijavac.Tree;

import minijavac.Temp.Temp;

public class TEMP extends Expr {
    public Temp temp;

    public TEMP(Temp t) {
        temp = t;
    }

    public ExprList kids() {
        return null;
    }

    public Expr build(ExprList kids) {
        return this;
    }
}