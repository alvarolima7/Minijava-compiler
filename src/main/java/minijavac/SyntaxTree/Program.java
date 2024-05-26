package minijavac.SyntaxTree;

import minijavac.SyntaxTree.classes.*;
import minijavac.SyntaxTree.visitor.*;

import java.util.ArrayList;

public class Program implements Visitable {
    public MainClass mainClass;
    public ArrayList<ClassDecl> classesDeclaration;

    public Program(MainClass mainClass, ArrayList<ClassDecl> classDeclList) {
        this.mainClass = mainClass;
        this.classesDeclaration = classDeclList;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
