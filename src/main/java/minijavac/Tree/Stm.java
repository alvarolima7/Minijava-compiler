package minijavac.Tree;

abstract public class Stm {
    abstract public ExprList kids();

    abstract public Stm build(ExprList kids);
}
