package minijavac.SemanticAnalysis;

import minijavac.SyntaxTree.*;
import minijavac.SyntaxTree.classes.*;
import minijavac.SyntaxTree.expression.*;
import minijavac.SyntaxTree.statement.*;
import minijavac.SyntaxTree.type.*;
import minijavac.SyntaxTree.visitor.ASTVisitor;

import java.util.Map;

public class TypeCheckVisitor implements ASTVisitor<Type> {
    public SymbolTable symbolTable;
    public ClassInfo currClass;
    public MethodInfo currMethod;

    public TypeCheckVisitor(Program program) {
        symbolTable = new SymbolTable(program);

        symbolTable.classes.put(Symbol.symbol("int"), new ClassInfo());
        symbolTable.classes.put(Symbol.symbol("boolean"), new ClassInfo());
        symbolTable.classes.put(Symbol.symbol("int[]"), new ClassInfo());
    }

    @Override
    public Type visit(Program program) {
        program.mainClass.accept(this);
        program.classesDeclaration.forEach(classDecl -> classDecl.accept(this));

        return null;
    }

    @Override
    public Type visit(MainClass mainClass) {
        currClass = new ClassInfo();
        currMethod = new MethodInfo();
        currClass.name = Symbol.symbol(mainClass.name.toString());
        currMethod.name = Symbol.symbol("main");
        mainClass.body.accept(this);

        return null;
    }

    @Override
    public Type visit(ClassDeclSimple classDeclSimple) {
        currClass = symbolTable.get(Symbol.get(classDeclSimple.name.toString()));

        classDeclSimple.fields.forEach(fieldDecl -> fieldDecl.accept(this));
        classDeclSimple.methods.forEach(methodDecl -> methodDecl.accept(this));

        return null;
    }

    @Override
    public Type visit(ClassDeclExtends classDeclExtends) {
        currClass = symbolTable.get(Symbol.get(classDeclExtends.name.toString()));

        classDeclExtends.fields.forEach(fieldDecl -> fieldDecl.accept(this));
        classDeclExtends.methods.forEach(methodDecl -> methodDecl.accept(this));

        return null;
    }

    @Override
    public Type visit(VarDecl varDecl) {
        Type varType = currMethod.context.get(Symbol.get(varDecl.name.toString()));

        varType = varType == null
                ? currClass.fields.get(Symbol.get(varDecl.name.toString()))
                : varType;

        if (!symbolTable.hasType(varType))
            Error.undefinedType(currClass.name.toString(), currMethod.name.toString(), varType.toString());

        return null;
    }

    @Override
    public Type visit(MethodDecl methodDecl) {
        currMethod = currClass.methods.get(Symbol.get(methodDecl.name.toString()));

        methodDecl.params.forEach(param -> param.accept(this));
        methodDecl.locals.forEach(local -> local.accept(this));
        methodDecl.statements.forEach(statement -> statement.accept(this));

        Type returnExprType = methodDecl.returnExp.accept(this);
        if (!symbolTable.hasType(methodDecl.returnType))
            Error.undefinedType(currClass.name.toString(), currMethod.name.toString(), methodDecl.returnType.toString());
        else if (!returnExprType.equals(methodDecl.returnType))
            Error.incompatibleReturnType(currClass.name.toString(), currMethod.name.toString(), methodDecl.returnType.toString(), returnExprType.toString());

        return null;
    }

    @Override
    public Type visit(Formal formal) {
        Type formalType = currMethod.context.get(Symbol.get(formal.name.toString()));
        if (!symbolTable.hasType(formalType))
            Error.undefinedType(currClass.name.toString(), currMethod.name.toString(), formalType.toString());

        return null;

    }

    @Override
    public Type visit(And and) {
        Type leftType = and.left.accept(this);
        Type rightType = and.right.accept(this);

        if (!(leftType instanceof BooleanType))
            Error.incompatibleType(currClass.name.toString(), currMethod.name.toString(), "boolean", leftType.toString());
        if (!(rightType instanceof BooleanType))
            Error.incompatibleType(currClass.name.toString(), currMethod.name.toString(), "boolean", rightType.toString());

        return new BooleanType();
    }

