import java.util.*;

/**
 * COMP30260 Artificial Intelligence for Games and Puzzles
 * Assignment 1
 * Author: Edgaras Lagunovas
 * Student ID: 15204377
 * Created: 10/8/2017
 */

public class Tree {

    public final static int T_MAX = 2500;
    public final static int T_MIN = -T_MAX;
    public final static int T_INVALID = 0;

    public final static int CHANCE = 100;

    private Node rootNode;
    private int depth;
    private int branchingFactor;

    public Tree(int depth, int branchingFactor) {
        this.depth = depth;
        this.branchingFactor = branchingFactor;

        Random random = new Random();
//        random.setSeed(2017);

        int T = T_INVALID;

        while (T == T_INVALID) {
            T = random.nextInt(T_MAX - T_MIN + 1) + T_MIN;
        }

        rootNode = new Node(null, branchingFactor);

        Map<Node, Integer> toTraverse = new LinkedHashMap<Node, Integer>();

        int currentDepth = 0;
        int count = 1;

        Map.Entry<Node, Integer> currentEntry = new CustomEntry<Node, Integer>(rootNode, currentDepth);

        long t0 = System.currentTimeMillis();

        do {
            Node currentNode = currentEntry.getKey();
            currentDepth = currentEntry.getValue() + 1;
            if (currentDepth <= depth) {
                for (int i = 0; i < currentNode.getChildren().length; i++) {
                    count++;

                    int chance = random.nextInt(CHANCE) + 1;
                    int generatedBranchingFactor = branchingFactor;

                    if (chance <= 5) {
                        generatedBranchingFactor++;
                    } else if (chance > 5 && chance <= 10) {
                        generatedBranchingFactor--;
                    }

                    Node child = new Node(currentNode, generatedBranchingFactor);
                    currentNode.addChild(child);
                    toTraverse.put(child, currentDepth);
                }
            }

            toTraverse.remove(currentNode);

            Iterator<Map.Entry<Node, Integer>> it = toTraverse.entrySet().iterator();

            if (it.hasNext()) {
                currentEntry = it.next();
            }

        } while(!toTraverse.isEmpty());

        long t1 = System.currentTimeMillis();

        System.out.println("Milisec taken: " + (t1 - t0));


        System.out.println("nodes: " + count);
    }

    public Node getRootNode() {
        return rootNode;
    }

    public int getDepth() {
        return depth;
    }

    public int getBranchingFactor() {
        return branchingFactor;
    }

}