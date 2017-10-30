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
    public int order; // ONLY USED FOR TESTING PURPOSES, DELETE BEFORE SUBMISSION.

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
//                originalChildrenOrder[i] = child;
                originalChildrenOrder[i] = new Node(child);
                // TODO DEEP COPY
                return true;
            }
        }
        return false;
    }

    public int getChildIndex(Node node) {
        for (int i = 0; i < children.length; i++) {
            if (node == children[i]) {
                return i;
            }
        }
        return -1;
    }

    public void reorder(Node node) {
        int index = getChildIndex(node);
        if (index == -1) return;

        Node[] reorderedArray = new Node[children.length];
//        reorderedArray[0] = children[index];

        boolean found = false;

        for (int i = 0; i < children.length; i++) {
            int store = (index == i) ? 0 : i + 1;

            if (index == i) {
                store = 0;
                found = true;
            } else {
                store = (found) ? i : i + 1;
            }
            reorderedArray[store] = children[i];
        }

//        for (int i = 0; i < children.length; i++) {
//            int store = i + i;
//            if (i == index) {
//                i++;
//                if (index == children.length - 1) {
//                    break;
//                }
//            }
//            reorderedArray[store] = children[i];
//        }

        children = reorderedArray;
    }

    public void reset() {
        for (int i = 0; i < children.length; i++) {
            children[i] = new Node(originalChildrenOrder[i]);
        }
    }

    // ONLY USED FOR TESTING PURPOSES, DELETE BEFORE SUBMISSION.
    @Override
    public String toString() {
        return "[" + order + ", " + E + ", ]";
    }

}