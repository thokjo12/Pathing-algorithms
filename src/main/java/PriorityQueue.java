import java.util.ArrayList;
import java.util.Comparator;

public class PriorityQueue {
    private ArrayList<Tuple<Node, Double>> ts = new ArrayList<>();

    public PriorityQueue() {
    }

    public void add(Tuple<Node, Double> node) {
        if (contains(node.first)) {
            updateEntry(node);
        } else {
            ts.add(node);
        }

        ts.sort(Comparator.comparingDouble(o -> o.second));
    }

    private void updateEntry(Tuple<Node, Double> tupNode) {
        for (Tuple<Node, Double> tuple : ts) {
            if (tuple.first.y == tupNode.first.y && tuple.first.x == tupNode.first.x) {
                tuple.second = tupNode.second;
            }
        }
        ts.sort(Comparator.comparingDouble(o -> o.second));
    }


    public Tuple<Node, Double> findEntry(Node node) {
        for (Tuple<Node, Double> tuple : ts) {
            if (tuple.first.y == node.y && tuple.first.x == node.x) {
                ts.remove(tuple);
                return tuple;
            }
        }
        ts.sort(Comparator.comparingDouble(o -> o.second));
        return null;
    }

    public void remove(Node node) {
        for (Tuple<Node, Double> tuple : ts) {
            if (tuple.first.y == node.y && tuple.first.x == node.x) {
                ts.remove(tuple);
            }
        }
        ts.sort(Comparator.comparingDouble(o -> o.second));
    }

    private boolean contains(Node node) {
        for (Tuple<Node, Double> tuple : ts) {
            if (tuple.first.y == node.y && tuple.first.x == node.x) {
                return true;
            }
        }
        return false;
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
