package minijavac.SemanticAnalysis;

import minijavac.SyntaxTree.Program;
import minijavac.SyntaxTree.classes.*;
import minijavac.SyntaxTree.type.Type;

import java.util.HashMap;

public class SymbolTable {
    public HashMap<Symbol, ClassInfo> classes;

    public SymbolTable(Program program) {
        classes = new HashMap<>();

        program.classesDeclaration.forEach(classDecl -> {
            Symbol className = Symbol.symbol(classDecl instanceof ClassDeclSimple
                    ? ((ClassDeclSimple) classDecl).name.toString()
                    : ((ClassDeclExtends) classDecl).name.toString());
            if (classes.containsKey(className))
                Error.alreadyDeclaredClass(className.toString());
            else
                classes.put(className, new ClassInfo(classDecl));
        });

        program.classesDeclaration
                .stream()
                .filter(classDecl -> classDecl instanceof ClassDeclExtends)
                .map(classDecl -> (ClassDeclExtends) classDecl)
                .forEach(classDecl -> {
                    Symbol className = Symbol.symbol(classDecl.name.toString());
                    ClassInfo classInfo = classes.get(className);
                    ClassInfo parentInfo = classes.get(Symbol.symbol(classDecl.superClassName.toString()));
                    if (parentInfo == null)
                        Error.undefinedClass(classDecl.superClassName.toString());
                    else
                        classInfo.setParent(parentInfo);
                });
    }

    public ClassInfo get(Symbol className) {
        return classes.get(className);
    }

    public boolean hasType(Type type) {
        return classes.containsKey(Symbol.symbol(type.toString()));
    }

}
