package minijavac.Tree;

public class ESEQ extends Expr {
    public Stm stm;
    public Expr exp;

    public ESEQ(Stm s, Expr e) {
        stm = s;
        exp = e;
    }

    public ExprList kids() {
        throw new Error("kids() not applicable to ESEQ");
    }

    public Expr build(ExprList kids) {
        throw new Error("build() not applicable to ESEQ");
    }
}
