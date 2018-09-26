/**
 * java does not really have a simple pair/tuple class.
 * so here is a simple tuple that stores two types that can be references by first and second.
 * @param <T> some type t
 * @param <B> some type b
 */
public class Tuple<T, B> {
    public T first;
    public B second;

    public Tuple(T first, B second) {
        this.first = first;
        this.second = second;
    }
}
