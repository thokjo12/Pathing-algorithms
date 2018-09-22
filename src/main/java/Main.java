
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    static ArrayList<String> boards = new ArrayList<String>() {{

        add("src/main/boards/board-1-1.txt");
        add("src/main/boards/board-1-2.txt");
        add("src/main/boards/board-1-3.txt");
        add("src/main/boards/board-1-4.txt");
        add("src/main/boards/board-2-1.txt");
        add("src/main/boards/board-2-2.txt");
        add("src/main/boards/board-2-3.txt");
        add("src/main/boards/board-2-4.txt");
    }};

    public static void main(String[] args) {
        List<Graph> graphs = boards.stream()
                .map(Main::createGraphFromFile)
                .collect(Collectors.toList());

        Graph graph = graphs.get(7);

        HashMap<Node,Node> result_astar = pathingAlgos.astar(graph.start,graph.goal);
        HashMap<Node,Node> result_dijkstra = pathingAlgos.dijkstra(graph.start,graph.goal);
        HashMap<Node,Node> result_breadth = pathingAlgos.breadth_first(graph.start,graph.goal);

        List<Node> path_astar = pathingAlgos.reconstruct(graph.start,graph.goal,result_astar);
        List<Node> path_dijkstra = pathingAlgos.reconstruct(graph.start,graph.goal,result_dijkstra);
        List<Node> path_breadth = pathingAlgos.reconstruct(graph.start,graph.goal,result_breadth);

        int total_Cost_astar=0;

        for (Node node:path_astar) {
            total_Cost_astar += node.cost;
        }

        System.out.println("total cost of path_astar " + total_Cost_astar);
        System.out.println("lenght of path path_astar " + path_astar.size());
        reconstructGraphWithPath(path_astar,graph);

        int total_Cost_dijkstra=0;

        for (Node node:path_dijkstra) {
            total_Cost_dijkstra += node.cost;
        }

        System.out.println("total cost of path_dijkstra " + total_Cost_dijkstra);
        System.out.println("lenght of path path_dijkstra " + path_dijkstra.size());
        reconstructGraphWithPath(path_dijkstra,graph);
        int total_Cost_breadth=0;

        for (Node node:path_breadth) {
            total_Cost_breadth += node.cost;
        }

        System.out.println("total cost of path_breadth " + total_Cost_breadth);
        System.out.println("lenght of path path_breadth " + path_breadth.size());
        reconstructGraphWithPath(path_breadth,graph);
    }

    private static void reconstructGraphWithPath(List<Node> path,Graph graph) {
        int total = 0;
        for (int i = 0; i < graph.row; i++){
            for (int y = 0; y < graph.col; y++){
                Node node = graph.nodes.get(total);
                if(path.contains(node) && !node.type.equals("B")){
                    System.out.print("O");
                }else{
                    System.out.print(graph.nodes.get(total).type);
                }
                total++;
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * reads the file, creates a hashmap mapping index to nodes
     * maps the nodes neighbors left, top, right, bottom
     * gets the goal and start and puts that in the graph
     *
     * @param path filepath
     * @return a graph of the data in the file
     */
    public static Graph createGraphFromFile(String path) {
        Graph graph = new Graph();


        int cols = 0;
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(path));
            String buff;

            while ((buff = reader.readLine()) != null) {
                cols = buff.length();
                builder.append(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] items = builder.toString().split("");
        graph.col = cols;
        graph.row = items.length/cols;

        HashMap<Integer, Node> nodeMap = new HashMap<>();
        int total = 0;
        for (int i = 0; i < items.length / cols; i++) {
            for (int x = 0; x < cols; x++) {
                nodeMap.put(total, new Node(getCost(items[total]), items[total], x, i));
                total++;
            }
        }

        for (int i = 1; i - 1 < nodeMap.size(); i++) {
            int index = i - 1;
            Node curNode = nodeMap.get(index);
            if (curNode.type.equals("A")) {
                graph.start = curNode;
            } else if (curNode.type.equals("B")) {
                graph.goal = curNode;
            }
            if (curNode.type.equals("#")) {
                curNode.passable = false;
            }

            if (nodeMap.containsKey(index + 1) && i % cols != 0) {
                curNode.addneightbor(2, nodeMap.get(index + 1));
            }
            if (nodeMap.containsKey(index - 1) && index % cols != 0) {
                curNode.addneightbor(0, nodeMap.get(index - 1));
            }
            if (nodeMap.containsKey(index - (cols - 1))) {
                curNode.addneightbor(1, nodeMap.get(index - (cols)));
            }
            if (nodeMap.containsKey(index + (cols - 1))) {
                curNode.addneightbor(3, nodeMap.get(index + (cols)));
            }
        }

        graph.nodes = new ArrayList<>(nodeMap.values());

        return graph;
    }

    private static int getCost(String item) {
        switch (item) {
            case "w":
                return 100;
            case "m":
                return 50;
            case "f":
                return 10;
            case "g":
                return 5;
            case "r":
                return 1;
            default:
                return 0;
        }
    }
}
