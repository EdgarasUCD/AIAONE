import java.util.Arrays;

/**
 * COMP30260 Artificial Intelligence for Games and Puzzles
 * Assignment 1
 * Author: Edgaras Lagunovas
 * Student ID: 15204377
 * Created: 10/8/2017
 */

public class Node {

    public Node[] children;
    private Node[] originalChildrenOrder;
    private int E;
    public int order; // ONLY USED FOR TESTING PURPOSES, DELETE BEFORE SUBMISSION.

    public Node(int E, int childrenCount) {
        this.E = E;
        this.children = new Node[childrenCount];
        originalChildrenOrder = new Node[childrenCount];
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

    public Node[] getChildren(boolean modifiable) {
        if (modifiable) {
            return children;
        }
        return originalChildrenOrder;
    }

    public boolean addChild(Node child) {
        if (children[children.length - 1] == null) {
            for (int i = 0; i < children.length; i++) {
                if (children[i] != null) continue;
                children[i] = child;
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

        boolean found = false;

        for (int i = 0; i < children.length; i++) {
            int store;

            if (index == i) {
                store = 0;
                found = true;
            } else {
                store = (found) ? i : i + 1;
            }
            reorderedArray[store] = children[i];
        }

        children = reorderedArray;
    }

    public void reset() {
        for (int i = 0; i < originalChildrenOrder.length; i++) {
            Node orderChild = originalChildrenOrder[i];

            SEARCH: {
                for (int j = 0; j < children.length; j++) {
                    Node child = children[j];
                    if (child == orderChild) {
                        if (i != j) {
                            // swap nodes
                            Node temp = children[i];
                            children[i] = child;
                            children[j] = temp;
                        }
                        // Node belongs here so break
                        break SEARCH;
                    }

                }
            }

        }
    }

    public void cloneArray() {
        originalChildrenOrder = Arrays.copyOf(children, children.length);
    }

    // ONLY USED FOR TESTING PURPOSES, DELETE BEFORE SUBMISSION.
    @Override
    public String toString() {
        return "[" + order + ", " + E + "]";
    }

}