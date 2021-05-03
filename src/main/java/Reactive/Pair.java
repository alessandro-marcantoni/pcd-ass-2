package Reactive;

public class Pair<T,U> {

    private final T first;
    private final U second;

    private Pair(final T f, final U s) {
        this.first = f;
        this.second = s;
    }

    public static <T,U> Pair<T,U> of(final T f, final U s) {
        return new Pair<>(f, s);
    }

    public T getFirst() {
        return this.first;
    }

    public U getSecond() {
        return this.second;
    }

}
