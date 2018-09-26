import java.util.ArrayList;

/**
 * a simplistic representation of a graph.
 * contains the start and goal of the graph
 * keeps track of row and col length, in cases when we want to visually represent the graph
 * nodes, the nodes in the graph
 */
public class Graph{
    Node start;
    Node goal;
    int row;
    int col;

    ArrayList<Node> nodes;
}
