package minijavac.Canon;

import minijavac.Temp.Label;
import minijavac.Tree.*;

public class BasicBlocks {
    public StmListList blocks;
    public Label done;
    private StmListList lastBlock;
    private StmList lastStm;

    public BasicBlocks(StmList stms) {
        done = new Label();
        mkBlocks(stms);
    }

    private void addStm(Stm s) {
        lastStm = lastStm.tail = new StmList(s,null);
    }

    private void doStms(StmList l) {
        if (l==null)
            doStms(new StmList(new JUMP(done), null));
        else if (l.head instanceof JUMP || l.head instanceof CJUMP) {
            addStm(l.head);
            mkBlocks(l.tail);
        } else if (l.head instanceof LABEL lHead)
            doStms(new StmList(new JUMP((lHead).label), l));
        else {
            addStm(l.head);
            doStms(l.tail);
        }
    }

    void mkBlocks(StmList l) {
        if (l == null) return;

        if (l.head instanceof LABEL) {
            lastStm = new StmList(l.head,null);
            if (lastBlock == null) lastBlock = blocks = new StmListList(lastStm,null);
            else lastBlock = lastBlock.tail = new StmListList(lastStm,null);
            doStms(l.tail);
            return;
        }

        mkBlocks(new StmList(new LABEL(new Label()), l));
    }
}