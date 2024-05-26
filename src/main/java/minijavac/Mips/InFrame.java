package minijavac.Mips;

import minijavac.Tree.*;
import minijavac.Frame.Access;

public class InFrame extends Access {
    int offset;
    InFrame(int o) {
        offset = o;
    }

    public Expr exp(Expr fp) {
        return new MEM
                (new BINOP(BINOP.PLUS, fp, new CONST(offset)));
    }

    public String toString() {
        return String.valueOf(offset);
    }
}