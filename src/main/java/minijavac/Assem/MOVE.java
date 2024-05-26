package minijavac.Assem;

import minijavac.Temp.Temp;
import minijavac.Temp.TempList;

public class MOVE extends Instr {
    public Temp dst;
    public Temp src;

    public MOVE(String a, Temp d, Temp s) {
        assem = a;
        dst = d;
        src = s;

        use = new Temp[]{ s };
        def = new Temp[]{ d };
        jumps = null;
    }

    public TempList use() {
        return new TempList(src, null);
    }

    public TempList def() {
        return new TempList(dst, null);
    }

    public Targets jumps() {
        return null;
    }

}