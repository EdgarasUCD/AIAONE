import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    private void principalVariationReordering(Entry<LinkedHashMap<Node, Entry<Integer, List<Node>>>, Integer> returnedObject) {
        Map.Entry<Node, Entry<Integer, List<Node>>> currentNodeEntry = returnedObject.getKey().entrySet().iterator().next();
        Node currentNode = currentNodeEntry.getKey();

        for (Node node : currentNodeEntry.getValue().getValue()) {
            currentNode.reorder(node);
            currentNode = node;
        }
    }

    public AssignmentOne() {
        Tree tree = new Tree(5, 21, 150);

//        Entry<LinkedHashMap<Node, Entry<Integer, List<Node>>>, Integer> returnObject = negaMax(tree.getRootNode(), tree.getDepth(), new Entry(null, ALPHA), BETA, true, new Entry(new LinkedHashMap<Node, Entry<Integer, List<Node>>>(), 0));
//
//        long t0 = System.currentTimeMillis();
//        System.out.println(principalVariationSearch(tree.getRootNode(), ALPHA, BETA, tree.getDepth()));
//        long t1 = System.currentTimeMillis();
//        System.out.println("TIME TAKEN : " + (t1 - t0));
//
//        principalVariationReordering(returnObject);
//
//        t0 = System.currentTimeMillis();
//        System.out.println(principalVariationSearch(tree.getRootNode(), ALPHA, BETA, tree.getDepth()));
//        t1 = System.currentTimeMillis();
//        System.out.println("TIME TAKEN : " + (t1 - t0));


        long t0 = System.currentTimeMillis();

        System.out.println("=========================");
        System.out.println("Running NegaMax");
        System.out.println("=========================");

        Entry<LinkedHashMap<Node, Entry<Integer, List<Node>>>, Integer> returnObject = negaMax(tree.getRootNode(), tree.getDepth(), new Entry(null, ALPHA), BETA, true, new Entry(new LinkedHashMap<Node, Entry<Integer, List<Node>>>(), 0));

        long t1 = System.currentTimeMillis();

        System.out.println("TIME TAKEN : " + (t1 - t0));

        Entry<Integer, List<Node>> rootNodeEntry = returnObject.getKey().get(tree.getRootNode());
        List<Node> current = rootNodeEntry.getValue();

        System.out.println("=========================");
        System.out.println("Number of evals: " + returnObject.getValue());
        System.out.println("=========================");

        System.out.println("[0, " + rootNodeEntry.getKey() + "]");

        for (Node node : current) {
            System.out.println(node);
        }

//        System.out.println("=========================");
//        System.out.println("Tree Structure");
//        System.out.println("=========================");
//        tree.reset();

        System.out.println("=========================");

        System.out.println("REORDER");

        principalVariationReordering(returnObject);

        System.out.println("=========================");
//        tree.reset();

        t0 = System.currentTimeMillis();

        System.out.println("=========================");
        System.out.println("Running NegaMax");
        System.out.println("=========================");

        returnObject = negaMax(tree.getRootNode(), tree.getDepth(), new Entry(null, ALPHA), BETA, true, new Entry(new LinkedHashMap<Node, Entry<Integer, List<Node>>>(), 0));

        t1 = System.currentTimeMillis();

        System.out.println("TIME TAKEN : " + (t1 - t0));

        rootNodeEntry = returnObject.getKey().get(tree.getRootNode());
        current = rootNodeEntry.getValue();

        System.out.println("=========================");
        System.out.println("Number of evals: " + returnObject.getValue());
        System.out.println("=========================");

        System.out.println("[0, " + rootNodeEntry.getKey() + "]");

        for (Node node : current) {
            System.out.println(node);
        }

    }

    private Entry negaMax(Node node, int height, Entry<Node, Integer> achievable, int hope, boolean modifiable, Entry<LinkedHashMap<Node, Entry<Integer, List<Node>>>, Integer> returnObject) {
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
            for (Node m : node.getChildren(modifiable)) {
                negaMax(m, height - 1, new Entry(achievable.getKey(), -hope), -achievable.getValue(), modifiable, returnObject);

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

    private int principalVariationSearch(Node node, int achievable, int hope, int depth) {
        if (depth == 0 || node.isLeaf()) {
            return node.getE();
        } else {
            Node[] children = node.getChildren(false);
            int score = -principalVariationSearch(children[0], -hope, -achievable, depth - 1);
            if (score < hope) {
                for (int i = 1; i < children.length; i++) {
                    Node child = children[i];
                    int lowerBound = Math.max(achievable, score);
                    int upperBound = lowerBound + 1;
                    int temp = -principalVariationSearch(child, -upperBound, -lowerBound, depth - 1);
                    if (temp >= upperBound && temp < hope) {
                        temp = -principalVariationSearch(child, -hope, -temp, depth - 1);
                    }
                    score = Math.max(score, temp);
                    if (temp >= hope) {
                        break;
                    }
                }
            }
            return score;
        }
    }

}