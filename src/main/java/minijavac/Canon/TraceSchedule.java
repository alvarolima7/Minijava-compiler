package minijavac.Canon;

import minijavac.Temp.Label;
import minijavac.Tree.*;

import java.util.Dictionary;
import java.util.Hashtable;

public class TraceSchedule {
    public StmList stms;
    BasicBlocks basicBlocks;
    Dictionary table = new Hashtable();

    public TraceSchedule(BasicBlocks b) {
        basicBlocks = b;
        for(StmListList l = b.blocks; l!=null; l=l.tail)
            table.put(((LABEL)l.head.head).label, l.head);
        stms=getNext();
        table=null;
    }

    StmList getLast(StmList block) {
        StmList l=block;
        while (l.tail.tail!=null)  l=l.tail;
        return l;
    }

    void trace(StmList l) {
        for(;;) {
            LABEL lab = (LABEL)l.head;
            table.remove(lab.label);
            StmList last = getLast(l);
            Stm s = last.tail.head;

            if (s instanceof JUMP j) {
                StmList target = (StmList)table.get(j.targets.head);
                if (j.targets.tail==null && target!=null) {
                    last.tail=target;
                    l=target;
                }
                else {
                    last.tail.tail=getNext();
                    return;
                }
            } else if (s instanceof CJUMP j) {
                StmList t = (StmList)table.get(j.iftrue);
                StmList f = (StmList)table.get(j.iffalse);
                if (f!=null) {
                    last.tail.tail=f;
                    l=f;
                }
                else if (t!=null) {
                    last.tail.head=new CJUMP(CJUMP.notRel(j.relop),
                            j.left,j.right,
                            j.iffalse,j.iftrue);
                    last.tail.tail=t;
                    l=t;
                }
                else {
                    Label ff = new Label();
                    last.tail.head=new CJUMP(j.relop,j.left,j.right,
                            j.iftrue,ff);
                    last.tail.tail=new StmList(new LABEL(ff),
                            new StmList(new JUMP(j.iffalse),
                                    getNext()));
                    return;
                }
            }
            else throw new Error("Bad basic block in TraceSchedule");
        }
    }

    StmList getNext() {
        if (basicBlocks.blocks==null)
            return new StmList(new LABEL(basicBlocks.done), null);
        else {
            StmList s = basicBlocks.blocks.head;
            LABEL lab = (LABEL)s.head;
            if (table.get(lab.label) != null) {
                trace(s);
                return s;
            }
            else {
                basicBlocks.blocks = basicBlocks.blocks.tail;
                return getNext();
            }
        }
    }
}