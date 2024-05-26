package minijavac.SyntaxTree.visitor;

import minijavac.SyntaxTree.*;
import minijavac.SyntaxTree.classes.*;
import minijavac.SyntaxTree.expression.*;
import minijavac.SyntaxTree.statement.*;
import minijavac.SyntaxTree.type.*;

public class PrettyPrintVisitor implements ASTVisitor<Void> {
    int identLevel = 0;

    public PrettyPrintVisitor() {}

    private String indentation() {
        return "    ".repeat(identLevel);
    }

    @Override
    public Void visit(Program program) {
        System.out.print("class ");
        program.mainClass.accept(this);
        System.out.print("}\n\n");

        for (ClassDecl classDecl : program.classesDeclaration) {
            classDecl.accept(this);
            System.out.println();
        }

        return null;
    }

    @Override
    public Void visit(MainClass mainClass) {
        System.out.print(mainClass.name.toString() + " {\n");
        identLevel++;
        System.out.print(indentation() + "public static void main(String[] " + mainClass.argsName.toString() + ") {");
        identLevel++;
        mainClass.body.accept(this);
        identLevel--;
        System.out.print("\n" + indentation() + "}\n");
        identLevel--;

        return null;
    }

    @Override
    public Void visit(ClassDeclSimple classDeclSimple) {
        System.out.print("\nclass ");
        classDeclSimple.name.accept(this);
        System.out.print(" {");
        identLevel++;

        if (!classDeclSimple.fields.isEmpty())
            System.out.println();

        for (VarDecl field : classDeclSimple.fields)
            field.accept(this);

        if (!classDeclSimple.methods.isEmpty())
            System.out.println();

        for (MethodDecl methodDecl : classDeclSimple.methods) {
            methodDecl.accept(this);
            System.out.println();
        }

        identLevel--;

        System.out.print("}\n");

        return null;
    }

    @Override
    public Void visit(ClassDeclExtends classDeclExtends) {
        System.out.print("class ");
        classDeclExtends.name.accept(this);
        System.out.print(" extends ");
        classDeclExtends.superClassName.accept(this);
        System.out.print(" {");
        identLevel++;

        if (!classDeclExtends.fields.isEmpty())
            System.out.println();

        for (VarDecl field : classDeclExtends.fields)
            field.accept(this);

        if (!classDeclExtends.methods.isEmpty())
            System.out.println();

        for (MethodDecl methodDecl : classDeclExtends.methods)
            methodDecl.accept(this);

        identLevel--;

        System.out.print("}\n");

        return null;
    }

    @Override
    public Void visit(VarDecl varDecl) {
        System.out.print(indentation());
        varDecl.type.accept(this);
        System.out.print(" ");
        varDecl.name.accept(this);
        System.out.print(";\n");

        return null;
    }

    @Override
    public Void visit(MethodDecl methodDecl) {
        System.out.print(indentation() + "public ");
        methodDecl.returnType.accept(this);
        System.out.print(" ");
        methodDecl.name.accept(this);
        System.out.print("(");

        int index = 0;
        for (Formal param : methodDecl.params) {
            param.accept(this);

            if (index != methodDecl.params.size() - 1)
                System.out.print(", ");
            index++;
        }
        System.out.print(") {");
        identLevel++;

        if (!methodDecl.locals.isEmpty())
            System.out.println();

        for (VarDecl varDecl : methodDecl.locals)
            varDecl.accept(this);

        for (Statement statement : methodDecl.statements)
            statement.accept(this);

        if (!methodDecl.statements.isEmpty())
            System.out.println();

        System.out.print("\n" + indentation() + "return ");
        methodDecl.returnExp.accept(this);
        System.out.print(";\n");
        identLevel--;
        System.out.print(indentation() + "}\n");

        return null;
    }

    @Override
    public Void visit(Formal formal) {
        System.out.print(formal.type.toString() + " " + formal.name.toString());

        return null;
    }

    @Override
    public Void visit(And and) {
        System.out.print("( ");
        and.left.accept(this);
        System.out.print(" && ");
        and.right.accept(this);
        System.out.print(" )");

        return null;
    }

    @Override
    public Void visit(LessThan lessThan) {
        System.out.print("( ");
        lessThan.left.accept(this);
        System.out.print(" < ");
        lessThan.right.accept(this);
        System.out.print(" )");

        return null;
    }

    @Override
    public Void visit(Plus plus) {
        System.out.print("( ");
        plus.left.accept(this);
        System.out.print(" + ");
        plus.right.accept(this);
        System.out.print(" )");

        return null;
    }

