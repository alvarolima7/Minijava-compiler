package minijavac.SyntaxTree.statement;

import minijavac.SyntaxTree.Identifier;
import minijavac.SyntaxTree.expression.Expression;
import minijavac.SyntaxTree.visitor.ASTVisitor;

public class Assign extends Statement {
    public Identifier varName;
    public Expression value;

    public Assign(Identifier varName, Expression value) {
        this.varName = varName;
        this.value = value;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
