package minijavac.SyntaxTree;

import minijavac.SyntaxTree.visitor.*;

public class Identifier implements Visitable {
    String id;

    public Identifier(String id) {
        this.id = id;
    }

    public String toString() {
        return id;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}