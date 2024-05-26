package minijavac.SyntaxTree.type;

import minijavac.SyntaxTree.visitor.Visitable;

public abstract class Type implements Visitable {
    public abstract String toString();

    public boolean equals(Type other) {
        return switch (other) {
            case IdentifierType identifierType -> this instanceof IdentifierType &&
                    identifierType.toString().equals(this.toString());
            default -> this.getClass() == other.getClass();
        };
    }
}
