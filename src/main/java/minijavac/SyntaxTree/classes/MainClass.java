package minijavac.SyntaxTree.classes;

import minijavac.SyntaxTree.Identifier;
import minijavac.SyntaxTree.statement.Statement;
import minijavac.SyntaxTree.visitor.ASTVisitor;
import minijavac.SyntaxTree.visitor.Visitable;

public class MainClass implements Visitable {
    public Identifier name;
    public Identifier argsName;
    public Statement body;

    public MainClass(Identifier mainClassName, Identifier argsName, Statement mainClassBody) {
        this.name = mainClassName;
        this.argsName = argsName;
        this.body = mainClassBody;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
