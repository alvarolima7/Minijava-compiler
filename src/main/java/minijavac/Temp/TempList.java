package minijavac.Temp;

import java.util.Arrays;

public class TempList {
    public Temp head;
    public TempList tail;

    public TempList(Temp[] temps) {
        if (temps == null || temps.length == 0)
            return;

        head = temps[0];
        tail = new TempList(Arrays.copyOfRange(temps, 1, temps.length));
    }

    public TempList(Temp h, TempList t) {
        head = h;
        tail = t;
    }
}

