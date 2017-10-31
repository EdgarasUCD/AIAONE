import java.util.*;

/**
 * COMP30260 Artificial Intelligence for Games and Puzzles
 * Assignment 1
 * Author: Edgaras Lagunovas
 * Student ID: 15204377
 * Created: 10/8/2017
 */

public class Tree {

    private final static int T_MAX = 2500;
    private final static int T_MIN = -T_MAX;
    private final static int T_INVALID = 0;
    private final static int CHANCE = 100;
    private final static int WIN_STATE = 10000;

    private Node rootNode;
    private int depth;
    private int branchingFactor;
    private int approximation;
    private Random random;

    private int generateTrueValue(int max, int min) {
        int T = T_INVALID;

        // Make sure T is not 0
        while (T == T_INVALID) {
            T = random.nextInt((max - min) + 1) + min;
        }

        return T;
    }

    public Tree(int depth, int branchingFactor, int approximation) {
        this.depth = depth;
        this.branchingFactor = branchingFactor;
        this.approximation = approximation;

        random = new Random();
//      random.setSeed(2017);

        int T = generateTrueValue(T_MAX, T_MIN);

        System.out.println("Generated T: " + T);

        rootNode = new Node(T, branchingFactor);

        Map<Node, Integer> toTraverse = new LinkedHashMap<Node, Integer>();

        int currentDepth = 0;
        int nodeCount = 1;
        int winStateNodeCount = 0;

        Map.Entry<Node, Integer> currentEntry = new AbstractMap.SimpleEntry<Node, Integer>(rootNode, currentDepth);

        do {
            Node currentNode = currentEntry.getKey();

            // E is T at this point
            T = currentNode.getE();

            currentNode.setE(currentNode.getE() + generateD());

            currentDepth = currentEntry.getValue() + 1;

            if (currentDepth <= depth) {
                int childrenCount = currentNode.getChildren(false).length;

                // Negate at least one random node.Klaipėda, Lithuania
                int negateIndex = random.nextInt(childrenCount);
                T = -T;

                for (int i = 0; i < childrenCount; i++) {
                    nodeCount++;

                    // branching factor b with 90% chance, b+1 with 5% chance, b-1 with 5% chance

                    int chance = random.nextInt(CHANCE) + 1;
                    int generatedBranchingFactor = branchingFactor;

                    // FIXME Disabled for testing. TO BE UNCOMMENTED FOR SUBMISSION.
//                    if (currentNode != rootNode) {
//                        if (chance <= 5) {
//                            generatedBranchingFactor++;
//                        } else if (chance > 5 && chance <= 10) {
//                            generatedBranchingFactor--;
//                        }
//                    }

                    int E = (negateIndex == i) ? T : generateTrueValue(WIN_STATE, T);

                    Node child = new Node(E, generatedBranchingFactor);
                    child.order = nodeCount - 1;

                    currentNode.addChild(child);

                    if (E < WIN_STATE) {
                        toTraverse.put(child, currentDepth);
                    } else {
                        winStateNodeCount++;
                    }

                }
            }

            toTraverse.remove(currentNode);

            Iterator<Map.Entry<Node, Integer>> it = toTraverse.entrySet().iterator();

            if (it.hasNext()) {
                currentEntry = it.next();
            }

        } while(!toTraverse.isEmpty());

        System.out.println("Node count: " + nodeCount);
        System.out.println("Win state node count: " + winStateNodeCount);
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

    private int generateD() {
    return 0;
    //     return random.nextInt(approximation * 2 + 1) -approximation;
    }


    public void reset() {
        Stack<Node> stack = new Stack<Node>();
        stack.push(rootNode);

        do {
            Node currentNode = stack.pop();
            currentNode.reset();
            Node[] currentNodeChildren = currentNode.getChildren(false);
            for (int i = currentNodeChildren.length - 1; i >= 0 && currentNodeChildren[0] != null; i--) {
                stack.push(currentNodeChildren[i]);
            }
        } while (!stack.isEmpty());
    }

}