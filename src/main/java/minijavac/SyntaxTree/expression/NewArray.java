package minijavac.SyntaxTree.expression;

import minijavac.SyntaxTree.visitor.ASTVisitor;

public class NewArray extends Expression {
    public Expression size;

    public NewArray(Expression size) {
        this.size = size;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
