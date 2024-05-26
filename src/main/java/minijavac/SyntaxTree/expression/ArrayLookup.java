package minijavac.SyntaxTree.expression;

import minijavac.SyntaxTree.visitor.ASTVisitor;

public class ArrayLookup extends Expression {
    public Expression array, index;

    public ArrayLookup(Expression array, Expression index) {
        this.array = array;
        this.index = index;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
