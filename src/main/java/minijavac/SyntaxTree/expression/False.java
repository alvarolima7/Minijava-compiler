package minijavac.SyntaxTree.expression;

import minijavac.SyntaxTree.visitor.ASTVisitor;

public class False extends Expression {
    public False() {}

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
