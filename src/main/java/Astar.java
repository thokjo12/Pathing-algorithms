import java.util.*;

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
    public HashMap<Node, Node> run() {
        Node current;
        HashMap<Node, Node> cameFrom = new HashMap<>();
        HashMap<Node, Double> costSoFar = new HashMap<>();
        cameFrom.put(start, start);
        costSoFar.put(start, 0.0);

        while (!open.isEmpty()) {
            current = open.pop().first;
            if (current == goal) {
                break;
            }
            if(!current.type.equals("A")){
                current.type ="O";
            }

            if (current.passable) {

                for (Node neighbor : current.neighbors) {
                    if (neighbor != null && neighbor.passable) {
                        double cost = costSoFar.get(current) + neighbor.cost;
                        if (!costSoFar.containsKey(neighbor) || cost < costSoFar.get(neighbor)) {

                            costSoFar.put(neighbor, cost);
                            double priority = cost + heuristic(neighbor, goal);
                            open.add(new Tuple<>(neighbor, priority));
                            cameFrom.put(neighbor, current);
                        }
                    }
                }
            } else {
                closed.add(current);
            }
        }
        return cameFrom;
    }
}
