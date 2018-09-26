import java.util.*;

/**
 * implementation based on Amit Patel's work over at red blob games
 * https://www.redblobgames.com/pathfinding/a-star/introduction.html
 * https://www.redblobgames.com/pathfinding/a-star/implementation.html
 * <p>
 * one thing i would like to implement here is a tiebreaker to resolve the issues with the priority queue filling up
 * with nodes that has the same priority.
 * Tie breaker would be some number that is dependent on the graph which be multiplied by the heuristic, this could
 * effectively reduce the nodes searched(but also increase if the tiebreaker is too small or too large).
 * The current issue is that when the priority queue is filled up with entries of
 * equal priority, aStar can become dijkstra until the nodes with equal priority are reduced to 1.
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
     * check of the neighbor is not in costsofar or that
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
     * effectively the same as the asstar implementation in this file,
     * only difference is that the heuristic is not used to calculate a priority, as the queue here uses the costSoFar and
     * the cost of the node for that.
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
     * Uses a simple FIFO stack as a queue
     * has a hashmap named parent (node, node) to reference parent of some node.
     * pushes start to the stack,
     * while the stack is not empty,
     * check if node is goal, if so stop the search.
     * poll nodes from the stack, check the neighbors, if parents does not contain the neighbor, push it to the queue and
     * parent.
     *
     * uses no heuristic and no cost to guide the search, searches everything untill goal is found.
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

    /**
     *Reconstructs the path based on the parents from goal to start,
     * starts with the goal node as current, checks the parents hashmap for current's parent, appends this to a list
     * keeps going til current is the start node.
     * reverses the path to start at the start node instead of goal.
     * @param start The start node
     * @param goal the target node
     * @param parent hashmap containing node to node reference.
     * @return a list, as the path traversed by the algorithm that constructed the parent hashmap .
     */
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
