package minijavac.SyntaxTree.classes;

import minijavac.SyntaxTree.Identifier;
import minijavac.SyntaxTree.expression.Expression;
import minijavac.SyntaxTree.statement.Statement;
import minijavac.SyntaxTree.visitor.ASTVisitor;
import minijavac.SyntaxTree.visitor.Visitable;
import minijavac.SyntaxTree.type.Type;

import java.util.ArrayList;

public class MethodDecl implements Visitable {
    public Type returnType;
    public Identifier name;
    public ArrayList<Formal> params;
    public ArrayList<VarDecl> locals;
    public ArrayList<Statement> statements;
    public Expression returnExp;

    public MethodDecl(Type returnType, Identifier name, ArrayList<Formal> params,
                      ArrayList<VarDecl> vars, ArrayList<Statement> statements, Expression returnExp)
    {
        this.returnType = returnType;
        this.name = name;
        this.params = params;
        this.locals = vars;
        this.statements = statements;
        this.returnExp = returnExp;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
