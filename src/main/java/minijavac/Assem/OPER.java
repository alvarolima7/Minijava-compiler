package minijavac.Assem;

import minijavac.Temp.Label;
import minijavac.Temp.LabelList;
import minijavac.Temp.Temp;
import minijavac.Temp.TempList;

import java.util.ArrayList;

public class OPER extends Instr {
    public TempList dst;
    public TempList src;
    public Targets jump;

    public OPER(String a, TempList d, TempList s, LabelList j) {
        assem = a;
        dst = d;
        src = s;
        jump = new Targets(j);

        ArrayList<Temp> useArrList = new ArrayList<>();
        TempList currSrc = s;
        while (currSrc != null) {
            useArrList.add(currSrc.head);
            currSrc = currSrc.tail;
        }
        use = useArrList.toArray(new Temp[0]);

        ArrayList<Temp> defArrList = new ArrayList<>();
        TempList currDst = d;
        while (currDst != null) {
            defArrList.add(currDst.head);
            currDst = currDst.tail;
        }
        def = defArrList.toArray(new Temp[0]);

        ArrayList<Label> jumpArrList = new ArrayList<>();
        LabelList currJump = j;
        while (currJump != null) {
            jumpArrList.add(currJump.head);
            currJump = currJump.tail;
        }
        jumps = jumpArrList.toArray(new Label[0]);
    }

    public OPER(String a, TempList d, TempList s) {
        assem = a;
        dst = d;
        src = s;
        jump = null;
    }

    public TempList use() {
        return src;
    }

    public TempList def() {
        return dst;
    }

    public Targets jumps() {
        return jump;
    }

}