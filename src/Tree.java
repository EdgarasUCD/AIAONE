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
    private int approximation;

    public Tree(int depth, int branchingFactor, int approximation) {
        this.depth = depth;
        this.branchingFactor = branchingFactor;
        this.approximation = approximation;

        Random random = new Random();
//        random.setSeed(2017);

        int T = T_INVALID;

        while (T == T_INVALID) {
            T = random.nextInt((T_MAX - T_MIN) + 1) + T_MIN;
        }

        rootNode = new Node(null, T, branchingFactor);

        Map<Node, Integer> toTraverse = new LinkedHashMap<Node, Integer>();

        int currentDepth = 0;
        int count = 1;

        Map.Entry<Node, Integer> currentEntry = new CustomEntry<Node, Integer>(rootNode, currentDepth);

        long t0 = System.currentTimeMillis();

        do {
            Node currentNode = currentEntry.getKey();
            currentDepth = currentEntry.getValue() + 1;
            int E = currentNode.getE();
            if (currentDepth <= depth && E <= 10000) {

                int childrenCount = currentNode.getChildren().length;

                // Currently only one is negated
                int negateIndex = random.nextInt(childrenCount);

                for (int i = 0; i < childrenCount; i++) {
                    count++;

                    // branching factor b with 90% chance, b+1 with 5% chance, b-1 with 5% chance

                    int chance = random.nextInt(CHANCE) + 1;
                    int generatedBranchingFactor = branchingFactor;

                    if (chance <= 5) {
                        generatedBranchingFactor++;
                    } else if (chance > 5 && chance <= 10) {
                        generatedBranchingFactor--;
                    }

                    // generate ∂, where ∂ is a small number between -Approx and +Approx chosen randomly for each one individually.

                    // Clamp value to be greater than or equal to parent E and no more than 10000.
                    Node child = new Node(currentNode, (negateIndex == i) ? -E : Math.min(Math.max(E + generateD(random), E), 10000), generatedBranchingFactor);
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

    private int generateD(Random random) {
        return random.nextInt(approximation * 2 + 1) -approximation;
    }

}