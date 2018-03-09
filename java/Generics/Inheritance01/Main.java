/**
 * Main
 */
public class Main {
    public static void main(String[] args) {
        Base<Integer> i = new Base<>(123);
        i.print();

        Super<Integer, Double> id = new Super<>(123, 1.23);
        id.print();
    }
}

class Base<T> {
    private T t;

    public Base(T t) {
        this.t = t;
    }

    public void print() {
        System.out.printf("Base class output: %s.\n", t.toString());
    }
}

class Super<T, S> extends Base<T>{
    private S s;

    public Super(T t, S s) {
        super(t);
        this.s = s;
    }

    public void print() {
        super.print();
        System.out.printf("Super class output: %s.\n", s.toString());
    }
}

