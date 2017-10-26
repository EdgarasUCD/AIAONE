import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * COMP30260 Artificial Intelligence for Games and Puzzles
 * Assignment 1
 * Author: Edgaras Lagunovas
 * Student ID: 15204377
 * Created: 10/8/2017
 */

public class AssignmentOne {

    public static final int ALPHA = -10000;
    public static final int BETA = 10000;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        new AssignmentOne();
        System.out.println("Milisec taken: " + (System.currentTimeMillis() - startTime));
    }

    public AssignmentOne() {
        Tree tree = new Tree(2, 3, 150);

        System.out.println("=========================");
        System.out.println("Running NegaMax");
        System.out.println("=========================");
        LinkedHashMap<Node, List<Node>> pvMap = negaMax(tree.getRootNode(), tree.getDepth(), new Entry(null, ALPHA), BETA, new LinkedHashMap<Node, List<Node>>());

        List<Node> current = pvMap.get(tree.getRootNode());

        System.out.println("=========================");
        System.out.println("Kelias iki best leaf nuo root node");
        System.out.println("=========================");

        for (Node node : current) {
            System.out.println(node);
        }

        System.out.println("=========================");
        System.out.println("Tree Structure");
        System.out.println("=========================");
        tree.reset();
    }

    private LinkedHashMap<Node, List<Node>> negaMax(Node node, int height, Entry achievable, int hope, LinkedHashMap<Node, List<Node>> pvMap) {

        if (!pvMap.containsKey(node)) {
            pvMap.put(node, new ArrayList<Node>());
        }

        System.out.println("Looking at node: " + node.order);
        if (height == 0 || node.isLeaf()) {
            System.out.println("=== height == 0 || node.isLeaf() : " + node.getE() + " ===");
            return pvMap;
        } else {
            int temp;
            for (Node m : node.getChildren()) {
                negaMax(m, height - 1, new Entry((Node) achievable.getKey(), -hope), - ((int) achievable.getValue()), pvMap);

                int achievableValue = (int) achievable.getValue();

                temp = -m.getE();
                if (temp >= hope) {
                    // Better or equal than hoped, so return daughter m.
                    System.out.println("=== temp >= hope : " + temp + " ===");

                    // Insert path
                    List<Node> nodePv = pvMap.get(node);
                    nodePv.add(m);
                    nodePv.addAll(pvMap.get(m));
                    return pvMap;
                }

                if (temp > achievableValue) {
                    achievable.setKey(m);
                    achievable.setValue(temp);
                    // pvMap.node already has node in it by returned call because of height or is leaf.
                } else if (achievableValue > temp) {
                    System.out.println("ACHIEVABLE IS GREATER THAN TEMP");
                }

            }
        }
        Node achievableNode = (Node) achievable.getKey();
        List<Node> nodePv = pvMap.get(node);
        nodePv.add(achievableNode);
        nodePv.addAll(pvMap.get(achievableNode));
        System.out.println("=== return achievable; : " + achievableNode + " ===");
        return pvMap;
    }

}