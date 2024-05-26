package minijavac.Assem;

import minijavac.Mips.MipsFrame;
import minijavac.Tree.*;
import minijavac.Temp.*;
import minijavac.Tree.LABEL;
import minijavac.Tree.MOVE;

public class Codegen {
    MipsFrame frame;
    InstrList head = null, tail = null;

    public Codegen(MipsFrame frame) {
        this.frame = frame;
    }

    public InstrList codegen(Stm stm) {
        munchStm(stm);
        InstrList instrList = head;
        head = tail = null;

        return instrList;
    }

    void emit(Instr inst) {
        if (tail != null)
            tail = tail.tail = new InstrList(inst, null);
        else
            tail = head = new InstrList(inst, null);
    }

    void munchStm(Stm stm) {
        switch (stm) {
            case CJUMP cjump -> munchCJUMP(cjump);
            case LABEL label -> munchLABEL(label);
            case MOVE move -> munchMOVE(move);
            case JUMP jump -> munchJUMP(jump);
            case EXP exp -> munchCALL((CALL)exp.exp);
            case SEQ seq -> {
                munchStm(seq.left);
                munchStm(seq.right);
            }
            default -> throw new Error("Invalid statement type " + stm.getClass().getSimpleName());
        }
    }

    void munchCJUMP(CJUMP cjump) {
        Temp r = munchExpr(new BINOP(BINOP.MINUS, cjump.left, cjump.right));
        switch (cjump.relop) {
            case CJUMP.EQ -> emit(new OPER("beq `s0,$zero,`j0\n", null, new TempList(r, null), new LabelList(cjump.iftrue, null)));
            case CJUMP.LT -> emit(new OPER("blt `s0,$zero,`j0\n", null, new TempList(r, null), new LabelList(cjump.iftrue, null)));
            default -> throw new Error("Invalid relop " + cjump.relop);
        }
    }

    void munchLABEL(LABEL label) {
        emit(new minijavac.Assem.LABEL(label.label.toString(), label.label));
    }

    void munchMOVE(MOVE move) {
        if (move.dst instanceof MEM mem)
            munchMOVE_MEM(mem, move.src);

        else if (move.dst instanceof TEMP && move.src instanceof CALL call) {
            Temp r = munchExpr(call.func);
            TempList l = munchArgs(call.args);
            if (call.func instanceof NAME name)
                emit(new OPER("jal " + name.label.toString() + '\n', new TempList(r, null), l, null));
        }

        else if (move.dst instanceof TEMP temp)
            emit(new minijavac.Assem.MOVE("move `s0,`d0\n", temp.temp, munchExpr(move.src)));
    }

    void munchJUMP(JUMP jump) {
        emit(new OPER("j `j0\n", null, null, jump.targets));
    }

    void munchMOVE_MEM(MEM dst, Expr src) {
        if (dst.exp instanceof BINOP binop) {
            if (binop.binop == BINOP.PLUS && binop.right instanceof CONST const_) {
                TempList dstTemps = new TempList(munchExpr(binop.left), null);
                TempList srcTemps = new TempList(munchExpr(src), null);
                emit(new OPER("sw `s0," + const_.value + "(`d0)\n", dstTemps, srcTemps, null));
            }

            else if (binop.binop == BINOP.PLUS && binop.left instanceof CONST const_) {
                TempList dstTemps = new TempList(munchExpr(binop.right), null);
                TempList srcTemps = new TempList(munchExpr(src), null);
                emit(new OPER("sw `s0," + const_.value + "(`d0)\n", dstTemps, srcTemps, null));
            }
        }

        else if (src instanceof MEM mem) {
            TempList dstTemps = new TempList(munchExpr(dst.exp), null);
            TempList srcTemps = new TempList(munchExpr(mem.exp), null);
            emit(new OPER("move `d0, `s0\n", dstTemps, srcTemps, null));
        }

        else if (dst.exp instanceof CONST const_) {
            TempList srcTemps = new TempList(munchExpr(src), null);
            TempList dstTemps = new TempList(munchExpr(dst.exp), null);
            emit(new OPER("sw `s0," + const_.value + "(`d0)\n", dstTemps, srcTemps, null));
        }

        else {
            TempList dstTemps = new TempList(munchExpr(dst.exp), null);
            TempList srcTemps = new TempList(munchExpr(src), null);
            emit(new OPER("sw `s0, 0(`d0)\n", dstTemps, srcTemps, null));
        }
    }

    TempList munchArgs(ExprList args) {
        TempList out = null;
        ExprList tmp = args;

        while (tmp != null) {
            out = new TempList(munchExpr(tmp.head), out);
            tmp = tmp.tail;
        }

        return out;
    }

