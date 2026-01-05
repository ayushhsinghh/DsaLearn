package org.ayushsingh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class BackTracking {


        // Template
        // void backtrack(State state) {
        //
        //    if (goalReached(state)) { // Success State
        //        recordAnswer(state); // Print or Add to result
        //        return;
        //    }
        //
        //    for (Choice choice : choices) {
        //        if (!isValid(choice, state)) continue;
        //
        //        apply(choice, state);   // choose
        //        backtrack(state);       // explore
        //        undo(choice, state);    // un-choose
        //    }
        // }

    public static List<String> Subsets(String input) {
        List<String> result = new ArrayList<>();
        subset2(0,input, new StringBuilder(), result);
        return result;
    }
    public static void subsets(int i, String input, StringBuilder curr, List<String> result) {
        if(i == input.length()) {
            result.add(curr.toString());
            return;
        }
            curr.append(input.charAt(i));
            subsets(i + 1, input, curr, result); // Include
            curr.deleteCharAt(curr.length() - 1); // Backtrack(Undo)

            subsets(i + 1, input, curr, result); // Exclude
    }

    // HandlesDuplicates
    public static void subset2(int i, String input, StringBuilder curr, List<String> result) {
        result.add(curr.toString());

        for(int j = i; j < input.length(); j++) {

            if(j > i && input.charAt(j-1) == input.charAt(j)) { // this will skip the duplicates, Note: Input MUST BE SORTED
                continue;
            }

            curr.append(input.charAt(j));
            subset2(j + 1, input, curr, result); // Include
            curr.deleteCharAt(curr.length() - 1); // Backtrack(Undo)
        }
    }

    public static List<String> permutations(String input) {
        List<String> result = new ArrayList<>();
        char[] chars = input.toCharArray();
        Arrays.sort(chars);
        boolean[] visited = new boolean[input.length()];
        permutations(new String(chars), new StringBuilder(), result, visited);
        return result;
    }

    public static void permutations(String input, StringBuilder curr, List<String> result, boolean[] visited) {
        if(curr.length() == input.length()) {
            result.add(curr.toString());
            return;
        }

        for(int i = 0; i < input.length(); i++) {

            if(visited[i]) continue;

            // Used to Handle Duplicates ?? HOW ??
            //  We skip when !used[i - 1] because it means the previous identical character has not been chosen in the current permutation prefix.
            // If we allow the current duplicate to be chosen in that situation, we would start a new permutation branch that is identical to one already explored, causing duplicate permutations.
            if(i > 0 && input.charAt(i-1) == input.charAt(i) && !visited[i-1]) continue; // Only WORKS if the Input is SORTED

            visited[i] = true;
            curr.append(input.charAt(i));
            permutations(input, curr, result, visited);

            curr.deleteCharAt(curr.length() - 1);
            visited[i] = false;
        }
    }

    public static List<List<Integer>> combinationSum(int[] nums,  int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        combinationSum(0, nums, target, new ArrayList<>(), result);
        return result;
    }

    public static void combinationSum(int start, int[] nums, int target, List<Integer> curr, List<List<Integer>> result) {
        if(target == 0) {
            result.add(new ArrayList<>(curr));
            return;
        }

        for (int i = start; i < nums.length; i++) {

            if(i > start && nums[i] == nums[i-1]) continue;

            if(nums[i] > target) break;
            curr.add(nums[i]);
            combinationSum(i+1, nums, target - nums[i], curr, result);
            curr.remove(curr.size()-1);
        }
    }

    public static List<int[]> Nqueens(int n) {
        List<int[]> result = new ArrayList<>();
        int[][] board = new int[n][n];

        boolean[] colUsed = new boolean[n];
        boolean[] mainD = new boolean[2 * n - 1];
        boolean[] antiD = new boolean[2 * n - 1];
        Nqueens(0, n, board, colUsed, mainD, antiD, result);

        return result;
    }
    public static void Nqueens(int start, int n, int[][] board, boolean[] colUsed, boolean[] mainD, boolean[] antiD,  List<int[]> result) {

        if(start == n) {
            result.add(getQueenPosition(board));
            return;
        }

        for(int i = 0; i < n; i++) {
            if(colUsed[i] || antiD[start+i] || mainD[start - i + (n-1)])  continue;

            board[start][i] = 1;
            colUsed[i] = true;
            mainD[start - i + (n-1)] = true;
            antiD[start + i] = true;

            Nqueens(start + 1, n, board, colUsed, mainD, antiD, result);

            board[start][i] = 0;
            colUsed[i] = false;
            mainD[start - i + (n-1)] = false;
            antiD[start + i] = false;
        }
    }
    public static int[] getQueenPosition(int [][] board) {
        int[] ans = new int[board.length];
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if(board[i][j] == 1) ans[i] = j;
            }
        }
        return ans;
    }





    // Permutation of Strings - II
    // When generating permutations with duplicates, I sort the input and ensure that duplicate elements are only chosen in a specific order.
    // I skip a duplicate if its previous identical element has not been used in the current path, which prevents duplicate sibling branches.

    public static void main(String[] args) {
//        var result = combinationSum(new int[]{1,1,2,3,5,3,5,6,3,5}, 9);
        List<int[]> result = Nqueens(4);
        for(int[] row : result) {
            System.out.println(Arrays.toString(row));
        }
//        System.out.println(result);
    }

}
