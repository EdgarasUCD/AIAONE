import java.util.*;

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
        Tree tree = new Tree(6, 15, 150);

        Entry<LinkedHashMap<Node, Entry<Integer, List<Node>>>, Integer> returnObject = null;

        System.out.println("[DEPTH, EVALS]");
        int totalNegamax = 0;

//        for (int i = 4; i <= tree.getDepth(); i++) {
//            returnObject = negaMax(tree.getRootNode(), i, new Entry(null, ALPHA), BETA, true, new Entry(new LinkedHashMap<Node, Entry<Integer, List<Node>>>(), 0));
//
//            Entry<Integer, List<Node>> rootNodeEntry = returnObject.getKey().get(tree.getRootNode());
//            List<Node> current = rootNodeEntry.getValue();
//
//            int eval = returnObject.getValue();
//
//            System.out.println(" --- [" + i + ", " + returnObject.getValue() + "] ---");
//
//            // CAN REORDER
//
////            for (Node node : current) {
////                System.out.println(node);
////            }
////            System.out.println("============");
//
//            totalNegamax += eval;
//        }

        System.out.println("Total evals: " + totalNegamax);

        System.out.println("==========================================");

        Entry<Integer, Integer> entry = principalVariationSearch(tree.getRootNode(), ALPHA, BETA, tree.getDepth(), new Entry(0, 0));
        System.out.println(entry.getKey());
        System.out.println(entry.getValue());


//        System.out.println("==============");
//
//        tree.print();
//
//        System.out.println("reorder");
//        System.out.println("========");
//
//        principalVariationReordering(negaMax(tree.getRootNode(), tree.getDepth(), new Entry(null, ALPHA), BETA, true, new Entry(new LinkedHashMap<Node, Entry<Integer, List<Node>>>(), 0)));
//
//        System.out.println("=================");
//        tree.print();
//
//        System.out.println("===========original order array=============");
//tree.reset();
//tree.print();

        returnObject = negaMax(tree.getRootNode(), tree.getDepth(), new Entry(null, ALPHA), BETA, true, new Entry(new LinkedHashMap<Node, Entry<Integer, List<Node>>>(), 0));
        System.out.println(returnObject.getValue());

        principalVariationReordering(returnObject);
        System.out.println("reoder");

        returnObject = negaMax(tree.getRootNode(), tree.getDepth(), new Entry(null, ALPHA), BETA, true, new Entry(new LinkedHashMap<Node, Entry<Integer, List<Node>>>(), 0));
        System.out.println(returnObject.getValue());

        tree.reset();

        System.out.println("reset");

        returnObject = negaMax(tree.getRootNode(), tree.getDepth(), new Entry(null, ALPHA), BETA, true, new Entry(new LinkedHashMap<Node, Entry<Integer, List<Node>>>(), 0));
        System.out.println(returnObject.getValue());



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

    private Entry<Integer, Integer> principalVariationSearch(Node node, int achievable, int hope, int depth, Entry<Integer, Integer> returnObject) {
        if (depth == 0 || node.isLeaf()) {
            returnObject.setValue(returnObject.getValue() + 1);
            return new Entry(node.getE(), returnObject.getValue());
        } else {
            Node[] children = node.getChildren(true);
            Entry<Integer, Integer> scoreEntry = principalVariationSearch(children[0], -hope, -achievable, depth - 1, returnObject);
            scoreEntry.setKey(-scoreEntry.getKey());
            if (scoreEntry.getKey() < hope) {
                for (int i = 1; i < children.length; i++) {
                    Node child = children[i];
                    int lowerBound = Math.max(achievable, scoreEntry.getKey());
                    int upperBound = lowerBound + 1;
                    Entry<Integer, Integer> tempEntry = principalVariationSearch(child, -upperBound, -lowerBound, depth - 1, returnObject);
                    tempEntry.setKey(-tempEntry.getKey());
                    if (tempEntry.getKey() >= upperBound && tempEntry.getKey() < hope) {
                        tempEntry = principalVariationSearch(child, -hope, -tempEntry.getKey(), depth - 1, returnObject);
                        tempEntry.setKey(-tempEntry.getKey());
                    }

                    if (tempEntry.getKey() > scoreEntry.getKey()) {
                        scoreEntry = tempEntry;
                    }

                    if (tempEntry.getKey() >= hope) {
                        break;
                    }
                }
            }
            return scoreEntry;
        }
    }

}