package minijavac.SyntaxTree.type;

import minijavac.SyntaxTree.Identifier;
import minijavac.SyntaxTree.visitor.ASTVisitor;

public class IdentifierType extends Type {
    public Identifier typename;

    public IdentifierType(String typename) {
        this.typename = new Identifier(typename);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return typename.toString();
    }
}
