package minijavac.SemanticAnalysis;

public class Error {
    public static boolean anySemanticError = false;

    public static void alreadyDeclaredLocal(String methodName, String varName) {
        anySemanticError = true;
        System.out.println("Error: Var " + varName + " already declared in method " + methodName);
    }

    public static void alreadyDeclaredField(String className, String fieldName) {
        anySemanticError = true;
        System.out.println("Error: field " + fieldName + " already declared in class " + className);
    }

    public static void alreadyDeclaredParameter(String methodName, String paramName) {
        anySemanticError = true;
        System.out.println("Error: Parameter " + paramName + " already declared in method " + methodName);
    }

    public static void alreadyDeclaredMethod(String className, String methodName) {
        anySemanticError = true;
        System.out.println("Error: method " + methodName + " already declared in class " + className);
    }

    public static void alreadyDeclaredClass(String className) {
        anySemanticError = true;
        System.out.println("Error: class " + className + " was already declared");
    }

    public static void undefinedClass(String className) {
        anySemanticError = true;
        System.out.println("Error: class " + className + " was not declared");
    }

    public static void undefinedType(String className, String methodName, String typeName) {
        anySemanticError = true;
        System.out.println("Error: On " + className + "." + methodName + " has undefined type " + typeName);
    }

    public static void alreadyDeclaredVariable(String className, String methodName, String varName) {
        anySemanticError = true;
        System.out.println("Error: On " + className + "." + methodName + " has already declared variable " + varName);
    }

    public static void incompatibleReturnType(String className, String methodName, String exprectedReturn, String got) {
        anySemanticError = true;
        System.out.println("Error: On " + className + "." + methodName + " has incompatible return type. Expected " + exprectedReturn + " but got " + got);
    }

    public static void incompatibleType(String className, String methodName, String expected, String got) {
        anySemanticError = true;
        System.out.println("Error: On " + className + "." + methodName + " has incompatible type. Expected " + expected + " but got " + got);
    }

    public static void undefinedVariable(String className, String methodName, String varName) {
        anySemanticError = true;
        System.out.println("Error: On " + className + "." + methodName + " has undefined variable " + varName);
    }

    public static void undefinedMethod(String className, String methdoName, String undefinedMethod) {
        anySemanticError = true;
        System.out.println("Error: On " + className + "." + methdoName + " has undefined method " + undefinedMethod);
    }

    public static void incompatibleNumberOfArguments(String className, String methodName, String calledMethod, int numArgsExpected, int got) {
        anySemanticError = true;
        System.out.println("Error: On " + className + "." + methodName + " has incompatible number of arguments on method " + calledMethod + ". Expected " + numArgsExpected + " but got " + got);
    }
}
