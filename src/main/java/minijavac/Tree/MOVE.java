package minijavac.Tree;

public class MOVE extends Stm {
    public Expr dst, src;

    public MOVE(Expr d, Expr s) {
        dst = d;
        src = s;
    }

    public ExprList kids() {
        if (dst instanceof MEM)
            return new ExprList(((MEM) dst).exp, new ExprList(src, null));
        else return new ExprList(src, null);
    }

    public Stm build(ExprList kids) {
        if (dst instanceof MEM)
            return new MOVE(new MEM(kids.head), kids.tail.head);
        else return new MOVE(dst, kids.head);
    }
}

