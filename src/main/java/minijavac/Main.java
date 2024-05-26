package minijavac;

import java.util.logging.*;

public class Main {
    public static void main(String[] args) {
        try {
            String fileToCompile = args[0];
            MiniJavaCompiler.compile(fileToCompile);
            Logger.getGlobal().log(Level.INFO, "{0} compiled successfully.", fileToCompile);
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, "Compilation failed.", e);
        }
    }
}
