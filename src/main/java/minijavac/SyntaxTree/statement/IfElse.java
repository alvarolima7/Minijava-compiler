package minijavac.SyntaxTree.statement;

import minijavac.SyntaxTree.expression.Expression;
import minijavac.SyntaxTree.visitor.ASTVisitor;

public class IfElse extends Statement {
    public Expression condition;
    public Statement thenStmt;
    public Statement elseStmt;


    public IfElse(Expression ifCondition, Statement thenStmt, Statement elseStmt) {
        this.condition = ifCondition;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
