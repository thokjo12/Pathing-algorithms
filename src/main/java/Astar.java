import java.util.*;

public class Astar {
    PriorityQueue open;
    Node start;
    Node goal;

    /**
     * @param start
     * @param goal
     */
    public Astar(Node start, Node goal) {
        this.goal = goal;
        this.start = start;
        open = new PriorityQueue();
        open.add(new Tuple<>(start, 0.0));
    }

    /**
     * manhattan
     *
     * @param a
     * @param b
     * @return
     */
    public double h(Node a, Node b) {
        return (Math.abs(a.x - b.x) + Math.abs(a.y - b.y));
    }

    HashMap<Node, Node> parent = new HashMap<>();
    HashMap<Node, Double> costSoFar = new HashMap<>();

    /**
     *
     */
    public HashMap<Node, Node> run() {
        Node current;
        parent.put(start, start);
        costSoFar.put(start, 0.0);
        while (!open.isEmpty()) {
            current = open.pop().first;
            if (current == goal) {
                break;
            }
            current.type = "D";
            for (Node next : current.neighbors) {

                if (next != null && next.passable) {
                    double g = costSoFar.get(current) + next.cost;

                    if (!costSoFar.containsKey(next) || g < costSoFar.get(next)) {
                        costSoFar.put(next, g);
                        double f = g + h(next, goal);

                        open.add(new Tuple<>(next, f));

                        parent.put(next, current);
                    }
                }
            }

        }
        return parent;
    }


    public List<Node> reconstruct() {
        List<Node> path = new ArrayList<>();
        Node current = goal;
        while (current != start) {
            path.add(current);
            current = parent.get(current);
        }
        System.out.println(parent.size());
        Collections.reverse(path);
        return path;
    }
}
