package minijavac.SyntaxTree.statement;

import minijavac.SyntaxTree.expression.Expression;
import minijavac.SyntaxTree.visitor.ASTVisitor;

public class While extends Statement {
    public Expression condition;
    public Statement body;

    public While(Expression whileCondition, Statement whileBody) {
        this.condition = whileCondition;
        this.body = whileBody;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