    @Override
    public Void visit(Minus minus) {
        System.out.print("( ");
        minus.left.accept(this);
        System.out.print(" - ");
        minus.right.accept(this);
        System.out.print(" )");

        return null;
    }

    @Override
    public Void visit(Times times) {
        System.out.print("( ");
        times.left.accept(this);
        System.out.print(" * ");
        times.right.accept(this);
        System.out.print(" )");

        return null;
    }

    @Override
    public Void visit(ArrayLookup arrayLookup) {
        arrayLookup.array.accept(this);
        System.out.print("[ ");
        arrayLookup.index.accept(this);
        System.out.print(" ]");

        return null;
    }

    @Override
    public Void visit(ArrayLength arrayLength) {
        arrayLength.exp.accept(this);
        System.out.print(".length");

        return null;
    }

    @Override
    public Void visit(MethodCall methodCall) {
        methodCall.caller.accept(this);
        System.out.print(".");
        methodCall.methodName.accept(this);
        System.out.print("(");
        int index = 0;
        for (Expression expression : methodCall.args) {
            expression.accept(this);
            if (index < methodCall.args.size() - 1)
                System.out.print(", ");
            index++;
        }
        System.out.print(")");

        return null;
    }

    @Override
    public Void visit(IntegerLiteral integerLiteral) {
        System.out.print(integerLiteral.value);

        return null;
    }

    @Override
    public Void visit(True aTrue) {
        System.out.print("true");

        return null;
    }

    @Override
    public Void visit(False aFalse) {
        System.out.print("false");

        return null;
    }

    @Override
    public Void visit(IdentifierExp identifierExp) {
        System.out.print(identifierExp.name);

        return null;
    }

    @Override
    public Void visit(This aThis) {
        System.out.print("this");

        return null;
    }

    @Override
    public Void visit(NewArray newArray) {
        System.out.print("new int[ ");
        newArray.size.accept(this);
        System.out.print(" ]");

        return null;
    }

    @Override
    public Void visit(NewObject newObject) {
        System.out.print("new ");
        newObject.identifier.accept(this);
        System.out.print("()");

        return null;
    }

    @Override
    public Void visit(Not not) {
        System.out.print("!( ");
        not.exp.accept(this);
        System.out.print(" )");

        return null;
    }

    @Override
    public Void visit(Identifier identifier) {
        System.out.print(identifier.toString());

        return null;
    }

    @Override
    public Void visit(Block block) {
        System.out.print(" {");
        block.statements.forEach(stmt -> stmt.accept(this));
        identLevel--;
        System.out.print("\n" + indentation() + "}");
        identLevel++;

        return null;
    }

    @Override
    public Void visit(IfElse ifElse) {
        System.out.print("\n" + indentation() + "if ( ");
        ifElse.condition.accept(this);
        System.out.print(" )");
        identLevel++;
        ifElse.thenStmt.accept(this);
        identLevel--;
        System.out.print("\n" + indentation() + "else");
        identLevel++;
        ifElse.elseStmt.accept(this);
        identLevel--;

        return null;
    }

    @Override
    public Void visit(While aWhile) {
        System.out.print("\n" + indentation() + "while ( ");
        aWhile.condition.accept(this);
        System.out.print(" )");
        identLevel++;
        aWhile.body.accept(this);
        identLevel--;

        return null;
    }

    @Override
    public Void visit(Print print) {
        System.out.print("\n" + indentation() + "System.out.println( ");
        print.exp.accept(this);
        System.out.print(" );");

        return null;
    }

    @Override
    public Void visit(Assign assign) {
        System.out.print("\n" + indentation());
        assign.varName.accept(this);
        System.out.print(" = ");
        assign.value.accept(this);
        System.out.print(";");

        return null;
    }

    @Override
    public Void visit(ArrayAssign arrayAssign) {
        System.out.print("\n" + indentation());
        arrayAssign.arrayName.accept(this);
        System.out.print("[ ");
        arrayAssign.index.accept(this);
        System.out.print(" ] = ");
        arrayAssign.value.accept(this);
        System.out.print(";");

        return null;
    }

    @Override
    public Void visit(BooleanType booleanType) {
        System.out.print("boolean");

        return null;
    }

    @Override
    public Void visit(IntegerType integerType) {
        System.out.print("int");

        return null;
    }

    @Override
    public Void visit(IdentifierType identifierType) {
        identifierType.typename.accept(this);

        return null;
    }

    @Override
    public Void visit(IntArrayType intArrayType) {
        System.out.print("int[]");

        return null;
    }
}
