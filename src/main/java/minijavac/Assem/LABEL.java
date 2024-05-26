package minijavac.Assem;

import minijavac.Temp.Label;
import minijavac.Temp.TempList;

public class LABEL extends Instr {
    public Label label;

    public LABEL(String a, Label l) {
        assem = a;
        label = l;

        use = null;
        def = null;
        jumps = null;
    }

    public TempList use() {
        return null;
    }

    public TempList def() {
        return null;
    }

    public Targets jumps() {
        return null;
    }
}
