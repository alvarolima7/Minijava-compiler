package minijavac.Tree;

public class SEQ extends Stm {
    public Stm left, right;

    public SEQ(Stm l, Stm r) {
        left = l;
        right = r;
    }

    public ExprList kids() {
        throw new Error("kids() not applicable to SEQ");
    }

    public Stm build(ExprList kids) {
        throw new Error("build() not applicable to SEQ");
    }
}
