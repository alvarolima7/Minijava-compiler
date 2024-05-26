package minijavac.Tree;

abstract public class Expr {
    abstract public ExprList kids();

    abstract public Expr build(ExprList kids);
}
