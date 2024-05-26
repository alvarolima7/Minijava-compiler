package minijavac.Mips;

import minijavac.Temp.Temp;
import minijavac.Tree.Expr;
import minijavac.Tree.TEMP;
import minijavac.Frame.Access;

public class InReg extends Access {
    Temp temp;
    InReg(Temp t) {
        temp = t;
    }

    public Expr exp(Expr fp) {
        return new TEMP(temp);
    }

    public String toString() {
        return temp.toString();
    }
}