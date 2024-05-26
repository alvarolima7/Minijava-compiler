package minijavac.SyntaxTree.expression;

import minijavac.SyntaxTree.Identifier;
import minijavac.SyntaxTree.visitor.ASTVisitor;

import java.util.ArrayList;

public class MethodCall extends Expression {
    public Expression caller;
    public Identifier methodName;
    public ArrayList<Expression> args;

    public MethodCall(Expression caller, Identifier methodName, ArrayList<Expression> args) {
        this.caller = caller;
        this.methodName = methodName;
        this.args = args;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
