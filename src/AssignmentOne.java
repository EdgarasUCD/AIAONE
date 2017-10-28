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
        Tree tree = new Tree(5, 21, 150);

        System.out.println("=========================");
        System.out.println("Running NegaMax");
        System.out.println("=========================");

        Entry<LinkedHashMap<Node, Entry<Integer, List<Node>>>, Integer> returnObject = negaMax(tree.getRootNode(), tree.getDepth(), new Entry(null, ALPHA), BETA, new Entry(new LinkedHashMap<Node, Entry<Integer, List<Node>>>(), 0));

        Entry<Integer, List<Node>> rootNodeEntry = returnObject.getKey().get(tree.getRootNode());
        List<Node> current = rootNodeEntry.getValue();

        System.out.println("=========================");
        System.out.println("Number of evals: " + returnObject.getValue());
        System.out.println("Kelias iki best leaf nuo root node");
        System.out.println("=========================");

        System.out.println("[0, " + rootNodeEntry.getKey() + "]");

        for (Node node : current) {
            System.out.println(node);
        }

        System.out.println("=========================");
        System.out.println("Tree Structure");
        System.out.println("=========================");
//        tree.reset();
    }

    private Entry negaMax(Node node, int height, Entry<Node, Integer> achievable, int hope, Entry<LinkedHashMap<Node, Entry<Integer, List<Node>>>, Integer> returnObject) {
        LinkedHashMap<Node, Entry<Integer, List<Node>>> pvMap = returnObject.getKey();

        if (!pvMap.containsKey(node)) {
            pvMap.put(node, new Entry(0, new ArrayList<Node>()));
        }

//        System.out.println("Looking at node: " + node.order);
        if (height == 0 || node.isLeaf()) {
//            System.out.println("=== height == 0 || node.isLeaf() : " + node.getE() + " ===");
            returnObject.setValue(returnObject.getValue() + 1);
            return returnObject;
        } else {
            int temp;
            for (Node m : node.getChildren()) {
                negaMax(m, height - 1, new Entry(achievable.getKey(), -hope), -achievable.getValue(), returnObject);

                temp = -m.getE();
                if (temp >= hope) {
                    // Better or equal than hoped, so return daughter m.
//                    System.out.println("=== temp >= hope : " + temp + " ===");

                    // Insert path
                    Entry<Integer, List<Node>> nodeInfo = pvMap.get(node);
                    nodeInfo.setKey(temp);
                    List<Node> nodePv = nodeInfo.getValue();
                    nodePv.add(m);
                    nodePv.addAll(pvMap.get(m).getValue());
                    return returnObject;
                }

                if (temp > achievable.getValue()) {
                    achievable.setKey(m);
                    achievable.setValue(temp);
                    // pvMap.node already has node in it by returned call because of height or is leaf.
                } else if (achievable.getValue() > temp) {
//                    System.out.println("ACHIEVABLE IS GREATER THAN TEMP");
                }

            }
        }

        Entry<Integer, List<Node>> nodeInfo = pvMap.get(node);
        nodeInfo.setKey(achievable.getValue());
        List<Node> nodePv = nodeInfo.getValue();
        nodePv.add(achievable.getKey());
        nodePv.addAll(pvMap.get(achievable.getKey()).getValue());

//        System.out.println("=== return achievable; : " + achievable.getKey() + " ===");
        return returnObject;
    }

}