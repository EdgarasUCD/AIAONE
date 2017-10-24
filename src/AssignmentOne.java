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

    public AssignmentOne() {
        Tree tree = new Tree(2, 3, 150);

        System.out.println("=========================");
        System.out.println("Running NiggaMax");
        System.out.println("=========================");
        negaMax(tree.getRootNode(), tree.getDepth(), ALPHA, BETA);
        System.out.println("=========================");
        System.out.println("MEDELALIS");
        System.out.println("=========================");
        tree.reset();
    }

    private int negaMax(Node node, int height, int achievable, int hope) {
        System.out.println("Looking at node: " + node.order);
        if (height == 0 || node.isLeaf()) {
            System.out.println("height == 0 || node.isLeaf() : " + node.getE());
            return node.getE();
        } else {
            int temp;
            for (Node m : node.getChildren()) {
                temp = -negaMax(m, height - 1, -hope, -achievable);
                if (temp >= hope) {
                    System.out.println("temp >= hope : " + temp);
                    return temp;
                }
                achievable = Math.max(temp, achievable);
            }
        }
            System.out.println("return achievable; : " + achievable);
            return achievable;
    }

}