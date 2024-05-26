class Teste {
    public static void main(String[] args){
        System.out.println(new Quadrado().func0());
    }
}

class Quadrado{
    int a;
    int b;
    int[] d;
    int e;

    public int func0(){
        a = 3;
        b = 2;
        return a+b;
    }

    public int func1(int c){
        return a+b+c;
    }

    public int func2(){
        return a+b;
    }
}