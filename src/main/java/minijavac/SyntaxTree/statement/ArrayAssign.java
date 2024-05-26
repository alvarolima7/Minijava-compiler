package minijavac.SyntaxTree.statement;

import minijavac.SyntaxTree.Identifier;
import minijavac.SyntaxTree.expression.Expression;
import minijavac.SyntaxTree.visitor.ASTVisitor;

public class ArrayAssign extends Statement {
    public Identifier arrayName;
    public Expression index;
    public Expression value;

    public ArrayAssign(Identifier id, Expression index, Expression value) {
        this.arrayName = id;
        this.index = index;
        this.value = value;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
