package minijavac.Frame;

import minijavac.Tree.Expr;

public abstract class Access {
    public abstract String toString();

    public abstract Expr exp(Expr e);
}