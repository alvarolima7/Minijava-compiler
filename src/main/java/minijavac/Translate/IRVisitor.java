package minijavac.Translate;

import minijavac.Frame.*;
import minijavac.Temp.*;
import minijavac.Tree.*;
import minijavac.SemanticAnalysis.*;
import minijavac.SyntaxTree.*;
import minijavac.SyntaxTree.classes.*;
import minijavac.SyntaxTree.expression.*;
import minijavac.SyntaxTree.statement.Print;
import minijavac.SyntaxTree.statement.*;
import minijavac.SyntaxTree.type.*;
import minijavac.SyntaxTree.visitor.ASTVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class IRVisitor implements ASTVisitor<Exp> {
    TypeCheckVisitor typeCheckVisitor;
    SymbolTable symbolTable;
    ClassInfo currClass;
    MethodInfo currMethod;
    Frame currFrame;
    LinkedList<ProcFrag> frags;

    public IRVisitor(Frame Frame, TypeCheckVisitor typeCheckVisitor) {
        this.typeCheckVisitor = typeCheckVisitor;
        this.symbolTable = typeCheckVisitor.symbolTable;
        currFrame = Frame;
        frags = new LinkedList<>();
    }

    public LinkedList<ProcFrag> getResult() {
        return frags;
    }

    @Override
    public Exp visit(Program program) {
        program.mainClass.accept(this);
        program.classesDeclaration.forEach(classDecl -> classDecl.accept(this));

        return null;
    }

    @Override
    public Exp visit(Identifier identifier) {
        Access a = currFrame.allocLocal(false);

        return new Exp(a.exp(new TEMP(currFrame.FP())));
    }

    @Override
    public Exp visit(MainClass mainClass) {
        Symbol frameName = Symbol.symbol(mainClass.name.toString() + "$main");
        List<Boolean> escapes = List.of(false);
        currFrame = currFrame.newFrame(frameName, escapes);

        Stm mainStm = mainClass.body.accept(this).unNx();
        List<Stm> bodyList = new ArrayList<>(List.of(mainStm));
        procEntryExit(bodyList);

        return null;
    }

    @Override
    public Exp visit(ClassDeclSimple classDeclSimple) {
        currClass = symbolTable.get(Symbol.get(classDeclSimple.name.toString()));

        classDeclSimple.name.accept(this);

        classDeclSimple.fields.forEach(field -> field.accept(this));
        classDeclSimple.methods.forEach(method -> method.accept(this));

        return null;
    }

    @Override
    public Exp visit(ClassDeclExtends classDeclExtends) {
        currClass = symbolTable.get(Symbol.get(classDeclExtends.name.toString()));

        classDeclExtends.name.accept(this);

        classDeclExtends.fields.forEach(field -> field.accept(this));
        classDeclExtends.methods.forEach(method -> method.accept(this));

        return null;
    }

    @Override
    public Exp visit(VarDecl varDecl) {
        varDecl.type.accept(this);
        varDecl.name.accept(this);

        return null;
    }

    @Override
    public Exp visit(MethodDecl methodDecl) {
        currMethod = currClass.methods.get(Symbol.get(methodDecl.name.toString()));

        Symbol frameName = Symbol.symbol(currClass.name.toString() + "$" + currMethod.name.toString());
        List<Boolean> escapes = Collections.nCopies(currMethod.params.size() + 1, false);
        currFrame = currFrame.newFrame(frameName, escapes);

        methodDecl.params.forEach(param -> param.accept(this));
        methodDecl.locals.forEach(local -> local.accept(this));

        Stm firstStm = methodDecl.statements.isEmpty()
                ? new EXP(new CONST(0))
                : methodDecl.statements.getFirst().accept(this).unNx();

        Stm body = methodDecl.statements
                .stream()
                .map(stmt -> stmt.accept(this).unNx())
                .skip(1)
                .reduce(firstStm, SEQ::new);

        Expr returnExp = new ESEQ(body, methodDecl.returnExp.accept(this).unEx());
        body = new MOVE(new TEMP(currFrame.RV()), returnExp);

        ArrayList<Stm> bodyList = new ArrayList<>(List.of(body));
        procEntryExit(bodyList);

        return null;
    }

    public void procEntryExit(List<Stm> body) {
//        currFrame.procEntryExit1(body);
        frags.add(new ProcFrag(body, currFrame));
    }

    @Override
    public Exp visit(Formal formal) {
        formal.name.accept(this);
        formal.type.accept(this);

        return null;
    }

    @Override
    public Exp visit(And and) {
        return new Exp(new BINOP(BINOP.AND,
                and.left.accept(this).unEx(),
                and.right.accept(this).unEx()));
    }

    @Override
    public Exp visit(LessThan lessThan) {
        Expr left = lessThan.left.accept(this).unEx();
        Expr right = lessThan.right.accept(this).unEx();

        Label trueLabel = new Label();
        Label falseLabel = new Label();
        Label end = new Label();

        Temp result = new Temp();

        MEM resultMem = new MEM(new TEMP(result));

        MOVE ifTrue = new MOVE(new TEMP(result), new CONST(1));
        MOVE ifFalse = new MOVE(new TEMP(result), new CONST(0));

        CJUMP cjump = new CJUMP(CJUMP.LT, left, right, trueLabel, falseLabel);

        Expr lessThanExpr = new ESEQ(
                new SEQ(cjump,
                        new SEQ(new LABEL(trueLabel),
                                new SEQ(ifTrue,
                                        new SEQ(new JUMP(end),
                                                new SEQ(new LABEL(falseLabel),
                                                        new SEQ(ifFalse,
                                                                new LABEL(end))))))),
                resultMem);

        return new Exp(lessThanExpr);
    }

    @Override
    public Exp visit(Plus plus) {
        return new Exp(new BINOP(BINOP.PLUS,
                plus.left.accept(this).unEx(),
                plus.right.accept(this).unEx()));
    }

    @Override
    public Exp visit(Minus minus) {
        return new Exp(new BINOP(BINOP.MINUS,
                minus.left.accept(this).unEx(),
                minus.right.accept(this).unEx()));
    }

    @Override
    public Exp visit(Times times) {
        return new Exp(new BINOP(BINOP.MUL,
                times.left.accept(this).unEx(),
                times.right.accept(this).unEx()));
    }

    @Override
    public Exp visit(ArrayLookup arrayLookup) {
        Exp arrayBegin = arrayLookup.array.accept(this);
        Exp index = arrayLookup.index.accept(this);

        BINOP offsetInBytes = new BINOP(BINOP.MUL, index.unEx(), new CONST(currFrame.wordSize()));
        BINOP pointer = new BINOP(BINOP.PLUS, arrayBegin.unEx(), offsetInBytes);

        return new Exp(new MEM(pointer));
    }

    @Override
    public Exp visit(ArrayLength arrayLength) {
        return new Exp(new MEM(arrayLength.exp.accept(this).unEx()));
    }

    @Override
    public Exp visit(MethodCall methodCall) {
        ArrayList<Expr> args = methodCall.args
                .stream()
                .map(arg -> arg.accept(this).unEx())
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        args.addFirst(methodCall.caller.accept(this).unEx());

        typeCheckVisitor.currClass = currClass;
        typeCheckVisitor.currMethod = currMethod;
        Type callerType = methodCall.caller.accept(typeCheckVisitor);

        Label label = new Label(callerType.toString() + "$" + methodCall.methodName);
        CALL methodCallExp = new CALL(new NAME(label), args);

        return new Exp(methodCallExp);
    }

    @Override
    public Exp visit(IntegerLiteral integerLiteral) {
        return new Exp(new CONST(integerLiteral.value));
    }

    @Override
    public Exp visit(True aTrue) {
        return new Exp(new CONST(1));
    }

    @Override
    public Exp visit(False aFalse) {
        return new Exp(new CONST(0));
    }

    @Override
    public Exp visit(IdentifierExp identifierExp) {
        Access a = currFrame.allocLocal(false);
        return new Exp(a.exp(new TEMP(currFrame.FP())));
    }

    @Override
    public Exp visit(This aThis) {
        return new Exp(new MEM(new TEMP(currFrame.FP())));
    }

    @Override
    public Exp visit(NewArray newArray) {
        Exp sizeExp = newArray.size.accept(this);

        BINOP size = new BINOP(BINOP.PLUS, sizeExp.unEx(), new CONST(1));
        BINOP sizeInBytes = new BINOP(BINOP.MUL, size, new CONST(currFrame.wordSize()));

        List<Expr> args = new ArrayList<>(List.of(sizeInBytes));
        Expr alloc = currFrame.externalCall("initArray", args);

        return new Exp(alloc);
    }

    @Override
    public Exp visit(NewObject newObject) {
        ClassInfo objInfo = symbolTable.get(Symbol.get(newObject.identifier.toString()));

        int objSize = objInfo.fields.size();
        BINOP objSizeInBytes = new BINOP(BINOP.MUL, new CONST(objSize), new CONST(currFrame.wordSize()));

        List<Expr> args = new ArrayList<>(List.of(objSizeInBytes));

        Expr alloc = currFrame.externalCall("malloc", args);

        return new Exp(alloc);
    }

    @Override
    public Exp visit(Not not) {
        return new Exp(new BINOP(BINOP.XOR,
                new CONST(1),
                not.exp.accept(this).unEx()));
    }

    @Override
    public Exp visit(Block block) {
        Stm firstStm = block.statements.getFirst().accept(this).unNx();
        Stm blockStm = block.statements
                .stream()
                .map(stmt -> stmt.accept(this).unNx())
                .skip(1)
                .reduce(firstStm, SEQ::new);

        return new Exp(new ESEQ(blockStm, new CONST(0)));
    }

    @Override
    public Exp visit(IfElse ifElse) {
        Exp condition = ifElse.condition.accept(this);
        Exp thenExp = ifElse.thenStmt.accept(this);
        Exp elseExp = ifElse.elseStmt.accept(this);

        Label thenLabel = new Label();
        Label elseLabel = new Label();
        Label end = new Label();

        CJUMP cjump = new CJUMP(CJUMP.EQ, condition.unEx(), new CONST(1), thenLabel, elseLabel);

        SEQ ifElseStm = new SEQ(cjump,
                new SEQ(new LABEL(thenLabel),
                        new SEQ(thenExp.unNx(),
                                new SEQ(new JUMP(end),
                                        new SEQ(new LABEL(elseLabel),
                                                new SEQ(elseExp.unNx(),
                                                        new LABEL(end)))))));

        return new Exp(new ESEQ(ifElseStm, new CONST(0)));
    }

    @Override
    public Exp visit(While aWhile) {
        Label whileConditionTest = new Label();
        Label whileBodyStart = new Label();
        Label end = new Label();

        Exp condition = aWhile.condition.accept(this);
        Exp body = aWhile.body.accept(this);

        CJUMP cjump = new CJUMP(CJUMP.EQ, condition.unEx(), new CONST(1), whileBodyStart, end);

        SEQ loop = new SEQ(new LABEL(whileConditionTest),
                new SEQ(cjump,
                        new SEQ(new LABEL(whileBodyStart),
                                new SEQ(body.unNx(),
                                        new SEQ(new JUMP(whileConditionTest),
                                                new LABEL(end))))));

        return new Exp(new ESEQ(loop, new CONST(0)));
    }

    @Override
    public Exp visit(Print print) {
        Exp printExp = print.exp.accept(this);
        ArrayList<Expr> args = new ArrayList<>(List.of(printExp.unEx()));

        Expr syscall = currFrame.externalCall("print", args);

        Stm printStm = new EXP(syscall);

        return new Exp(new ESEQ(printStm, new CONST(0)));
    }

    @Override
    public Exp visit(Assign assign) {
        Expr var = assign.varName.accept(this).unEx();
        Expr value = assign.value.accept(this).unEx();

        MOVE assignStm = new MOVE(new MEM(var), value);

        return new Exp(new ESEQ(assignStm, new CONST(0)));
    }

    @Override
    public Exp visit(ArrayAssign arrayAssign) {
        Exp arrayBegin = arrayAssign.arrayName.accept(this);
        Exp index = arrayAssign.index.accept(this);
        Exp value = arrayAssign.value.accept(this);

        BINOP offsetInBytes = new BINOP(BINOP.MUL, index.unEx(), new CONST(currFrame.wordSize()));
        BINOP pointerToElement = new BINOP(BINOP.PLUS, arrayBegin.unEx(), offsetInBytes);

        MOVE arrayAssignStm = new MOVE(new MEM(pointerToElement), value.unEx());

        return new Exp(new ESEQ(arrayAssignStm, new CONST(0)));
    }

    @Override
    public Exp visit(BooleanType booleanType) {
        return null;
    }

    @Override
    public Exp visit(IntegerType integerType) {
        return null;
    }

    @Override
    public Exp visit(IdentifierType identifierType) {
        return null;
    }

    @Override
    public Exp visit(IntArrayType intArrayType) {
        return null;
    }
}
