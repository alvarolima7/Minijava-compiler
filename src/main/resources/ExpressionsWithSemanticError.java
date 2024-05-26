class Teste {
    public static void main(String[] args){
        System.out.println(new Operacoes().Start());
    }
}

class Operacoes{
    int a;
    boolean b;
    int[] d;

    public int sub(){
        return b-d;
    }

    public int and(){
        return b&&a;
    }

    public int se(){
        int z;
        if(a){
            z = 1;
        }
        else{
            z = 0;
        }
        return z;
    }

    public int Start(){
        return 1;
    }
}