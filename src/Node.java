/**
 * COMP30260 Artificial Intelligence for Games and Puzzles
 * Assignment 1
 * Author: Edgaras Lagunovas
 * Student ID: 15204377
 * Created: 10/8/2017
 */

public class Node {

    private Node[] children;
    private Node[] originalChildrenOrder;
    private int E;
    public int order;

    public Node(int E, int childrenCount) {
        this.E = E;
        this.children = new Node[childrenCount];
        originalChildrenOrder = new Node[childrenCount];
    }

    public Node(Node node) {
        this(node.getE(), node.getChildren().length);
    }

    public int getE() {
        return E;
    }

    public int setE(int E) {
        int temp = this.E;
        this.E = E;
        return temp;
    }

    public boolean isLeaf() {
        return children[children.length - 1] == null;
    }

    public boolean isInternal() {
        return children.length != 0;
    }

    public Node[] getChildren() {
        return children;
    }

    public boolean addChild(Node child) {
        if (children[children.length - 1] == null) {
            for (int i = 0; i < children.length; i++) {
                if (children[i] != null) continue;
                children[i] = child;
                originalChildrenOrder[i] = child;
//                originalChildrenOrder[i] = new Node(child);
                // TODO DEEP COPY
                return true;
            }
        }
        return false;
    }

    public void swap(int indexOne, int indexTwo) {
        Node temp = children[indexOne];
        children[indexOne] = children[indexTwo];
        children[indexTwo] = temp;
    }

    public void reset() {
        children = originalChildrenOrder.clone();
    }

}