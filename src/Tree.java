import java.util.ArrayList;
import java.util.List;

/**
 * COMP30260 Artificial Intelligence for Games and Puzzles
 * Assignment 1
 * Author: Edgaras Lagunovas
 * Student ID: 15204377
 * Created: 10/8/2017
 */

public class Tree {

    private Node rootNode;
    private int depth;
    private int branchingFactor;

    public Tree(int depth, int branchingFactor) {
        this.depth = depth;
        this.branchingFactor = branchingFactor;

        rootNode = new Node(null, branchingFactor);
        Node currentNode = rootNode;
        List<Node> toTraverse = new ArrayList<Node>();
        int currentDepth = -1;

        do {
            currentDepth++;
            for (int i = 0; i < branchingFactor; i++) {
                Node child = new Node(currentNode, branchingFactor);
                currentNode.addChild(child);
                toTraverse.add(child);
            }

            currentNode = toTraverse.remove(0);
        } while(!toTraverse.isEmpty() && depth >= currentDepth);
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