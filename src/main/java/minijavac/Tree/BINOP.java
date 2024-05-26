package minijavac.Tree;

public class BINOP extends Expr {
    public int binop;
    public Expr left, right;

    public BINOP(int b, Expr l, Expr r) {
        binop = b;
        left = l;
        right = r;
    }

    public final static int PLUS = 0, MINUS = 1, MUL = 2, DIV = 3,
            AND = 4, OR = 5, LSHIFT = 6, RSHIFT = 7, ARSHIFT = 8, XOR = 9;

    public ExprList kids() {
        return new ExprList(left, new ExprList(right, null));
    }

    public Expr build(ExprList kids) {
        return new BINOP(binop, kids.head, kids.tail.head);
    }
}
