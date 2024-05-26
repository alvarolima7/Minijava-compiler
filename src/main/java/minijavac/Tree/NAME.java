package minijavac.Tree;

import minijavac.Temp.Label;

public class NAME extends Expr {
    public Label label;

    public NAME(Label l) {
        label = l;
    }

    public ExprList kids() {
        return null;
    }

    public Expr build(ExprList kids) {
        return this;
    }
}
