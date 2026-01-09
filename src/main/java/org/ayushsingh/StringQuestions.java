package org.ayushsingh;

public class StringQuestions {

    // Longest Palindromic Substring
    // Expand from Center Method.
    // Logic: A palindrome mirrors around its center. I expand around every possible center—both odd and even—to find the longest palindromic substring in O(n²) time and O(1) space.
    public String longestPalindrome(String s) {
        int start = 0;
        int end = 0;
        for(int i = 0; i < s.length(); i++) {
            //
            int len = Math.max(
                    expand(s, i, i), // Odd Case, 1 middle
                    expand(s, i, i+1) // Even Case, 2 middle
            ); // Expand from all the position and file a length of palindrome.

            if (len > end - start) {
                start = i - (len - 1) / 2;
                end   = i + len / 2;
            }
        }

        return s.substring(start, end+1);
    }
    public int expand(String s, int left, int right) {
        while(left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right-left-1;
        // Why -1?
        //Because:
        //  Valid palindrome indices are (left + 1) to (right - 1)
        //  Length = (right - 1) - (left + 1) + 1 == (right - left - 1)
    }


    // Rabin-karp Algorithm
    // Create a HashFunction.
    // Hash the pattern.
    // create a fixed size window and match the hash with each window
    // if hash matches, compare the words
    // FOUND !!
    public static boolean rabinKarp(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();

        if (m > n) return false;

        int BASE = 31;
        int MOD = 1_000_000_007;

        long patternHash = 0;
        long windowHash = 0;
        long highestPower = 1; // BASE^(m-1)

        // Precompute highest power
        for (int i = 0; i < m - 1; i++) {
            highestPower = (highestPower * BASE) % MOD; // helps in rolling the hash
        }

        // Initial hash
        for (int i = 0; i < m; i++) {
            patternHash = (patternHash * BASE + (pattern.charAt(i) - 'a' + 1)) % MOD;
            windowHash  = (windowHash  * BASE + (text.charAt(i) - 'a' + 1)) % MOD;
        }

        for (int i = 0; i <= n - m; i++) {
            if (windowHash == patternHash) {
                if (text.substring(i, i + m).equals(pattern)) {
                    return true;
                }
            }

            // Roll the hash
            if (i < n - m) {
                windowHash = (windowHash - (text.charAt(i) - 'a' + 1) * highestPower % MOD + MOD) % MOD;
                windowHash = (windowHash * BASE + (text.charAt(i + m) - 'a' + 1)) % MOD;
            }
        }
        return false;
    }


    public static void main(String[] args) {
        String s = "abcdefg";
        String p = "abc";
        if(rabinKarp(s,p)) {
            System.out.println("matched");
        }
    }


}
