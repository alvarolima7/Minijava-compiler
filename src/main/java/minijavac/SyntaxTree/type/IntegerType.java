package minijavac.SyntaxTree.type;

import minijavac.SyntaxTree.visitor.ASTVisitor;

public class IntegerType extends Type {
    public IntegerType() {}

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "int";
    }
}
