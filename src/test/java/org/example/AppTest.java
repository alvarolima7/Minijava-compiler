package org.example;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import minijavac.MiniJavaCompiler;
import minijavac.LexerParser.ParseException;
import minijavac.SemanticAnalysis.SemanticException;
import minijavac.SemanticAnalysis.Error;

import java.io.IOException;

public class AppTest extends TestCase {

    public AppTest( String testName )
    {
        super( testName );
    }

    public static Test suite() {
        return new TestSuite( AppTest.class );
    }

    public void testSuccess() throws IOException, ParseException, SemanticException {

        Error.anySemanticError = false;

        String[] filesToCompile = {
            "src/main/resources/BinaryTree.java",
            "src/main/resources/Factorial.java",
            "src/main/resources/LinearSearch.java",
            "src/main/resources/LinkedList.java",
            "src/main/resources/QuickSort.java",
            "src/main/resources/TreeVisitor.java"
        };

        for (String fileToCompile : filesToCompile) {
            MiniJavaCompiler.compile(fileToCompile);
            System.out.println(fileToCompile);
        }

        assertTrue(true);
    }

    public void testError() {

        String[] filesToCompile = {
                "src/main/resources/FactorialWithSemanticError.java",
                "src/main/resources/VarDeclarationsWithSemanticError.java",
                "src/main/resources/ClassExtendWithSemanticError.java",
                "src/main/resources/ExpressionsWithSemanticError.java",
                "src/main/resources/MoreSemanticErrors.java"
        };

        int failCounter = 0;

        for (String fileToCompile : filesToCompile) {
            try {
                System.out.println(fileToCompile);
                MiniJavaCompiler.compile(fileToCompile);
            }
            catch (Exception e){
                failCounter++;
            }
        }

        assertEquals(filesToCompile.length, failCounter);
    }
}