    @Override
    public Type visit(LessThan lessThan) {
        Type leftType = lessThan.left.accept(this);
        Type rightType = lessThan.right.accept(this);

        if (!(leftType instanceof IntegerType))
            Error.incompatibleType(currClass.name.toString(), currMethod.name.toString(), "int", leftType.toString());
        if (!(rightType instanceof IntegerType))
            Error.incompatibleType(currClass.name.toString(), currMethod.name.toString(), "int", rightType.toString());

        return new BooleanType();
    }

    @Override
    public Type visit(Plus plus) {
        Type leftType = plus.left.accept(this);
        Type rightType = plus.right.accept(this);

        if (!(leftType instanceof IntegerType))
            Error.incompatibleType(currClass.name.toString(), currMethod.name.toString(), "int", leftType.toString());
        if (!(rightType instanceof IntegerType))
            Error.incompatibleType(currClass.name.toString(), currMethod.name.toString(), "int", rightType.toString());

        return new IntegerType();
    }

    @Override
    public Type visit(Minus minus) {
        Type leftType = minus.left.accept(this);
        Type rightType = minus.right.accept(this);

        if (!(leftType instanceof IntegerType))
            Error.incompatibleType(currClass.name.toString(), currMethod.name.toString(), "int", leftType.toString());
        if (!(rightType instanceof IntegerType))
            Error.incompatibleType(currClass.name.toString(), currMethod.name.toString(), "int", rightType.toString());

        return new IntegerType();
    }

    @Override
    public Type visit(Times times) {
        Type leftType = times.left.accept(this);
        Type rightType = times.right.accept(this);

        if (!(leftType instanceof IntegerType))
            Error.incompatibleType(currClass.name.toString(), currMethod.name.toString(), "int", leftType.toString());
        if (!(rightType instanceof IntegerType))
            Error.incompatibleType(currClass.name.toString(), currMethod.name.toString(), "int", rightType.toString());

        return new IntegerType();
    }

    @Override
    public Type visit(ArrayLookup arrayLookup) {
        Type arrayType = arrayLookup.array.accept(this);
        if (!(arrayType instanceof IntArrayType))
            Error.incompatibleType(currClass.name.toString(), currMethod.name.toString(), "int[]", arrayType.toString());
        Type indexType = arrayLookup.index.accept(this);
        if (!(indexType instanceof IntegerType))
            Error.incompatibleType(currClass.name.toString(), currMethod.name.toString(), "int", indexType.toString());

        return new IntegerType();
    }

    @Override
    public Type visit(ArrayLength arrayLength) {
        Type arrayType = arrayLength.exp.accept(this);
        if (!(arrayType instanceof IntArrayType))
            Error.incompatibleType(currClass.name.toString(), currMethod.name.toString(), "int[]", arrayType.toString());

        return new IntegerType();
    }

    @Override
    public Type visit(MethodCall methodCall) {
        Type callerType = methodCall.caller.accept(this);
        if (!symbolTable.hasType(callerType)) {
            Error.undefinedType(currClass.name.toString(), currMethod.name.toString(), callerType.toString());
            return callerType;
        }

        ClassInfo callerClass = symbolTable.get(Symbol.get(callerType.toString()));
        MethodInfo method = callerClass.methods.get(Symbol.get(methodCall.methodName.toString()));
        if (method == null) {
            Error.undefinedMethod(currClass.name.toString(), currMethod.name.toString(), methodCall.methodName.toString());
            return callerType;
        }

        if (method.params.size() != methodCall.args.size())
            Error.incompatibleNumberOfArguments(currClass.name.toString(), currMethod.name.toString(), methodCall.methodName.toString(), method.params.size(), methodCall.args.size());
        else {
            int index = 0;
            for (Map.Entry<Symbol, Type> param : method.params.entrySet()) {
                Type argType = methodCall.args.get(index).accept(this);
                if (!isSameType(param.getValue(), argType))
                    Error.incompatibleType(currClass.name.toString(), currMethod.name.toString(), param.getValue().toString(), argType.toString());

                index++;
            }
        }

        return method.returnType;
    }

    @Override
    public Type visit(IntegerLiteral integerLiteral) {
        return new IntegerType();
    }

    @Override
    public Type visit(True aTrue) {
        return new BooleanType();
    }

    @Override
    public Type visit(False aFalse) {
        return new BooleanType();
    }

