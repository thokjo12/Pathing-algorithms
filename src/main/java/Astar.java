import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Astar {
    PriorityQueue open;
    List<Node> closed;
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
        closed = new ArrayList<>();
        open.add(new Tuple<>(start, 0.0));
    }

    /**
     * manhattan
     *
     * @param a
     * @param b
     * @return
     */
    public double heuristic(Node a, Node b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    /**
     *
     */
    public void run() {
        Node current;
        HashMap<Node, Node> came_from = new HashMap<>();

        while (!open.isEmpty()) {
            current = open.pop().first;

            if (current == goal) {
                break;
            }
            if (current.passable) {
                for (Node neighbor : current.neighbors) {
                    break;
                }
            }else{
                closed.add(current);
            }
        }
    }

}
