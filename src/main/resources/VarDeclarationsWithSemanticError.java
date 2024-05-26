class Teste {
    public static void main(String[] args){
        System.out.println(new Operacoes().Start());
    }
}

class Operacoes{
    int a;
    int b;
    boolean c;
    int[] d;

    public int func0(){
        e = 3;
        return a+b+e;
    }

    public int func1(int c){
        return a+b+c;
    }

    public int func2(){
        return a+b+c;
    }

    public int Start(){
        Operacoes obj;
        int v1;
        int v2;
        int v3;
        v1 = obj.func0();
        v2 = obj.func1(3);
        v3 = obj.func2();
        return v1 + v2 + v3;
    }
}