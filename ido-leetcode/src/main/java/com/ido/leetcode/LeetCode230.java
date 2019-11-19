package com.ido.leetcode;


/**


 给定一个二叉搜索树，编写一个函数 kthSmallest 来查找其中第 k 个最小的元素。

 说明：
 你可以假设 k 总是有效的，1 ≤ k ≤ 二叉搜索树元素个数。



 *
 */
class LeetCode230 {



    private int count;

    int res = 0;

    public int kthSmallest(TreeNode root, int k) {


        //中序遍历
        traversal(root,k);

        return res;
    }

    public void traversal(TreeNode root, int k) {
        if (root == null) {

        }
        //左节点一直往下找
        if (root.left != null) {
            traversal(root.left,k);
        }

        //找根节点 需要+1了
        count += 1;
        if (count == k) {
            res = root.val;
            return;
        }

        //右节点也要一直往下找
        if (root.right != null) {
            traversal(root.right,k);
        }
    }




  public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
}