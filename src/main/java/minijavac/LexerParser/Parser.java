/* Parser.java */
/* Generated By:JavaCC: Do not edit this line. Parser.java */
package minijavac.LexerParser;
import minijavac.SyntaxTree.*;
import minijavac.SyntaxTree.classes.*;
import minijavac.SyntaxTree.expression.*;
import minijavac.SyntaxTree.statement.*;
import minijavac.SyntaxTree.type.*;
import java.util.ArrayList;
public class Parser implements ParserConstants {

  final public Program Start() throws ParseException {MainClass mainClass;
    ClassDecl currClass;
    ArrayList<ClassDecl> classDeclList = new ArrayList<>();
    mainClass = MainClass();
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case CLASS:{
        ;
        break;
        }
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      currClass = ClassDeclaration();
classDeclList.add(currClass);
    }
    jj_consume_token(0);
{if ("" != null) return new Program(mainClass, classDeclList);}
    throw new Error("Missing return statement in function");
}

  final public MainClass MainClass() throws ParseException {Token className;
    Token argsName;
    Statement body;
    jj_consume_token(CLASS);
    className = jj_consume_token(IDENTIFIER);
    jj_consume_token(LBRACE);
    jj_consume_token(PUBLIC);
    jj_consume_token(STATIC);
    jj_consume_token(VOID);
    jj_consume_token(MAIN);
    jj_consume_token(LPAREN);
    jj_consume_token(STRING);
    jj_consume_token(LBRACKET);
    jj_consume_token(RBRACKET);
    argsName = jj_consume_token(IDENTIFIER);
    jj_consume_token(RPAREN);
    jj_consume_token(LBRACE);
    body = Statement();
    jj_consume_token(RBRACE);
    jj_consume_token(RBRACE);
{if ("" != null) return new MainClass(new Identifier(className.image), new Identifier(argsName.image), body);}
    throw new Error("Missing return statement in function");
}

  final public ClassDecl ClassDeclaration() throws ParseException {Token className;
    ArrayList<VarDecl> varDeclList = new ArrayList<>();
    ArrayList<MethodDecl> methodDeclList = new ArrayList<>();
    VarDecl currVar;
    MethodDecl currMethod;
    Token superClassName = null;
    jj_consume_token(CLASS);
    className = jj_consume_token(IDENTIFIER);
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case EXTENDS:{
      jj_consume_token(EXTENDS);
      superClassName = jj_consume_token(IDENTIFIER);
      break;
      }
    default:
      jj_la1[1] = jj_gen;
      ;
    }
    jj_consume_token(LBRACE);
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case INT:
      case BOOLEAN:
      case IDENTIFIER:{
        ;
        break;
        }
      default:
        jj_la1[2] = jj_gen;
        break label_2;
      }
      currVar = VarDeclaration();
varDeclList.add(currVar);
    }
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case PUBLIC:{
        ;
        break;
        }
      default:
        jj_la1[3] = jj_gen;
        break label_3;
      }
      currMethod = MethodDeclaration();
methodDeclList.add(currMethod);
    }
    jj_consume_token(RBRACE);
{if ("" != null) return superClassName == null ?
     new ClassDeclSimple(new Identifier(className.image), varDeclList, methodDeclList) :
     new ClassDeclExtends(new Identifier(className.image), new Identifier(superClassName.image), varDeclList, methodDeclList);}
    throw new Error("Missing return statement in function");
}

  final public VarDecl VarDeclaration() throws ParseException {Type type;
    Token typeName;
    type = Type();
    typeName = jj_consume_token(IDENTIFIER);
    jj_consume_token(SEMICOLON);
{if ("" != null) return  new VarDecl(type, new Identifier(typeName.image));}
    throw new Error("Missing return statement in function");
}

  final public MethodDecl MethodDeclaration() throws ParseException {Type returnType;
    Token methodName;
    ArrayList<Formal> formalList;
    ArrayList<VarDecl> varDeclList = new ArrayList<>();
    ArrayList<Statement> statementList = new ArrayList<>();
    Expression returnExp;
    Formal currFormal;
    VarDecl currVar;
    Statement currStatement;
    jj_consume_token(PUBLIC);
    returnType = Type();
    methodName = jj_consume_token(IDENTIFIER);
    jj_consume_token(LPAREN);
    formalList = FormalList();
    jj_consume_token(RPAREN);
    jj_consume_token(LBRACE);
    label_4:
    while (true) {
      if (jj_2_1(2)) {
        ;
      } else {
        break label_4;
      }
      currVar = VarDeclaration();
varDeclList.add(currVar);
    }
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case IF:
      case WHILE:
      case LBRACE:
      case SYSTEM_OUT_PRINTLN:
      case IDENTIFIER:{
        ;
        break;
        }
      default:
        jj_la1[4] = jj_gen;
        break label_5;
      }
      currStatement = Statement();
