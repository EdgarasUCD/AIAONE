/**
 * COMP30260 Artificial Intelligence for Games and Puzzles
 * Assignment 1
 * Author: Edgaras Lagunovas
 * Student ID: 15204377
 * Created: 10/8/2017
 */

public class Node {

    private Node parent;
    private Node[] children;
    private int staticEvaluationValue;

    public Node(Node parent, int childrenCount) {
        this.parent = parent;
        this.children = new Node[childrenCount];
    }

    public boolean isLeaf() {
        return children.length == 0;
    }

    public boolean isInternal() {
        return children.length != 0;
    }

    public boolean isRoot() {
        return parent == null;
    }

    public Node getParent() {
        return parent;
    }

    public Node[] getChildren() {
        return children;
    }

    public boolean addChild(Node child) {
        for (int i = 0; i < children.length; i++) {
            if (children[i] == null) continue;
            children[i] = child;
            return true;
        }
        return false;
    }

}