    void munchCALL(CALL call) {
        Temp r = munchExpr(call.func);
        TempList l = munchArgs(call.args);
        if (call.func instanceof NAME name)
            emit(new OPER("jal " + name.label.toString() + '\n', new TempList(r, null), l, null));
    }

    Temp munchExpr(Expr expr) {
        return switch (expr) {
            case CONST const_ -> munchCONST(const_);
            case BINOP binop -> munchBINOP(binop);
            case MEM mem -> munchMEM(mem);
            case NAME name -> new Temp();
            case TEMP temp -> temp.temp;
            default -> throw new Error("Invalid expression type " + expr.getClass().getSimpleName());
        };
    }

    Temp munchMEM(MEM mem) {
        Temp r = new Temp();

        switch (mem.exp) {
            case CONST const_ -> {
                TempList dstTemps = new TempList(r, null);
                TempList srcTemps = new TempList(munchExpr(const_), null);
                emit(new OPER("lw `d0,`s0\n", dstTemps, srcTemps, null));
            }
            case BINOP binop -> {
                if (binop.binop == BINOP.PLUS && binop.right instanceof CONST const_) {
                    TempList dstTemps = new TempList(r, null);
                    TempList srcTemps = new TempList(munchExpr(binop.left), null);
                    emit(new OPER("lw `d0," + const_.value + "(`s0)\n", dstTemps, srcTemps, null));
                }

                else if (binop.binop == BINOP.PLUS && binop.left instanceof CONST const_) {
                    TempList dstTemps = new TempList(r, null);
                    TempList srcTemps = new TempList(munchExpr(binop.right), null);
                    emit(new OPER("lw `d0," + const_.value + "(`s0)\n", dstTemps, srcTemps, null));
                }

            }
            default -> {
                TempList dstTemps = new TempList(r, null);
                TempList srcTemps = new TempList(munchExpr(mem.exp), null);
                emit(new OPER("lw `d0,0(`s0)\n", dstTemps, srcTemps, null));
            }
        }

        return r;
    }

    Temp munchCONST(CONST const_) {
        Temp t = new Temp();
        emit(new OPER("li `d0, " + const_.value + '\n', new TempList(t, null), null, null));

        return t;
    }

    Temp munchBINOP(BINOP binop) {
        Temp r = new Temp();

        switch (binop.binop) {
            case BINOP.PLUS -> {
                if (binop.right instanceof CONST const_) {
                    TempList src = new TempList(munchExpr(binop.left), null);
                    emit(new OPER("addi `d0,`s0," + const_.value + "\n", new TempList(r, null), src));
                }

                else if (binop.left instanceof CONST const_) {
                    TempList src = new TempList(munchExpr(binop.right), null);
                    emit(new OPER("addi `d0,`s0," + const_.value + "\n", new TempList(r, null), src));
                }

                else {
                    TempList src = new TempList(munchExpr(binop.left), new TempList(munchExpr(binop.right), null));
                    emit(new OPER("add `d0,`s0,`s1\n", new TempList(r, null), src));
                }
            }
            case BINOP.MUL -> {
                TempList src = new TempList(munchExpr(binop.left), new TempList(munchExpr(binop.right), null));
                emit(new OPER("mul `d0,`s0,`s1\n", new TempList(r, null), src));
            }
            case BINOP.MINUS -> {
                TempList src = new TempList(munchExpr(binop.left), new TempList(munchExpr(binop.right), null));
                if (binop.right instanceof CONST)
                    emit(new OPER("sub `d0,`s0,`s1\n", new TempList(r, null), src));
                else
                    emit(new OPER("sub `d0,`s0, `s1\n", new TempList(r, null), src));
            }
            case BINOP.DIV -> {
                TempList src = new TempList(munchExpr(binop.left),new TempList(munchExpr(binop.right),null));
                emit(new OPER("div `d0,`s0,`s1\n", new TempList(r, null), src));
            }
            case BINOP.AND -> {
                TempList src = new TempList(munchExpr(binop.left), new TempList(munchExpr(binop.right), null));
                emit(new OPER("and `d0,`s0,`s1\n", new TempList(r, null), src));
            }
            case BINOP.OR -> {
                TempList src = new TempList(munchExpr(binop.left), new TempList(munchExpr(binop.right), null));
                emit(new OPER("or `d0,`s0,`s1\n", new TempList(r, null), src));
            }
            case BINOP.XOR -> {
                TempList src = new TempList(munchExpr(binop.left), new TempList(munchExpr(binop.right), null));
                emit(new OPER("xor `d0,`s0,`s1\n", new TempList(r, null), src));
            }
            default -> throw new Error("Invalid binop " + binop.binop);
        }

        return r;
    }
}
