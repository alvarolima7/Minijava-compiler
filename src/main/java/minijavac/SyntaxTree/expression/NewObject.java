package minijavac.SyntaxTree.expression;

import minijavac.SyntaxTree.Identifier;
import minijavac.SyntaxTree.visitor.ASTVisitor;

public class NewObject extends Expression {
    public Identifier identifier;

    public NewObject(Identifier identifier) {
        this.identifier = identifier;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
