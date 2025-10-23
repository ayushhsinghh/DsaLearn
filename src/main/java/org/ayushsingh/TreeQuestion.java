package org.ayushsingh;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class TreeQuestion {

    public static List<Integer> value = new ArrayList<>();

    private static void preOrder(Tree root) {
        if(root == null) return;
        System.out.print(root.val + " ");
        preOrder(root.left);
        preOrder(root.right);
    }

    private static void inOrder(Tree root) {
        if(root == null) return;
        inOrder(root.left);
        System.out.print(root.val + " ");
        inOrder(root.right);
    }

    private static void postOrder(Tree root) {
        if(root == null) return;
        postOrder(root.left);
        postOrder(root.right);
        System.out.print(root.val + " ");
    }

    private static void postOrderI(Tree root) {
        if(root == null) return;
        Stack<Tree> stack1 = new Stack<>();
        Stack<Tree> stack2 = new Stack<>();
        stack1.add(root);
        Tree top;
        while(!stack1.isEmpty()) {
            top = stack1.pop();
            stack2.add(top);
            if(top.left != null) stack1.push(top.left);
            if(top.right != null) stack1.push(top.right);
        }

        while(!stack2.isEmpty()) {
            System.out.print(stack2.pop().val + " ");
        }
    }

    private static void levelOrder(Tree root) {
        if(root == null) return;
        Queue<Tree> q = new LinkedList<>();
        q.add(root);
        int size;
        int level = 1;
        while(!q.isEmpty()) {
            size = q.size();
            System.out.print("Values at Level " + level + " are : ");
            while (size > 0) {
                Tree top = q.remove();
                System.out.print(top.val + " ");
                if(top.left != null){
                    q.add(top.left);
                }
                if(top.right != null) {
                    q.add(top.right);
                }
            size--;
            }
            level++;
            System.out.println();
        }
    }

    private static void preOrderI(Tree tree) {
        if(tree == null) return;
        Stack<Tree> stack = new Stack<>();
        stack.push(tree);
        Tree top;
        while(!stack.isEmpty()) {
            top = stack.pop();
            System.out.print(top.val + " ");
            if(top.right != null) stack.add(top.right);
            if(top.left != null) stack.add(top.left);
        }
    }

    private static int heightTree(Tree root) {
        if (root == null)  {
            return 0;
        }

        int lh = heightTree(root.left);
        int lf = heightTree(root.right);

        return 1 + Math.max(lf ,lh);
    }

    private static void fallingLeaves(Tree root) {
        if (root == null) return;
        if(root.left == null && root.right == null) {
            value.add(root.val);
        }
        fallingLeaves(root.left);
        fallingLeaves(root.right);
    }

    public static void main(String[] args) {
        Tree tree = Tree.createDummyTree();
        Tree.printTree(tree);
//        System.out.print("preOrder: ");
//        preOrder(tree);
//        System.out.println();
//        System.out.print("inOrder: ");
//        inOrder(tree);
//        System.out.println();
//        System.out.print("postOrder: ");
//        postOrder(tree);
//        System.out.println();
//        levelOrder(tree);
//        System.out.print("Hieght of Tree is: " + heightTree(tree));
//        fallingLeaves(tree);
//        System.out.println(value);

        System.out.print("PreOrder is: ");
        postOrder(tree);
        System.out.println();
        System.out.print("PreOrder is: ");
        postOrderI(tree);
    }
}
