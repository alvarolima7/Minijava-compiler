package minijavac.Temp;

public class DefaultMap implements TempMap {
    public DefaultMap() {
    }

    public String tempMap(Temp t) {
        return t.toString();
    }
}