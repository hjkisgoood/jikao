import java.util.*;

public class Solution {

    public class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;
        public TreeNode(int x) {
            this.val = x;
        }
        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    HashMap<Integer, Integer> map = new HashMap<>();
    public int[] levelPrintTree(int[] pre, int[] mid) {
        int [] res = new int[mid.length];
        for(int num :pre)
            for(int i = 0 ; i < mid.length ; i++)
                if(num == mid[i]) map.put(mid[i], i);//存储索引
        TreeNode root = help(pre, 0 ,pre.length, mid, 0, mid.length );
        Stack<TreeNode> st = new Stack<>();
        st.push(root);
        int index = 0;
        while (!st.isEmpty()){
            int size = st.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = st.pop();
                if(node.left != null) st.push(node.left);
                if(node.right != null) st.push(node.right);
                res[index] = node.val;
                index++;
            }
        }
        return res;
    }//层序遍历输出,中序遍历前序遍历构造
    private TreeNode help(int[] pre,int preBegin, int preEnd, int[] mid, int midBegin, int midEnd) {
        if(midBegin >= midEnd || preBegin >= preEnd) return null;
        TreeNode root = new TreeNode(pre[preBegin]);
        int index = map.get(pre[preBegin]);
        int len = index - midBegin;
        root.left = help(pre, preBegin + 1, preBegin + len, mid, midBegin, index);
        root.right = help(pre, preBegin + len + 1, preEnd, mid, index+1, midEnd);
        return root;
    }

    List<List<Integer>> res1 = new ArrayList<List<Integer>>();
    List<List<Integer>> res2 = new ArrayList<List<Integer>>();
    List<Integer> path1 = new ArrayList<>();
    List<Integer> path2 = new ArrayList<>();

    public int[] trunkLoad(int w, int[] c) {
        trunkLoadHelp1(w, c, 0);
        int[] res = new int[c.length];
        int index = 0;
        int min = c.length;
        for(int i = 0; i < res1.size(); i++) {
            if(res1.get(i).size() < min) {
                index = i;
                min = res1.get(i).size();
            }
        }
        for(int i : res2.get(index)){
            res[i] = 1;
        }
        return res;
    }//卡车装货问题

    private void trunkLoadHelp1(int target, int[] nums, int start){
        if( target <= 0 ){
            if(target == 0) {
                res1.add(new ArrayList<>(path1));
                res2.add(new ArrayList<>(path2));
            }
            return;
        }

        for(int i = start; i < nums.length; i++){
            path1.add(nums[i]);
            path2.add(i);
            trunkLoadHelp1(target - nums[i], nums, i + 1);
            path1.remove(path1.size() - 1);//回溯
            path2.remove(path2.size() - 1);
        }
    }

    public int maxSubArrSum(int[] nums) {
        int len = nums.length;
        if (len == 0) {return 0;}
        if(len == 1) {return nums[0];}
        int l , r;
        int max = nums[0];
        int sum;
        int[] help1 = new int[len];
        int[] help2 = new int[len];
        for (int i = 0; i < len; i++) {
           sum = nums[i];
           r = i+1;l = i;
               while(r < len && nums[r] >= 0){
                   sum += nums[r];
                   r++;
               }
               while(l < r - 1 && nums[l] < 0){
                   sum -= nums[l];
                   l++;
               }
               help1[i] = sum;
        }
        for (int i = 0; i < len; i++) {
            sum = nums[i];
            r = i+1;l = i;
            while(r < len && nums[r] < 0){
                sum += nums[r];
                r++;
            }
            while(l < r - 1 && nums[l] >= 0){
                sum -= nums[l];
                l++;
            }
            help2[i] = sum;
        }
        for(int i : help1){
            if(i > max) max = i;
        }
        for(int i = 0; i < len;){
            int start = i;
            sum = help1[i];
            while( i < len && nums[i] >= 0) i++;
            if(i < len) sum += help2[i];
            while (i < len && nums[i] < 0 ) i++;
            sum += help1[i];
            if(sum > max) max = sum;

        }
       return max;
    }//最大连续子数组和
}

/*

 public int maxSubArrSum(int[] nums) {
        int len = nums.length;
        if (len == 0) {return 0;}
        if(len == 1) {return nums[0];}
        int l = 0, r = 1;
        int max = nums[0];
        int sum;
       for (int i = 0; i < len; i++) {
           sum = 0;
           r = i;l = i;
               while(r < len && nums[r] > 0){
                   sum += nums[r];
                   r++;
                   if(sum > max) max = sum;
               }
               while(l < r && nums[l] < 0){
                   sum -= nums[l];
                   l++;
                   if(sum > max) max = sum;
               }
               max = Math.max(max, sum);
       }
       return max;
    }
 */
