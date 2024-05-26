package minijavac.SyntaxTree.expression;

import minijavac.SyntaxTree.visitor.ASTVisitor;

public class Not extends Expression {
    public Expression exp;

    public Not(Expression exp) {
        this.exp = exp;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
