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
    private int E;

    public Node(Node parent, int E, int childrenCount) {
        this.parent = parent;
        this.E = E;
        this.children = new Node[childrenCount];
    }

    public int getE() {
        return E;
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
        if (children[children.length - 1] != null) {
            for (int i = 0; i < children.length; i++) {
                if (children[i] == null) continue;
                children[i] = child;
                return true;
            }
        }
        return false;
    }

}