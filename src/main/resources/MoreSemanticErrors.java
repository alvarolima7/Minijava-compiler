class Teste {
    public static void main(String[] args){
        System.out.println(new SemanticErrors().Start());
    }
}

class SemanticErrors{
    TipoInexistente a;
    int[] d;

    public Azul func0(){
        return 1;
    }

    public int func1(int c){
        return c;
    }

    public int Start(){
        int b;
        int v2;
        SemanticErrors obj;
        v2 = obj.func1();
        b[0] = 1;
        d[0] = d;
        return v2;
    }
}