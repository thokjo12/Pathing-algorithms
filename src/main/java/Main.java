
import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

enum brds {
    board_1_1,
    board_1_2,
    board_1_3,
    board_1_4,
    board_2_1,
    board_2_2,
    board_2_3,
    board_2_4,
}


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

    public static void main(String[] args) throws IOException {
        List<Graph> graphs = boards.stream()
                .map(Main::createGraphFromFile)
                .collect(Collectors.toList());

        Graph graph = graphs.get(brds.board_2_4.ordinal());

        //run the different algorithms
        HashMap<Node, Node> result_astar = pathingAlgos.astar(graph.start, graph.goal);
        HashMap<Node, Node> result_dijkstra = pathingAlgos.dijkstra(graph.start, graph.goal);
        HashMap<Node, Node> result_breadth = pathingAlgos.breadth_first(graph.start, graph.goal);

        //reconstruct the paths for the different algorithms
        List<Node> path_astar = pathingAlgos.reconstruct(graph.start, graph.goal, result_astar);
        List<Node> path_dijkstra = pathingAlgos.reconstruct(graph.start, graph.goal, result_dijkstra);
        List<Node> path_breadth = pathingAlgos.reconstruct(graph.start, graph.goal, result_breadth);

        int total_Cost_astar = 0;

        //calculate cost for a star
        for (Node node : path_astar) {
            total_Cost_astar += node.cost;
        }

        //print graph and additional information
        System.out.println("total cost of path_astar " + total_Cost_astar);
        System.out.println("lenght of path path_astar " + path_astar.size());
        reconstructGraphWithPath(path_astar, graph);


        int total_Cost_dijkstra = 0;
        //calculate cost for dijkstra
        for (Node node : path_dijkstra) {
            total_Cost_dijkstra += node.cost;
        }

        //print graph and additional information
        System.out.println("total cost of path_dijkstra " + total_Cost_dijkstra);
        System.out.println("lenght of path path_dijkstra " + path_dijkstra.size());
        reconstructGraphWithPath(path_dijkstra, graph);

        int total_Cost_breadth = 0;
        //calculate cost for breadth
        for (Node node : path_breadth) {
            total_Cost_breadth += node.cost;
        }

        //print graph and additional information
        System.out.println("total cost of path_breadth " + total_Cost_breadth);
        System.out.println("lenght of path path_breadth " + path_breadth.size());
        reconstructGraphWithPath(path_breadth, graph);

        //change path var to
        writeToFile(graph,path_astar,"astar");
        writeToFile(graph,path_dijkstra,"dijkstra");
        writeToFile(graph,path_breadth,"breadth_first_search");
        Process process1 = Runtime.getRuntime().exec("python src/main/python/createplot.py astar " +total_Cost_astar);
        Process process2 = Runtime.getRuntime().exec("python src/main/python/createplot.py dijkstra "+total_Cost_dijkstra);
        Process process3 = Runtime.getRuntime().exec("python src/main/python/createplot.py breadth_first_search "+total_Cost_breadth);

    }

    /**
     * writes to a file that contains the board representation
     * row and col
     * and path representation in xy coordinates.
     * @param graph graph
     * @param path path
     * @param name filename
     */
    private static void writeToFile(Graph graph, List<Node> path,String name){
        File file = new File("src/main/python/"+name);
        try {
            FileWriter writer = new FileWriter(file);
            writer.append(String.format("%s,%s\n",graph.col,graph.row));
            writer.append("gs\n");
            int total = 0;
            StringBuilder rowline = new StringBuilder();
            for (int i = 0; i < graph.row; i++) {
                for (int y = 0; y < graph.col; y++) {
                    rowline.append(graph.nodes.get(total).type);
                    total++;
                }
                rowline.append("\n");
            }
            writer.append(rowline.toString());
            System.out.println();
            writer.append("ge\n");
            writer.append("ps\n");
            String pathstr = "";
            for (int i = 0; i<path.size(); i++){
                Node node = path.get(i);
                pathstr += String.format("%s.%s,",(int)node.x,(int)node.y);
            }
            writer.append(pathstr+"\n");
            writer.append("pe");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * visually represent the  graph with a path.
     *
     * @param path  the path to add to the visualization
     * @param graph the graph to visualize
     */
    private static void reconstructGraphWithPath(List<Node> path, Graph graph) {
        int total = 0;
        for (int i = 0; i < graph.row; i++) {
            for (int y = 0; y < graph.col; y++) {
                Node node = graph.nodes.get(total);
                if (path.contains(node) && !node.type.equals("B")) {
                    System.out.print("O");
                } else {
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
        graph.row = items.length / cols;

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

    /**
     * used to create nodes with cost based on the grid item type.
     *
     * @param item the char at some position in the grid
     * @return the cost of this type.
     */
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
