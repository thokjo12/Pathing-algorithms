
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Graph graph = createGraphFromFile("src/main/boards/board-1-1.txt");

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
        try {

            int cols = 0;
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String buff;

            while ((buff = reader.readLine()) != null) {
                cols = buff.length();
                builder.append(buff);
            }

            String[] items = builder.toString().split("");

            HashMap<Integer, Node> nodeMap = new HashMap<>();

            for (int i = 0; i < items.length; i++) {
                nodeMap.put(i, new Node(0, items[i]));
            }

            for (int i = 1; i - 1 < nodeMap.size(); i++) {
                int index = i - 1;
                Node curNode = nodeMap.get(index);

                if (curNode.type.equals("A")) graph.start = curNode;
                else if (curNode.type.equals("B")) graph.Goal = curNode;

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

        } catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }
}
