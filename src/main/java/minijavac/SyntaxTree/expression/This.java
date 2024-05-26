package minijavac.SyntaxTree.expression;

import minijavac.SyntaxTree.visitor.ASTVisitor;

public class This extends Expression {
    public This() {}

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
