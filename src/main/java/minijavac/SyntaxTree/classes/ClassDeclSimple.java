package minijavac.SyntaxTree.classes;

import minijavac.SyntaxTree.Identifier;
import minijavac.SyntaxTree.visitor.ASTVisitor;

import java.util.ArrayList;

public class ClassDeclSimple extends ClassDecl {
    public Identifier name;
    public ArrayList<VarDecl> fields;
    public ArrayList<MethodDecl> methods;

    public ClassDeclSimple(Identifier name, ArrayList<VarDecl> fields, ArrayList<MethodDecl> methods) {
        this.name = name;
        this.fields = fields;
        this.methods = methods;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
