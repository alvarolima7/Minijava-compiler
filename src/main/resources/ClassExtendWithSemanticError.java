class Main {
    public static void main(String[] a) {
        System.out.println(new A());
    }
}

class A extends B {

}

class B extends C {

}

class C extends A {

}