package minijavac.Translate;

import minijavac.Frame.Frame;
import minijavac.Tree.Stm;

import java.util.List;

public class ProcFrag {
    public List<Stm> body;
    public Frame frame;

    public ProcFrag(List<Stm> body, Frame frame) {
        this.body = body;
        this.frame = frame;
    }
}
