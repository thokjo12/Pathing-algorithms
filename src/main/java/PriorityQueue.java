import java.util.ArrayList;
import java.util.Comparator;

/**
 * a list masquerading as a queue with prioritized elements.
 * uses the sort function based on the priority to sort the list.
 */
public class PriorityQueue {
    private ArrayList<Tuple<Node, Double>> ts = new ArrayList<>();

    public PriorityQueue() {
    }

    /**
     * adds a  node to the queue, location in the queue depends on the priority
     * @param node the tuple node to add.
     */
    public void add(Tuple<Node, Double> node) {
        if (contains(node.first)) {
            updateEntry(node);
        } else {
            ts.add(node);
        }

        ts.sort(Comparator.comparingDouble(o -> o.second));
    }

    /**
     * updates a tuple based on x and y coordinates.
     * @param tupNode the tuple with the priority and some node
     */
    private void updateEntry(Tuple<Node, Double> tupNode) {
        for (Tuple<Node, Double> tuple : ts) {
            if (tuple.first.y == tupNode.first.y && tuple.first.x == tupNode.first.x) {
                tuple.second = tupNode.second;
            }
        }
        ts.sort(Comparator.comparingDouble(o -> o.second));
    }

    /**
     * search for a tuple based on a node
     * @param node the node to find
     * @return the node.
     */
    public Tuple<Node, Double> findEntry(Node node) {
        for (Tuple<Node, Double> tuple : ts) {
            if (tuple.first.y == node.y && tuple.first.x == node.x) {
                return tuple;
            }
        }
        ts.sort(Comparator.comparingDouble(o -> o.second));
        return null;
    }

    /**
     * remove a node from the list
     * @param node the node to remove
     */
    public void remove(Node node) {
        for (Tuple<Node, Double> tuple : ts) {
            if (tuple.first.y == node.y && tuple.first.x == node.x) {
                ts.remove(tuple);
            }
        }
        ts.sort(Comparator.comparingDouble(o -> o.second));
    }

    /**
     * check if the node is in the queue
     * @param node the node to check if its here
     * @return if here true, else false
     */
    private boolean contains(Node node) {
        for (Tuple<Node, Double> tuple : ts) {
            if (tuple.first.y == node.y && tuple.first.x == node.x) {
                return true;
            }
        }
        return false;
    }

    /**
     * pop from queue
     * @return the tuple with the node and priority
     */
    public Tuple<Node, Double> pop() {
        Tuple<Node, Double> node = ts.get(0);
        ts.remove(node);
        return node;
    }

    /**
     * check if empty
     */
    public boolean isEmpty() {
        return ts.isEmpty();
    }

}
