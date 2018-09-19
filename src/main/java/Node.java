import java.util.ArrayList;

public class Node {
    public Node(int cost, String type) {
        this.cost = cost;
        this.type = type;
    }

    public int cost;
    public String type;

    /**
     * 0 = left
     * 1 = top
     * 2 = right
     * 3 = bot
     */
    public ArrayList<Node> neighbors = new ArrayList<Node>(){{add(null);add(null);add(null);add(null);}};

    public void addneightbor(int i, Node neighbor){
        neighbors.remove(i);
        neighbors.add(i,neighbor);
    }
}