    @Override
    public Type visit(IdentifierExp identifierExp) {
        return currMethod.context.get(Symbol.get(identifierExp.name));
    }

    @Override
    public Type visit(This aThis) {
        return new IdentifierType(currClass.name.toString());
    }

    @Override
    public Type visit(NewArray newArray) {
        return new IntArrayType();
    }

    @Override
    public Type visit(NewObject newObject) {
        return new IdentifierType(newObject.identifier.toString());
    }

    @Override
    public Type visit(Not not) {
        Type exprType = not.exp.accept(this);
        if (!(exprType instanceof BooleanType))
            Error.incompatibleType(currClass.name.toString(), currMethod.name.toString(), "boolean", exprType.toString());

        return new BooleanType();
    }

    @Override
    public Type visit(Block block) {
        block.statements.forEach(statement -> statement.accept(this));

        return null;
    }

    @Override
    public Type visit(IfElse ifElse) {
        Type conditionType = ifElse.condition.accept(this);
        if (!(conditionType instanceof BooleanType))
            Error.incompatibleType(currClass.name.toString(), currMethod.name.toString(), "boolean", conditionType.toString());

        ifElse.thenStmt.accept(this);
        ifElse.elseStmt.accept(this);

        return null;
    }

    @Override
    public Type visit(While aWhile) {
        Type conditionType = aWhile.condition.accept(this);
        if (!(conditionType instanceof BooleanType))
            Error.incompatibleType(currClass.name.toString(), currMethod.name.toString(), "boolean", conditionType.toString());

        aWhile.body.accept(this);

        return null;
    }

    @Override
    public Type visit(Print print) {
        Type exprType = print.exp.accept(this);
        if (!(exprType instanceof IntegerType))
            Error.incompatibleType(currClass.name.toString(), currMethod.name.toString(), "int", exprType.toString());

        return null;
    }

    @Override
    public Type visit(Assign assign) {
        Type varType = currMethod.context.get(Symbol.get(assign.varName.toString()));
        if (varType == null) {
            Error.undefinedVariable(currClass.name.toString(), currMethod.name.toString(), assign.varName.toString());
            return null;
        }

        Type valueType = assign.value.accept(this);
        if (!isSameType(valueType, varType))
            Error.incompatibleType(currClass.name.toString(), currMethod.name.toString(), varType.toString(), valueType.toString());

        return null;
    }

    @Override
    public Type visit(ArrayAssign arrayAssign) {
        Type arrayType = currMethod.context.get(Symbol.get(arrayAssign.arrayName.toString()));
        if (arrayType == null) {
            Error.undefinedVariable(currClass.name.toString(), currMethod.name.toString(), arrayAssign.arrayName.toString());
            return null;
        } else if (!(arrayType instanceof IntArrayType))
            Error.incompatibleType(currClass.name.toString(), currMethod.name.toString(), "int[]", arrayType.toString());

        Type indexType = arrayAssign.index.accept(this);
        if (!(indexType instanceof IntegerType))
            Error.incompatibleType(currClass.name.toString(), currMethod.name.toString(), "int", indexType.toString());

        Type valueType = arrayAssign.value.accept(this);
        if (!(valueType instanceof IntegerType))
            Error.incompatibleType(currClass.name.toString(), currMethod.name.toString(), "int", valueType.toString());

        return null;
    }

    @Override
    public Type visit(BooleanType booleanType) {
        return booleanType;
    }

    @Override
    public Type visit(IntegerType integerType) {
        return integerType;
    }

    @Override
    public Type visit(IdentifierType identifierType) {
        return identifierType;
    }

    @Override
    public Type visit(IntArrayType intArrayType) {
        return intArrayType;
    }

    @Override
    public Type visit(Identifier identifier) {
        return null;
    }

    boolean isSameType(Type left, Type right) {
        if (left.equals(right))
            return true;

        ClassInfo leftInfo = symbolTable.get(Symbol.get(left.toString()));
        ClassInfo rightInfo = symbolTable.get(Symbol.get(right.toString()));

        if (leftInfo == null || rightInfo == null)
            return false;

        return leftInfo.isSubclassOf(rightInfo) || rightInfo.isSubclassOf(leftInfo);
    }
}
