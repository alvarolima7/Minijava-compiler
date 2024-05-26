package minijavac.Tree;

import minijavac.Temp.Label;
import minijavac.Temp.LabelList;

public class JUMP extends Stm {
    public Expr exp;
    public LabelList targets;

    public JUMP(Expr e, LabelList t) {
        exp = e;
        targets = t;
    }

    public JUMP(Label target) {
        this(new NAME(target), new LabelList(target, null));
    }

    public ExprList kids() {
        return new ExprList(exp, null);
    }

    public Stm build(ExprList kids) {
        return new JUMP(kids.head, targets);
    }
}