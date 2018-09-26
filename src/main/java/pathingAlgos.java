import java.util.*;

/**
 * implementation based on Amit Patel's work over at red blob games
 * https://www.redblobgames.com/pathfinding/a-star/introduction.html
 * https://www.redblobgames.com/pathfinding/a-star/implementation.html
 * <p>
 * one thing i would like to implement here is a tiebreaker to resolve the issues with the priority queue filling up
 * with nodes that has the same priority.
 * Tie breaker would be some number that is dependent on the graph which be multiplied by the heuristic this could
 * effectively reduce the nodes searched. The current issue is that when the priority queue is filled up with entries of
 * equal prioriy, it aStar can become dijkstra until the nodes with equal priority are reduced to 1.
 */
public class pathingAlgos {
    /**
     * manhattan distance, works great for grids that only allow left right top or bottom movement.
     *
     * @param a node a
     * @param b node b
     * @return the manhattan distance to b from a
     */
    public static double heuristic(Node a, Node b) {
        return (Math.abs(a.x - b.x) + Math.abs(a.y - b.y));
    }


    /**
     * construct two hashmaps, costSoFar(node, double) and parent(node, node).
     * create open as a priority queue of type tuple(node, double) where the double is the priority .
     * push start to open .
     * put start into parent, with itself as parent
     * and put start to costSoFar with cost = 0.
     * <p>
     * starts a loop that runs while open still has nodes.
     * breaks if node is the goal .
     * <p>
     * for current, iterate over its neighbors,
     * check if the neighbor is not null and is passable ( that its not a wall)
     * calculate the new cost of moving here with the costSoFar and the cost of the neighbor
     * checks if parent contains the neighbor, if it does not, check of the neighbor is not in costsofar or that
     * the new costs is less than the costSoFar of the node
     * if any of these matches push the node to costSoFar with the updated cost
     * calculate our priority (F(n) = g(n) + h(n,goal))
     * push it to open and put the parent of this node as the current node.
     *
     * @param start The start node we want to path from
     * @param goal  The goal node we want to path to
     * @return a hashmap containing nodes, each node points to their parent in relation to the search.
     */
    public static HashMap<Node, Node> astar(Node start, Node goal) {
        HashMap<Node, Node> parent = new HashMap<>();
        HashMap<Node, Double> costSoFar = new HashMap<>();
        PriorityQueue open = new PriorityQueue();
        open.add(new Tuple<>(start, 0.0));
        Node current;
        parent.put(start, start);
        costSoFar.put(start, 0.0);
        while (!open.isEmpty()) {
            current = open.pop().first;

            if (current == goal) {
                break;
            }

            for (Node next : current.neighbors) {

                if (next != null && next.passable) {
                    double g = costSoFar.get(current) + next.cost;
                    if (!costSoFar.containsKey(next) || g < costSoFar.get(next)) {
                        costSoFar.put(next, g);
                        double f = g + heuristic(next, goal);
                        open.add(new Tuple<>(next, f));
                        parent.put(next, current);
                    }
                }
            }
        }
        return parent;
    }

    /**
     * @param start The start node we want to path from
     * @param goal  The goal node we want to path to
     * @return a hashmap containing nodes, each node points to their parent in relation to the search.
     */
    public static HashMap<Node, Node> dijkstra(Node start, Node goal) {
        HashMap<Node, Node> parent = new HashMap<>();
        HashMap<Node, Double> costSoFar = new HashMap<>();
        PriorityQueue open = new PriorityQueue();
        open.add(new Tuple<>(start, 0.0));
        Node current;
        open = new PriorityQueue();
        open.add(new Tuple<>(start, 0.0));
        parent.put(start, start);
        costSoFar.put(start, 0.0);

        while (!open.isEmpty()) {
            current = open.pop().first;

            if (current == goal) {
                break;
            }

            for (Node next : current.neighbors) {

                if (next != null && next.passable) {
                    double g = costSoFar.get(current) + next.cost;

                    if (!costSoFar.containsKey(next) || g < costSoFar.get(next)) {
                        costSoFar.put(next, g);
                        open.add(new Tuple<>(next, g));
                        parent.put(next, current);
                    }
                }
            }

        }
        return parent;
    }

    /**
     * @param start The start node we want to path from
     * @param goal  The goal node we want to path to
     * @return a hashmap containing nodes, each node points to their parent in relation to the search.
     */
    public static HashMap<Node, Node> breadth_first(Node start, Node goal) {
        HashMap<Node, Node> parent = new HashMap<>();
        Queue<Node> queue = new ArrayDeque<>();

        Node current;
        queue.add(start);
        parent.put(start, start);

        while (!queue.isEmpty()) {
            current = queue.poll();

            if (current == goal) {
                break;
            }
            for (Node next : current.neighbors) {
                if (next != null && next.passable) {
                    if (!parent.containsKey(next)) {
                        queue.add(next);
                        parent.put(next, current);
                    }
                }
            }
        }
        return parent;
    }


    public static List<Node> reconstruct(Node start, Node goal, HashMap<Node, Node> parent) {
        List<Node> path = new ArrayList<>();
        Node current = goal;
        while (current != start) {
            path.add(current);
            current = parent.get(current);
        }
        Collections.reverse(path);
        return path;
    }
}
