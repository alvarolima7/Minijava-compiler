package minijavac;

import minijavac.Canon.*;
import minijavac.Frame.Frame;
import minijavac.LexerParser.*;
import minijavac.Mips.MipsFrame;
import minijavac.Translate.IRVisitor;
import minijavac.SemanticAnalysis.*;
import minijavac.SemanticAnalysis.Error;
import minijavac.SyntaxTree.Program;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class MiniJavaCompiler {
    private MiniJavaCompiler() {}

    public static void compile(String fileToCompilePath) throws IOException, ParseException, SemanticException {
        validateMiniJavaExtension(fileToCompilePath);
        Program program = createParseTree(fileToCompilePath);
        TypeCheckVisitor typeCheckVisitor = new TypeCheckVisitor(program);
        program.accept(typeCheckVisitor);

        if (Error.anySemanticError)
            throw new SemanticException();

        Frame frame = new MipsFrame();
        IRVisitor irVisitor = new IRVisitor(frame, typeCheckVisitor);
        program.accept(irVisitor);

        var frags = irVisitor.getResult();

        var traceList = frags.stream()
                .flatMap(frag -> frag.body.stream().map(stm -> new TraceSchedule(new BasicBlocks(Canon.linearize(stm)))))
                .toList();

        var instrList = traceList.stream()
                .flatMap(trace -> frame.codegen(trace.stms).stream())
                .toList();

        instrList.forEach(instr -> System.out.print(instr.assem));
    }

    private static Program createParseTree(String fileToCompilePath) throws IOException, ParseException {
        InputStream fileInputStream = getFileInputStream(fileToCompilePath);
        Parser parser = new Parser(fileInputStream);
        return parser.Start();
    }

    private static void validateMiniJavaExtension(String filename) throws IOException {
        String fileExtension = filename.substring(filename.lastIndexOf(".") + 1);
        if (!fileExtension.equals("java"))
            throw new IOException("File must be a .java file.");
    }

    public static InputStream getFileInputStream(String fileName) throws IOException {
        File file = new File(fileName);
        return Files.newInputStream(file.toPath());
    }
}
