package org.ayushsingh.models;

import java.util.LinkedList;
import java.util.Queue;

public class Tree {
    public int val;
    public Tree left;
    public Tree right;

    Tree() {}
    public Tree(int val) { this.val = val; }

    Tree(int val, Tree left, Tree right) {
         this.val = val;
         this.left = left;
         this.right = right;
    }

    // Function to create a dummy tree
    public static Tree createDummyTree() {
        //      1
        //     / \
        //    2   3
        //   / \  / \
        //  4   5 6  7
        // / \    /\
        // 8  9  10 11
        Tree root = new Tree(1);

        root.left = new Tree(2,
                new Tree(4, new Tree(8), new Tree(9)),
                new Tree(5));

        root.right = new Tree(3,
                new Tree(6, new Tree(10), new Tree(11)),
                new Tree(7));

        return root;
    }

    // Function to print the tree (preorder traversal)
    public static void printTree(Tree root) {
//        if (root == null) return;
//        System.out.print(root.val + " ");
//        printTree(root.left);
//        printTree(root.right);
        String output = """
                      1
                     / \\
                    2    3
                   / \\  / \\
                  4   5 6  7
                 / \\   /\\
                 8  9 10 11
                """;
        System.out.println(output);
    }

    public static void printTreeLevel(Tree root) {
        if (root == null) return;

        Queue<Tree> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Tree node = queue.poll();
                if (node != null) {
                    System.out.print(node.val + " ");
                    queue.add(node.left);
                    queue.add(node.right);
                } else {
                    System.out.print("null ");
                }
            }
            System.out.println(); // new line for each level
        }
    }
}
