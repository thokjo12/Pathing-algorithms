import java.util.*;

public class pathingAlgos {
    /**
     * manhattan
     *
     * @param a node a
     * @param b node b
     * @return the manhattan distance to b from a
     */
    public static double heuristic(Node a, Node b) {
        return (Math.abs(a.x - b.x) + Math.abs(a.y - b.y));
    }

    /**
     *
     */
    public static HashMap<Node, Node> astar(Node start,Node goal) {
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
                        double f = g +  heuristic(next, goal);

                        open.add(new Tuple<>(next, f));

                        parent.put(next, current);
                    }
                }
            }

        }
        return parent;
    }

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


    public static HashMap<Node, Node> breadth_first(Node start, Node goal) {
        HashMap<Node, Node> parent = new HashMap<>();
        Queue<Node> queue = new ArrayDeque<>();

        Node current;
        queue.add(start);
        parent.put(start,start);

        while (!queue.isEmpty()){
            current = queue.poll();

            if(current == goal){
                break;
            }
            for (Node next: current.neighbors) {
                if(next != null && next.passable){
                    if(!parent.containsKey(next)){
                        queue.add(next);
                        parent.put(next,current);
                    }
                }
            }
        }
        return parent;
    }


    public static List<Node> reconstruct(Node start,Node goal,HashMap<Node,Node> parent) {
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
