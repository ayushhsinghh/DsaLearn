package org.ayushsingh;

import org.ayushsingh.models.Tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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

    Map<String, Integer> map = new HashMap<>();

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

    public List<Integer> inorderTraversal(Tree root) {
        List<Integer> result = new ArrayList<>();
        Stack<Tree> stack = new Stack<>();
        Tree curr = root;

        while (curr != null || !stack.isEmpty()) {

            // 1. Push all left nodes
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }

            // 2. Pop and visit
            curr = stack.pop();
            result.add(curr.val);

            // 3. Move to right subtree
            curr = curr.right;
        }

        return result;
    }

    private static int heightTree(Tree root) {
        if (root == null)  {
            return 0;
        }

        int lh = heightTree(root.left);
        int lf = heightTree(root.right);

        return 1 + Math.max(lf ,lh);
    }

    // Given the root of a binary tree, check whether it is a mirror of itself (i.e., symmetric around its center).
    public boolean isSymmetric(Tree root) {
        return checkSymmetric(root.left, root.right);
    }
    public boolean checkSymmetric(Tree p, Tree q) {
        if(p == null && q == null) return true;
        if(p == null || q == null) return false;
        if(p.val != q.val) return false;
        return checkSymmetric(p.left, q.right) && checkSymmetric(p.right, q.left); // Compare Left with Right of both trees.
    }


    // Given the root of a binary tree, invert the tree, and return its root.
    // Basically a left to right Swap.
    public Tree invertTree(Tree root) {
        invert(root);
        return root;
    }
    public void invert(Tree root) {
        if(root == null) return;
        if(root.left != null || root.right != null) {
            Tree temp = root.left;
            root.left = root.right;
            root.right = temp;
        }
        invert(root.left);
        invert(root.right);
    }

    // Build Tree From PreOrder and InOrder

    // If we pick elements from preorder from start to end,
    // each element is a root — in top-down order.
    // var preIndex = 0;
    // Take preorder[preIndex] as root. (Increment PreIndex)
    // Find that root in the inorder array.
    // Elements before it in inorder = left subtree,
    // elements after it = right subtree.
    // Recurse for left subtree first, then right subtree.
    int rootIndex = 0; // Global Variable
    public Tree buildTree(int[] preorder, int[] inorder) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0 ; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        return buildTree(preorder, map, 0, inorder.length - 1);
    }
    public Tree buildTree(int[] preorder, Map<Integer, Integer> map, int start, int end) {
        if(start > end) return null;

        Tree root = new Tree(preorder[rootIndex++]);
        Integer mid = map.get(root.val);

        root.left = buildTree(preorder, map, start, mid-1);
        root.right = buildTree(preorder, map, mid+1, end);

        return root;
    }

    // Build Tree From PostOrder and Inorder
    // From the inorder array, you can find the root position.
    // Elements left of root belong to the left subtree
    // Elements right of root belong to the right subtree
    //
    //In postorder, the order is left → right → root,
    //  so when we go backwards, we first see:
    //  root → right subtree → left subtree
    //  This means while building recursively (from the end of postorder),
    // we must:
    // build right subtree first, then build left subtree.
    int rootIndex1; // Global Variable
    public Tree buildTree1(int[] inorder, int[] postorder) {
        rootIndex  = postorder.length - 1;
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }

        return helper(postorder, map, 0,postorder.length - 1);
    }
    public Tree helper(int[] postorder, Map<Integer, Integer> map, int start, int end) {
        if(start > end) return null;
        int rootVal = postorder[rootIndex1--];
        Tree root = new Tree(rootVal);
        Integer mid = map.get(root.val);

        root.right = helper(postorder, map, mid+1, end);
        root.left = helper(postorder, map, start, mid-1);

        return root;
    }

    // Sum Root to Leaf Numbers
    // Each root-to-leaf path in the tree represents a number.
    // Return the total sum of all root-to-leaf numbers.
    public int sumNumbers(Tree root, int count) {
        if(root == null) return 0;

        count = count * 10 + root.val;
        if(root.left == null && root.right == null) {
            return count;
        }

        int left = sumNumbers(root.left, count);
        int right = sumNumbers(root.right, count);
        return left + right;
    }
    public int sumNumbers(Tree root) {
        if(root == null) return 0;
        int count = 0;
        return sumNumbers(root, count);
    }


    // Validate Binary Search Tree
    // Range Comparison Problem
    public boolean isValid(Tree root, long max, long min) {
        if(root == null) return true;

        if(root.val <= min || root.val >= max) return false;

        return isValid(root.left, root.val, min) && isValid(root.right, max, root.val);
    }

    // Given a imbalance binary tree, print the leaf nodes then remove those leaf node, print the new leaf nodes until only root node left.
    static List<List<Integer>> res = new ArrayList<>();
    public static int fallingLeave(Tree root) {
        if(root == null) return -1;
        int l = fallingLeave(root.left);
        int r = fallingLeave(root.right);

        int height = 1 + Math.max(l, r);

        if(res.size() == height) {
            res.add(new ArrayList<>());
        }
        res.get(height).add(root.val);
        return height;
    }


    public static void main(String[] args) {
        Tree tree = Tree.createDummyTree();
        Tree.printTree(tree);
        System.out.print("PreOrder is: ");
        fallingLeave(tree);
        System.out.println(res);
//        Tree.printTree(tree);
//        System.out.print("PreOrder is: ");
//        postOrder(tree);
//        System.out.println();
//        System.out.print("PreOrder is: ");
//        postOrderI(tree);
    }
}
