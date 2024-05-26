package minijavac.SyntaxTree.classes;

import minijavac.SyntaxTree.Identifier;
import minijavac.SyntaxTree.visitor.ASTVisitor;

import java.util.ArrayList;

public class ClassDeclExtends extends ClassDecl {
    public Identifier name;
    public Identifier superClassName;
    public ArrayList<VarDecl> fields;
    public ArrayList<MethodDecl> methods;

    public ClassDeclExtends(Identifier name, Identifier superClassName, ArrayList<VarDecl> fields,
                            ArrayList<MethodDecl> methods)
    {
        this.name = name;
        this.superClassName = superClassName;
        this.fields = fields;
        this.methods = methods;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
