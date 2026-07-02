package org.ayushsingh;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.swap;

public class TestNode {

    public static List<String> permutation(String word) {
        List<String> ans = new ArrayList<>();
        char[] words = word.toCharArray();

        permute(words, 0, ans);

        return ans;

    }

    private static void permute(char[] words, int idx, List<String> ans) {
        if(idx == words.length) {
            ans.add(new String(words));
            return;
        }


        for(int i = idx; i < words.length ; i++) {
            swap(words, idx, i);
            permute(words, idx+1, ans);
            swap(words, idx, i);
        }
    }

    private static void swap(char[] words, int i, int j) {
        char holder = words[i];
        words[i] = words[j];
        words[j] = holder;
    }


    public static void main(String[] args) {
            List<String> permutations = permutation("abcd");
            System.out.println(permutations);
    }
}
