package minijavac.SyntaxTree.visitor;

public interface Visitable {
    <T> T accept(ASTVisitor<T> visitor);
}
