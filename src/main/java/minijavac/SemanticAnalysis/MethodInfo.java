package minijavac.SemanticAnalysis;

import minijavac.SyntaxTree.classes.*;
import minijavac.SyntaxTree.type.Type;

import java.util.LinkedHashMap;

public class MethodInfo {
    public Symbol name;
    public Type returnType;
    public LinkedHashMap<Symbol, Type> params = new LinkedHashMap<>();
    public LinkedHashMap<Symbol, Type> locals = new LinkedHashMap<>();
    public LinkedHashMap<Symbol, Type> context = new LinkedHashMap<>();

    public MethodInfo() {
    }

    public MethodInfo(MethodDecl methodDecl) {
        name = Symbol.symbol(methodDecl.name.toString());
        returnType = methodDecl.returnType;

        methodDecl.params.forEach(param -> {
            Symbol paramName = Symbol.symbol(param.name.toString());
            if (params.containsKey(paramName))
                Error.alreadyDeclaredParameter(name.toString(), paramName.toString());
            else
                params.put(paramName, param.type);
        });

        methodDecl.locals.forEach(local -> {
            Symbol localName = Symbol.symbol(local.name.toString());
            if (params.containsKey(localName) || locals.containsKey(localName))
                Error.alreadyDeclaredLocal(name.toString(), localName.toString());
            else
                locals.put(localName, local.type);
        });

        addToContext(params);
        addToContext(locals);
    }

    public void addToContext(LinkedHashMap<Symbol, Type> vars) {
        vars.forEach((varName, varType) -> {
            if (context.containsKey(varName))
                Error.alreadyDeclaredField(name.toString(), varName.toString());
            else
                context.put(varName, varType);
        });
    }
}