statementList.add(currStatement);
    }
    jj_consume_token(RETURN);
    returnExp = Expression();
    jj_consume_token(SEMICOLON);
    jj_consume_token(RBRACE);
{if ("" != null) return new MethodDecl(returnType, new Identifier(methodName.image), formalList, varDeclList, statementList, returnExp);}
    throw new Error("Missing return statement in function");
}

  final public ArrayList<Formal> FormalList() throws ParseException {Type type;
    Token name;
    ArrayList<Formal> formalList = new ArrayList<>();
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case INT:
    case BOOLEAN:
    case IDENTIFIER:{
      type = Type();
      name = jj_consume_token(IDENTIFIER);
formalList.add(new Formal(type, new Identifier(name.image)));
      label_6:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case COMMA:{
          ;
          break;
          }
        default:
          jj_la1[5] = jj_gen;
          break label_6;
        }
        jj_consume_token(COMMA);
        type = Type();
        name = jj_consume_token(IDENTIFIER);
formalList.add(new Formal(type, new Identifier(name.image)));
      }
      break;
      }
    default:
      jj_la1[6] = jj_gen;
      ;
    }
{if ("" != null) return formalList;}
    throw new Error("Missing return statement in function");
}

  final public Type Type() throws ParseException {
    if (jj_2_2(2)) {
      jj_consume_token(INT);
      jj_consume_token(LBRACKET);
      jj_consume_token(RBRACKET);
{if ("" != null) return new IntArrayType();}
    } else {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case BOOLEAN:{
        jj_consume_token(BOOLEAN);
{if ("" != null) return new BooleanType();}
        break;
        }
      case INT:{
        jj_consume_token(INT);
{if ("" != null) return new IntegerType();}
        break;
        }
      case IDENTIFIER:{
Token identifier;
        identifier = jj_consume_token(IDENTIFIER);
{if ("" != null) return new IdentifierType(identifier.image);}
        break;
        }
      default:
        jj_la1[7] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
    throw new Error("Missing return statement in function");
}

  final public Statement Statement() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case LBRACE:{
Block block;
      block = Block();
{if ("" != null) return block;}
      break;
      }
    case IF:{
IfElse ifElseStatement;
      ifElseStatement = IfElse();
{if ("" != null) return ifElseStatement;}
      break;
      }
    case WHILE:{
While whileStatement;
      whileStatement = While();
{if ("" != null) return whileStatement;}
      break;
      }
    case SYSTEM_OUT_PRINTLN:{
Print printStatement;
      printStatement = Print();
{if ("" != null) return printStatement;}
      break;
      }
    case IDENTIFIER:{
Token varName; Expression index = null; Expression value;
      varName = jj_consume_token(IDENTIFIER);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case LBRACKET:{
        jj_consume_token(LBRACKET);
        index = Expression();
        jj_consume_token(RBRACKET);
        break;
        }
      default:
        jj_la1[8] = jj_gen;
        ;
      }
      jj_consume_token(ASSIGN);
      value = Expression();
      jj_consume_token(SEMICOLON);
{if ("" != null) return index == null ?
          new Assign(new Identifier(varName.image), value) :
          new ArrayAssign(new Identifier(varName.image), index, value);}
      break;
      }
    default:
      jj_la1[9] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
}

  final public Block Block() throws ParseException {ArrayList<Statement> statementList = new ArrayList<>();
    Statement currStatement;
    jj_consume_token(LBRACE);
    label_7:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case IF:
      case WHILE:
      case LBRACE:
      case SYSTEM_OUT_PRINTLN:
      case IDENTIFIER:{
        ;
        break;
        }
      default:
        jj_la1[10] = jj_gen;
        break label_7;
      }
      currStatement = Statement();
