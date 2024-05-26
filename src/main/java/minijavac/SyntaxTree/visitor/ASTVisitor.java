package minijavac.SyntaxTree.visitor;

import minijavac.SyntaxTree.*;
import minijavac.SyntaxTree.expression.*;
import minijavac.SyntaxTree.statement.*;
import minijavac.SyntaxTree.classes.*;
import minijavac.SyntaxTree.type.*;

public interface ASTVisitor<T> {
    T visit(Program program);
    T visit(Identifier identifier);

    // Classes
    T visit(MainClass mainClass);
    T visit(ClassDeclSimple classDeclSimple);
    T visit(ClassDeclExtends classDeclExtends);
    T visit(VarDecl varDecl);
    T visit(MethodDecl methodDecl);
    T visit(Formal formal);

    // Expressions
    T visit(And and);
    T visit(LessThan lessThan);
    T visit(Plus plus);
    T visit(Minus minus);
    T visit(Times times);
    T visit(ArrayLookup arrayLookup);
    T visit(ArrayLength arrayLength);
    T visit(MethodCall methodCall);
    T visit(IntegerLiteral integerLiteral);
    T visit(True aTrue);
    T visit(False aFalse);
    T visit(IdentifierExp identifierExp);
    T visit(This aThis);
    T visit(NewArray newArray);
    T visit(NewObject newObject);
    T visit(Not not);

    // Statements
    T visit(Block block);
    T visit(IfElse ifElse);
    T visit(While aWhile);
    T visit(Print print);
    T visit(Assign assign);
    T visit(ArrayAssign arrayAssign);

    // Types
    T visit(BooleanType booleanType);
    T visit(IntegerType integerType);
    T visit(IdentifierType identifierType);
    T visit(IntArrayType intArrayType);
}
