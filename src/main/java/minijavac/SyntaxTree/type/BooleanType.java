package minijavac.SyntaxTree.type;

import minijavac.SyntaxTree.visitor.ASTVisitor;

public class BooleanType extends Type {
    public BooleanType() {}

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "boolean";
    }
}
