import java.util.ArrayList;

public class Node{
    public int cost;
    public int x,y;
    public String type;
    public boolean passable = true;

    /**
     * 0 = left
     * 1 = top
     * 2 = right
     * 3 = bot
     */
    public ArrayList<Node> neighbors = new ArrayList<Node>(){{add(null);add(null);add(null);add(null);}};

    public Node(int cost, String type,int x, int y) {
        this.cost = cost;
        this.type = type;
        this.x = x;
        this.y = y;
    }





    public void addneightbor(int i, Node neighbor){
        neighbors.remove(i);
        neighbors.add(i,neighbor);
    }
}

