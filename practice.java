import java.util.*;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
        left = null;
        right = null;
    }
}

public class practice {

 
    public static void solver2(int n, int k, ArrayList<ArrayList<Integer>> arr) {
        boolean ans = true;
        for (int i = 0; i < n / 2; i++) {
            for (int j = 0; j < n; j++) {

                if (arr.get(i).get(j) != arr.get(n - i - 1).get(n - j - 1)) {
                    k--;
                    if (k < 0) {
                        ans = false;
                        System.out.println("NO");
                        return;
                    }
                }
            }
        }
        if (n % 2 != 0) {
            for (int j = 0; j <= n / 2; j++) {
                if (arr.get(n / 2).get(j) != arr.get(n / 2).get(n - j - 1)) {
                    k--;
                    if (k < 0) {
                        ans = false;
                        System.out.println("NO");
                        return;
                    }
                }
            }

        }
        if (ans)
            System.out.println("YES");

    }

    public static void main(String[] args) {
        // take input of an int

        Scanner sc = new Scanner(System.in);
        // scan number of test cases
        int t = sc.nextInt();
        while (t > 0) {
            int n, k;
            n = sc.nextInt();
            k = sc.nextInt();
            ArrayList<ArrayList<Integer>> arr = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                ArrayList<Integer> temp = new ArrayList<>();
                for (int j = 0; j < n; j++) {
                    temp.add(sc.nextInt());
                }
                arr.add(temp);
            }
            solver2(n, k, arr);
            t--;
        }

    }

}
