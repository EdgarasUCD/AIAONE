/**
 * COMP30260 Artificial Intelligence for Games and Puzzles
 * Assignment 1
 * Author: Edgaras Lagunovas
 * Student ID: 15204377
 * Created: 10/8/2017
 */

public class AssignmentOne {

    public static void main(String[] args) {
        Tree tree = new Tree(2, 3);

        System.out.println(tree.getRootNode());
        System.out.println(tree.getRootNode().getChildren());
        System.out.println(tree.getRootNode().getParent());
    }

}