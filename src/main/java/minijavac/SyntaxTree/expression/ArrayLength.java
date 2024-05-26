package minijavac.SyntaxTree.expression;

import minijavac.SyntaxTree.visitor.ASTVisitor;

public class ArrayLength extends Expression {
    public Expression exp;

    public ArrayLength(Expression exp) {
        this.exp = exp;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
