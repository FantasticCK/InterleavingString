package com.CK;

public class Main {

    public static void main(String[] args) {

//        String s1 = "aabcc",s2 = "dbbca",s3 = "aadbbcbcbc";
//        String s1 = "", s2 = "b", s3 = "b";
        String s1 = "a", s2 = "b", s3 = "ab";
        System.out.println(new Solution().isInterleave(s1, s2, s3));
    }
}

class Solution {
    public boolean isInterleave(String s1, String s2, String s3) {
        if ((s1.length() == 0 && s3.equals(s2)) || (s2.length() == 0 && s3.equals(s1))) return true;
        Boolean[][][] dp = new Boolean[s1.length() + 1][s2.length() + 1][s3.length() + 1];
        helper(s1, 0, s2, 0, s3, 0, dp);
        return dp[0][0][0];
    }

    public boolean helper(String s1, int i1, String s2, int i2, String s3, int i3, Boolean[][][] dp) {
        if (dp[i1][i2][i3] != null) {
            return dp[i1][i2][i3];
        }

        int l1 = s1.length(), l2 = s2.length(), l3 = s3.length();
        if ((l3 - i3) != (l1 - i1 + l2 - i2)) {
            dp[i1][i2][i3] = false;
            return false;
        }

        if (i1 == l1 && i2 == l2 && i3 == l3) {
            dp[i1][i2][i3] = true;
            return true;
        }

        char target = s3.charAt(i3);

        if ((i1 >= l1 && s2.charAt(i2) != target) || (i2 >= l2 && s1.charAt(i1) != target) || (i2 < l2 && i1 < l1 && s1.charAt(i1) != target && s2.charAt(i2) != target)) {
            dp[i1][i2][i3] = false;
            return false;
        }

        if (i1 >= l1 || s1.charAt(i1) != target) {
            dp[i1][i2][i3] = helper(s1, i1, s2, i2 + 1, s3, i3 + 1, dp);
        } else if (i2 >= l2 || s2.charAt(i2) != target) {
            dp[i1][i2][i3] = helper(s1, i1 + 1, s2, i2, s3, i3 + 1, dp);
        } else {
            dp[i1][i2][i3] = helper(s1, i1, s2, i2 + 1, s3, i3 + 1, dp) || helper(s1, i1 + 1, s2, i2, s3, i3 + 1, dp);
        }

        return dp[i1][i2][i3];
    }
}

// 1D DP
class Solution2 {
    public boolean isInterleave(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length()) {
            return false;
        }
        boolean[] dp = new boolean[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0 && j == 0) {
                    dp[j] = true;
                } else if (i == 0) {
                    dp[j] = dp[j - 1] && s2.charAt(j - 1) == s3.charAt(j - 1);
                } else if (j == 0) {
                    dp[j] = dp[j] && s1.charAt(i - 1) == s3.charAt(i - 1);
                } else {
                    dp[j] = dp[j] && s1.charAt(i - 1) == s3.charAt(i + j - 1)
                            || dp[j - 1] && s2.charAt(j - 1) == s3.charAt(i + j - 1);
                }
            }
        }
        return dp[s2.length()];
    }
}