statementList.add(currStatement);
    }
    jj_consume_token(RBRACE);
{if ("" != null) return new Block(statementList);}
    throw new Error("Missing return statement in function");
}

  final public IfElse IfElse() throws ParseException {Expression ifCondition;
    Statement thenStatement;
    Statement elseStatement;
    jj_consume_token(IF);
    jj_consume_token(LPAREN);
    ifCondition = Expression();
    jj_consume_token(RPAREN);
    thenStatement = Statement();
    jj_consume_token(ELSE);
    elseStatement = Statement();
{if ("" != null) return new IfElse(ifCondition, thenStatement, elseStatement);}
    throw new Error("Missing return statement in function");
}

  final public While While() throws ParseException {Expression whileCondition;
    Statement body;
    jj_consume_token(WHILE);
    jj_consume_token(LPAREN);
    whileCondition = Expression();
    jj_consume_token(RPAREN);
    body = Statement();
{if ("" != null) return new While(whileCondition, body);}
    throw new Error("Missing return statement in function");
}

  final public Print Print() throws ParseException {Expression printExp;
    jj_consume_token(SYSTEM_OUT_PRINTLN);
    jj_consume_token(LPAREN);
    printExp = Expression();
    jj_consume_token(RPAREN);
    jj_consume_token(SEMICOLON);
{if ("" != null) return new Print(printExp);}
    throw new Error("Missing return statement in function");
}

  final public Expression Expression() throws ParseException {Expression expression;
    Expression lessThanExp;
    expression = LessThanExpression();
    label_8:
    while (true) {
      if (jj_2_3(2)) {
        ;
      } else {
        break label_8;
      }
      jj_consume_token(AND);
      lessThanExp = LessThanExpression();
expression = new And(expression, lessThanExp);
    }
{if ("" != null) return expression;}
    throw new Error("Missing return statement in function");
}

  final public Expression LessThanExpression() throws ParseException {Expression expression;
    Expression lessThanExp;
    expression = PlusMinusExpression();
    label_9:
    while (true) {
      if (jj_2_4(2)) {
        ;
      } else {
        break label_9;
      }
      jj_consume_token(LT);
      lessThanExp = PlusMinusExpression();
expression = new LessThan(expression, lessThanExp);
    }
{if ("" != null) return expression;}
    throw new Error("Missing return statement in function");
}

  final public Expression PlusMinusExpression() throws ParseException {Expression expression;
    Expression timesExp;
    expression = TimesExpression();
    label_10:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case PLUS:
      case MINUS:{
        ;
        break;
        }
      default:
        jj_la1[11] = jj_gen;
        break label_10;
      }
      if (jj_2_5(2)) {
        jj_consume_token(PLUS);
        timesExp = TimesExpression();
expression = new Plus(expression, timesExp);
      } else {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case MINUS:{
          jj_consume_token(MINUS);
          timesExp = TimesExpression();
expression = new Minus(expression, timesExp);
          break;
          }
        default:
          jj_la1[12] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    }
{if ("" != null) return expression;}
    throw new Error("Missing return statement in function");
}

  final public Expression TimesExpression() throws ParseException {Expression expression;
    Expression notExp;
    expression = NotExpression();
    label_11:
    while (true) {
      if (jj_2_6(2)) {
        ;
      } else {
        break label_11;
      }
      jj_consume_token(TIMES);
      notExp = NotExpression();
expression = new Times(expression, notExp);
    }
{if ("" != null) return expression;}
    throw new Error("Missing return statement in function");
}

  final public Expression NotExpression() throws ParseException {Expression expression;
    if (jj_2_7(2)) {
      jj_consume_token(NOT);
      expression = NotExpression();
{if ("" != null) return new Not(expression);}
    } else {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case LPAREN:
      case NEW:
      case TRUE:
      case FALSE:
      case THIS:
      case INT_LITERAL:
      case IDENTIFIER:{
        expression = Dot();
{if ("" != null) return expression;}
        break;
        }
      default:
        jj_la1[13] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
    throw new Error("Missing return statement in function");
}

  final public Expression Dot() throws ParseException {Expression expression;
    Expression rightHandSide;
    expression = Array();
    label_12:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case DOT:{
        ;
        break;
        }
      default:
        jj_la1[14] = jj_gen;
        break label_12;
      }
      if (jj_2_8(2)) {
        jj_consume_token(DOT);
        jj_consume_token(LENGTH);
{if ("" != null) return new ArrayLength(expression);}
      } else if (jj_2_9(2)) {
        jj_consume_token(DOT);
        rightHandSide = MethodCall(expression);
expression = rightHandSide;
      } else {
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
{if ("" != null) return expression;}
    throw new Error("Missing return statement in function");
}

  final public Expression MethodCall(Expression expression) throws ParseException {Token methodName;
    ArrayList<Expression> expList;
    methodName = jj_consume_token(IDENTIFIER);
    jj_consume_token(LPAREN);
    expList = ExpList();
    jj_consume_token(RPAREN);
{if ("" != null) return new MethodCall(expression, new Identifier(methodName.image), expList);}
    throw new Error("Missing return statement in function");
}

  final public ArrayList<Expression> ExpList() throws ParseException {ArrayList<Expression> expList = new ArrayList<>();
    Expression currExp;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case LPAREN:
    case NOT:
    case NEW:
    case TRUE:
    case FALSE:
    case THIS:
    case INT_LITERAL:
    case IDENTIFIER:{
      currExp = Expression();
expList.add(currExp);
      label_13:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case COMMA:{
          ;
          break;
          }
        default:
          jj_la1[15] = jj_gen;
          break label_13;
        }
        jj_consume_token(COMMA);
        currExp = Expression();
expList.add(currExp);
      }
      break;
      }
    default:
      jj_la1[16] = jj_gen;
      ;
    }
{if ("" != null) return expList;}
    throw new Error("Missing return statement in function");
}

  final public Expression Array() throws ParseException {Expression expression;
    Expression index = null;
    expression = Terminal();
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case LBRACKET:{
      jj_consume_token(LBRACKET);
      index = Expression();
      jj_consume_token(RBRACKET);
      break;
      }
    default:
      jj_la1[17] = jj_gen;
      ;
    }
{if ("" != null) return index == null ? expression : new ArrayLookup(expression, index);}
    throw new Error("Missing return statement in function");
}

  final public Expression Terminal() throws ParseException {Expression expression;
    Token identifier;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case LPAREN:{
      jj_consume_token(LPAREN);
      expression = Expression();
      jj_consume_token(RPAREN);
{if ("" != null) return expression;}
      break;
      }
    case IDENTIFIER:{
      identifier = jj_consume_token(IDENTIFIER);
{if ("" != null) return new IdentifierExp(identifier.image);}
      break;
      }
    case INT_LITERAL:{
      identifier = jj_consume_token(INT_LITERAL);
{if ("" != null) return new IntegerLiteral(Integer.parseInt(identifier.image));}
      break;
      }
    case TRUE:{
      jj_consume_token(TRUE);
{if ("" != null) return new True();}
      break;
      }
    case FALSE:{
      jj_consume_token(FALSE);
{if ("" != null) return new False();}
      break;
      }
    case THIS:{
      jj_consume_token(THIS);
{if ("" != null) return new This();}
      break;
      }
    case NEW:{
      jj_consume_token(NEW);
      expression = New();
{if ("" != null) return expression;}
      break;
      }
    default:
      jj_la1[18] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
}

  final public Expression New() throws ParseException {Token identifier;
    Expression expression;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case INT:{
      jj_consume_token(INT);
      jj_consume_token(LBRACKET);
      expression = Expression();
      jj_consume_token(RBRACKET);
{if ("" != null) return new NewArray(expression);}
      break;
      }
    case IDENTIFIER:{
      identifier = jj_consume_token(IDENTIFIER);
      jj_consume_token(LPAREN);
      jj_consume_token(RPAREN);
{if ("" != null) return new NewObject(new Identifier(identifier.image));}
      break;
      }
    default:
      jj_la1[19] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
}

  private boolean jj_2_1(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_1()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  private boolean jj_2_2(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_2()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(1, xla); }
  }

  private boolean jj_2_3(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_3()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(2, xla); }
  }

  private boolean jj_2_4(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_4()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(3, xla); }
  }

  private boolean jj_2_5(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_5()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(4, xla); }
  }

  private boolean jj_2_6(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_6()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(5, xla); }
  }

  private boolean jj_2_7(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_7()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(6, xla); }
  }

  private boolean jj_2_8(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_8()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(7, xla); }
  }

  private boolean jj_2_9(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_9()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(8, xla); }
  }

  private boolean jj_3_5()
 {
    if (jj_scan_token(PLUS)) return true;
    if (jj_3R_TimesExpression_283_5_17()) return true;
    return false;
  }

  private boolean jj_3R_MethodCall_322_5_19()
 {
    if (jj_scan_token(IDENTIFIER)) return true;
    return false;
  }

  private boolean jj_3R_Terminal_366_7_34()
 {
    if (jj_scan_token(NEW)) return true;
    return false;
  }

  private boolean jj_3R_Terminal_365_7_33()
 {
    if (jj_scan_token(THIS)) return true;
    return false;
  }

  private boolean jj_3R_Terminal_364_7_32()
 {
    if (jj_scan_token(FALSE)) return true;
    return false;
  }

  private boolean jj_3_9()
 {
    if (jj_scan_token(DOT)) return true;
    if (jj_3R_MethodCall_322_5_19()) return true;
    return false;
  }

  private boolean jj_3R_Terminal_363_7_31()
 {
    if (jj_scan_token(TRUE)) return true;
    return false;
  }

  private boolean jj_3R_Terminal_362_7_30()
 {
    if (jj_scan_token(INT_LITERAL)) return true;
    return false;
  }

  private boolean jj_3_8()
 {
    if (jj_scan_token(DOT)) return true;
    if (jj_scan_token(LENGTH)) return true;
    return false;
  }

  private boolean jj_3R_Terminal_361_7_29()
 {
    if (jj_scan_token(IDENTIFIER)) return true;
    return false;
  }

  private boolean jj_3R_PlusMinusExpression_269_5_16()
 {
    if (jj_3R_TimesExpression_283_5_17()) return true;
    return false;
  }

  private boolean jj_3R_NotExpression_297_24_21()
 {
    if (jj_3R_Dot_307_9_25()) return true;
    return false;
  }

  private boolean jj_3R_Type_172_7_24()
 {
    if (jj_scan_token(IDENTIFIER)) return true;
    return false;
  }

  private boolean jj_3R_Terminal_360_5_28()
 {
    if (jj_scan_token(LPAREN)) return true;
    return false;
  }

  private boolean jj_3R_Terminal_360_5_27()
 {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_Terminal_360_5_28()) {
    jj_scanpos = xsp;
    if (jj_3R_Terminal_361_7_29()) {
    jj_scanpos = xsp;
    if (jj_3R_Terminal_362_7_30()) {
    jj_scanpos = xsp;
    if (jj_3R_Terminal_363_7_31()) {
    jj_scanpos = xsp;
    if (jj_3R_Terminal_364_7_32()) {
    jj_scanpos = xsp;
    if (jj_3R_Terminal_365_7_33()) {
    jj_scanpos = xsp;
    if (jj_3R_Terminal_366_7_34()) return true;
    }
    }
    }
    }
    }
    }
    return false;
  }

  private boolean jj_3R_Type_169_7_23()
 {
    if (jj_scan_token(INT)) return true;
    return false;
  }

  private boolean jj_3R_Dot_307_9_25()
 {
    if (jj_3R_Array_347_5_26()) return true;
    return false;
  }

  private boolean jj_3R_Type_166_7_22()
 {
    if (jj_scan_token(BOOLEAN)) return true;
    return false;
  }

  private boolean jj_3_4()
 {
    if (jj_scan_token(LT)) return true;
    if (jj_3R_PlusMinusExpression_269_5_16()) return true;
    return false;
  }

  private boolean jj_3R_VarDeclaration_117_5_14()
 {
    if (jj_3R_Type_163_5_20()) return true;
    if (jj_scan_token(IDENTIFIER)) return true;
    return false;
  }

  private boolean jj_3_2()
 {
    if (jj_scan_token(INT)) return true;
    if (jj_scan_token(LBRACKET)) return true;
    return false;
  }

  private boolean jj_3R_Type_163_5_20()
 {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_2()) {
    jj_scanpos = xsp;
    if (jj_3R_Type_166_7_22()) {
    jj_scanpos = xsp;
    if (jj_3R_Type_169_7_23()) {
    jj_scanpos = xsp;
    if (jj_3R_Type_172_7_24()) return true;
    }
    }
    }
    return false;
  }

  private boolean jj_3R_LessThanExpression_255_5_15()
 {
    if (jj_3R_PlusMinusExpression_269_5_16()) return true;
    return false;
  }

  private boolean jj_3R_Array_347_5_26()
 {
    if (jj_3R_Terminal_360_5_27()) return true;
    return false;
  }

  private boolean jj_3_7()
 {
    if (jj_scan_token(NOT)) return true;
    if (jj_3R_NotExpression_296_5_18()) return true;
    return false;
  }

  private boolean jj_3R_NotExpression_296_5_18()
 {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_7()) {
    jj_scanpos = xsp;
    if (jj_3R_NotExpression_297_24_21()) return true;
    }
    return false;
  }

  private boolean jj_3_3()
 {
    if (jj_scan_token(AND)) return true;
    if (jj_3R_LessThanExpression_255_5_15()) return true;
    return false;
  }

  private boolean jj_3_6()
 {
    if (jj_scan_token(TIMES)) return true;
    if (jj_3R_NotExpression_296_5_18()) return true;
    return false;
  }

  private boolean jj_3R_TimesExpression_283_5_17()
 {
    if (jj_3R_NotExpression_296_5_18()) return true;
    return false;
  }

  private boolean jj_3_1()
 {
    if (jj_3R_VarDeclaration_117_5_14()) return true;
    return false;
  }

  /** Generated Token Manager. */
  public ParserTokenManager token_source;
  JavaCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private Token jj_scanpos, jj_lastpos;
  private int jj_la;
  private int jj_gen;
  final private int[] jj_la1 = new int[20];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
	   jj_la1_init_0();
	   jj_la1_init_1();
	}
	private static void jj_la1_init_0() {
	   jj_la1_0 = new int[] {0x2000,0x0,0x0,0x4000,0x100a00,0x2000000,0x0,0x0,0x400000,0x100a00,0x100a00,0x30000000,0x20000000,0x40000,0x4000000,0x2000000,0x40000,0x400000,0x40000,0x0,};
	}
	private static void jj_la1_init_1() {
	   jj_la1_1 = new int[] {0x0,0x400,0x2030,0x0,0x2200,0x0,0x2030,0x2030,0x0,0x2200,0x2200,0x0,0x0,0x31c4,0x0,0x0,0x31c6,0x0,0x31c4,0x2010,};
	}
  final private JJCalls[] jj_2_rtns = new JJCalls[9];
  private boolean jj_rescan = false;
  private int jj_gc = 0;

  /** Constructor with InputStream. */
  public Parser(java.io.InputStream stream) {
	  this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public Parser(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream = new JavaCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source = new ParserTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 20; i++) jj_la1[i] = -1;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
	  ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 20; i++) jj_la1[i] = -1;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public Parser(java.io.Reader stream) {
	 jj_input_stream = new JavaCharStream(stream, 1, 1);
	 token_source = new ParserTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 20; i++) jj_la1[i] = -1;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
	if (jj_input_stream == null) {
	   jj_input_stream = new JavaCharStream(stream, 1, 1);
	} else {
	   jj_input_stream.ReInit(stream, 1, 1);
	}
	if (token_source == null) {
 token_source = new ParserTokenManager(jj_input_stream);
	}

	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 20; i++) jj_la1[i] = -1;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public Parser(ParserTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 20; i++) jj_la1[i] = -1;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(ParserTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 20; i++) jj_la1[i] = -1;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  private Token jj_consume_token(int kind) throws ParseException {
	 Token oldToken;
	 if ((oldToken = token).next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 if (token.kind == kind) {
	   jj_gen++;
	   if (++jj_gc > 100) {
		 jj_gc = 0;
		 for (int i = 0; i < jj_2_rtns.length; i++) {
		   JJCalls c = jj_2_rtns[i];
		   while (c != null) {
			 if (c.gen < jj_gen) c.first = null;
			 c = c.next;
		   }
		 }
	   }
	   return token;
	 }
	 token = oldToken;
	 jj_kind = kind;
	 throw generateParseException();
  }

  @SuppressWarnings("serial")
  static private final class LookaheadSuccess extends java.lang.Error {
    @Override
    public Throwable fillInStackTrace() {
      return this;
    }
  }
  static private final LookaheadSuccess jj_ls = new LookaheadSuccess();
  private boolean jj_scan_token(int kind) {
	 if (jj_scanpos == jj_lastpos) {
	   jj_la--;
	   if (jj_scanpos.next == null) {
		 jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
	   } else {
		 jj_lastpos = jj_scanpos = jj_scanpos.next;
	   }
	 } else {
	   jj_scanpos = jj_scanpos.next;
	 }
	 if (jj_rescan) {
	   int i = 0; Token tok = token;
	   while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
	   if (tok != null) jj_add_error_token(kind, i);
	 }
	 if (jj_scanpos.kind != kind) return true;
	 if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
	 return false;
  }


/** Get the next Token. */
  final public Token getNextToken() {
	 if (token.next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 jj_gen++;
	 return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
	 Token t = token;
	 for (int i = 0; i < index; i++) {
	   if (t.next != null) t = t.next;
	   else t = t.next = token_source.getNextToken();
	 }
	 return t;
  }

  private int jj_ntk_f() {
	 if ((jj_nt=token.next) == null)
	   return (jj_ntk = (token.next=token_source.getNextToken()).kind);
	 else
	   return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;
  private int[] jj_lasttokens = new int[100];
  private int jj_endpos;

  private void jj_add_error_token(int kind, int pos) {
	 if (pos >= 100) {
		return;
	 }

	 if (pos == jj_endpos + 1) {
	   jj_lasttokens[jj_endpos++] = kind;
	 } else if (jj_endpos != 0) {
	   jj_expentry = new int[jj_endpos];

	   for (int i = 0; i < jj_endpos; i++) {
		 jj_expentry[i] = jj_lasttokens[i];
	   }

	   for (int[] oldentry : jj_expentries) {
		 if (oldentry.length == jj_expentry.length) {
		   boolean isMatched = true;

		   for (int i = 0; i < jj_expentry.length; i++) {
			 if (oldentry[i] != jj_expentry[i]) {
			   isMatched = false;
			   break;
			 }

		   }
		   if (isMatched) {
			 jj_expentries.add(jj_expentry);
			 break;
		   }
		 }
	   }

	   if (pos != 0) {
		 jj_lasttokens[(jj_endpos = pos) - 1] = kind;
	   }
	 }
  }

  /** Generate ParseException. */
  public ParseException generateParseException() {
	 jj_expentries.clear();
	 boolean[] la1tokens = new boolean[48];
	 if (jj_kind >= 0) {
	   la1tokens[jj_kind] = true;
	   jj_kind = -1;
	 }
	 for (int i = 0; i < 20; i++) {
	   if (jj_la1[i] == jj_gen) {
		 for (int j = 0; j < 32; j++) {
		   if ((jj_la1_0[i] & (1<<j)) != 0) {
			 la1tokens[j] = true;
		   }
		   if ((jj_la1_1[i] & (1<<j)) != 0) {
			 la1tokens[32+j] = true;
		   }
		 }
	   }
	 }
	 for (int i = 0; i < 48; i++) {
	   if (la1tokens[i]) {
		 jj_expentry = new int[1];
		 jj_expentry[0] = i;
		 jj_expentries.add(jj_expentry);
	   }
	 }
	 jj_endpos = 0;
	 jj_rescan_token();
	 jj_add_error_token(0, 0);
	 int[][] exptokseq = new int[jj_expentries.size()][];
	 for (int i = 0; i < jj_expentries.size(); i++) {
	   exptokseq[i] = jj_expentries.get(i);
	 }
	 return new ParseException(token, exptokseq, tokenImage);
  }

  private boolean trace_enabled;

/** Trace enabled. */
  final public boolean trace_enabled() {
	 return trace_enabled;
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

  private void jj_rescan_token() {
	 jj_rescan = true;
	 for (int i = 0; i < 9; i++) {
	   try {
		 JJCalls p = jj_2_rtns[i];

		 do {
		   if (p.gen > jj_gen) {
			 jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
			 switch (i) {
			   case 0: jj_3_1(); break;
			   case 1: jj_3_2(); break;
			   case 2: jj_3_3(); break;
			   case 3: jj_3_4(); break;
			   case 4: jj_3_5(); break;
			   case 5: jj_3_6(); break;
			   case 6: jj_3_7(); break;
			   case 7: jj_3_8(); break;
			   case 8: jj_3_9(); break;
			 }
		   }
		   p = p.next;
		 } while (p != null);

		 } catch(LookaheadSuccess ls) { }
	 }
	 jj_rescan = false;
  }

  private void jj_save(int index, int xla) {
	 JJCalls p = jj_2_rtns[index];
	 while (p.gen > jj_gen) {
	   if (p.next == null) { p = p.next = new JJCalls(); break; }
	   p = p.next;
	 }

	 p.gen = jj_gen + xla - jj_la; 
	 p.first = token;
	 p.arg = xla;
  }

  static final class JJCalls {
	 int gen;
	 Token first;
	 int arg;
	 JJCalls next;
  }

                     }
