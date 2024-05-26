package minijavac.Tree;

import minijavac.Temp.Label;

public class LABEL extends Stm {
    public Label label;

    public LABEL(Label l) {
        label = l;
    }

    public ExprList kids() {
        return null;
    }

    public Stm build(ExprList kids) {
        return this;
    }
}
