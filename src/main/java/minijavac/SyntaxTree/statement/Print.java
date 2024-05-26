package minijavac.SyntaxTree.statement;

import minijavac.SyntaxTree.expression.Expression;
import minijavac.SyntaxTree.visitor.ASTVisitor;

public class Print extends Statement {
    public Expression exp;

    public Print(Expression exp) {
        this.exp = exp;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
