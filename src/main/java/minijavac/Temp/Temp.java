package minijavac.Temp;

public class Temp {
    static int count;
    final int num;
    public boolean spillTemp = false;

    public String toString() {
        return "t" + num;
    }

    public Temp() {
        num = count++;
    }
}
