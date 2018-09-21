
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
        Graph graph = graphs.get(0);
        Astar astar = new Astar(graph.start,graph.goal);
        HashMap<Node,Node> path = astar.run();
        path.size();
        int total = 0;
        for (int i = 0; i < graph.row; i++){
            for (int y = 0; y < graph.col; y++){
                System.out.print(graph.nodes.get(total).type);
                total++;
            }
            System.out.println();
        }
//        PriorityQueue priorityQueue = new PriorityQueue();
//        priorityQueue.add(new Tuple<>(new Node(0,"",0,0),6.0));
//        priorityQueue.add(new Tuple<>(new Node(0,"",0,0),66.0));
//        priorityQueue.add(new Tuple<>(new Node(0,"",0,0),345.0));
//        priorityQueue.add(new Tuple<>(new Node(0,"",0,0),4.0));
//        priorityQueue.add(new Tuple<>(new Node(0,"",0,0),1.0));
//        priorityQueue.add(new Tuple<>(new Node(0,"",0,0),0.0));
//        priorityQueue.add(new Tuple<>(new Node(0,"",0,0),7.0));
//
//        priorityQueue.pop();
//        priorityQueue.pop();
//        priorityQueue.pop();
//        priorityQueue.pop();
//        priorityQueue.pop();
//        priorityQueue.pop();
//        priorityQueue.pop();
//        System.out.println(priorityQueue.isEmpty());

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
