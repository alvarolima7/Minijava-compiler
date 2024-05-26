package minijavac.SyntaxTree.statement;

import minijavac.SyntaxTree.visitor.ASTVisitor;

import java.util.ArrayList;

public class Block extends Statement {
    public ArrayList<Statement> statements;

    public Block(ArrayList<Statement> stmts) {
        this.statements = stmts;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
