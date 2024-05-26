package minijavac.SemanticAnalysis;

import minijavac.SyntaxTree.classes.*;
import minijavac.SyntaxTree.type.Type;

import java.util.LinkedHashMap;
import java.util.Optional;

public class ClassInfo {
    public Symbol name;
    public Optional<ClassInfo> parent = Optional.empty();
    public LinkedHashMap<Symbol, Type> fields = new LinkedHashMap<>();
    public LinkedHashMap<Symbol, MethodInfo> methods = new LinkedHashMap<>();

    public ClassInfo() {
    }

    public ClassInfo(ClassDecl classDecl) {
        if (classDecl instanceof ClassDeclSimple)
            setSimpleClass((ClassDeclSimple) classDecl);
        else
            setExtendsClass((ClassDeclExtends) classDecl);
    }

    private void setSimpleClass(ClassDeclSimple classDeclSimple) {
        name = Symbol.symbol(classDeclSimple.name.toString());

        classDeclSimple.fields.forEach(field -> {
            Symbol fieldName = Symbol.symbol(field.name.toString());
            if (fields.containsKey(fieldName))
                Error.alreadyDeclaredField(name.toString(), fieldName.toString());
            else
                fields.put(fieldName, field.type);
        });

        classDeclSimple.methods.forEach(method -> {
            MethodInfo methodInfo = new MethodInfo(method);
            if (methods.containsKey(methodInfo.name))
                Error.alreadyDeclaredMethod(name.toString(), methodInfo.name.toString());
            else {
                methodInfo.addToContext(fields);
                methods.put(methodInfo.name, methodInfo);
            }
        });
    }

    private void setExtendsClass(ClassDeclExtends classDeclExtends) {
        name = Symbol.symbol(classDeclExtends.name.toString());

        classDeclExtends.fields.forEach(field -> {
            Symbol fieldName = Symbol.symbol(field.name.toString());
            if (fields.containsKey(fieldName))
                Error.alreadyDeclaredField(name.toString(), fieldName.toString());
            else
                fields.put(fieldName, field.type);
        });

        classDeclExtends.methods.forEach(method -> {
            MethodInfo methodInfo = new MethodInfo(method);
            if (methods.containsKey(methodInfo.name))
                Error.alreadyDeclaredMethod(name.toString(), methodInfo.name.toString());
            else {
                methodInfo.addToContext(fields);
                methods.put(methodInfo.name, methodInfo);
            }
        });
    }

    public void setParent(ClassInfo parent) {
        this.parent = Optional.of(parent);

        parent.fields.forEach((fieldName, fieldType) -> {
            if (fields.containsKey(fieldName))
                Error.alreadyDeclaredField(name.toString(), fieldName.toString());
            else
                fields.put(fieldName, fieldType);
        });

        parent.methods.forEach((methodName, methodInfo) -> methods.putIfAbsent(methodName, methodInfo));
        methods.forEach((methodName, methodInfo) -> methodInfo.addToContext(parent.fields));
    }

    public boolean isSubclassOf(ClassInfo other) {
        if (parent.isEmpty())
            return false;
        if (parent.get().name.equals(other.name))
            return true;

        return parent.get().isSubclassOf(other);
    }
}
