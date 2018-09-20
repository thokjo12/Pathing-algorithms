import java.util.ArrayList;
import java.util.Comparator;

public class PriorityQueue {
    private ArrayList<Tuple<Node, Double>> ts = new ArrayList<>();

    public PriorityQueue() {
    }

    public void add(Tuple<Node, Double> node) {
        ts.add(node);
        ts.sort(Comparator.comparingDouble(o -> o.second));
    }

    public Tuple<Node, Double> pop() {
        Tuple<Node, Double> node = ts.get(0);
        ts.remove(node);
        return node;
    }

    public boolean isEmpty() {
        return ts.isEmpty();
    }

}
