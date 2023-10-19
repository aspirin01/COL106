import java.util.ArrayList;

public class Tree {

    public TreeNode root;

    public Tree() {
        root = null;
    }

    private int lexigoComp(String a,String b){
        for(int i = 0 ;i<Math.min(a.length(),b.length());i++){
            if(a.charAt(i)>b.charAt(i))return 0;
            if(a.charAt(i)<b.charAt(i))return 1;
        }
        if(a.length()>b.length())return 0;
        return 1;
    }
    public boolean nodeIsLeaf(TreeNode node){
        if(node.children.size()==0)return true;
        return false;
    }

    public void insert(String s) {
        // TO be completed by students
        if(root==null){
            root = new TreeNode();
            root.s.add(0,s);
            root.val.add(0,1);
            root.count++;
            return;
        }
        else{
            TreeNode temp = root,prev = null;
            int i = 0;
            while(temp!=null && !nodeIsLeaf(temp)){
                prev = temp;
                i=0;
                while(i<temp.s.size()&& lexigoComp(temp.s.get(i),s)==1){
                    i++;
                }
                temp = temp.children.get(i);
            }
            i=0;
            while(i<temp.s.size()&& lexigoComp(temp.s.get(i),s)==1){
                i++;
            }
            if(temp.count==3){
                splitTree(temp, prev);
            }
            temp.s.add(i,s);
            temp.val.add(i,1);
            temp.count++;
        }
    }
    private void splitTree(TreeNode child,TreeNode parent){

    }
    public boolean delete(String s) {
        // TO be completed by students
        return false;
    }

    public boolean search(String s) {
        // TO be completed by students
        boolean ans = false;
        searchHelper(s, root, ans);
        return ans;
    }

    private void searchHelper(String st, TreeNode head,boolean found) {
        if(found==true)return;
        if(head == null)
            found =  false;
        if(head.s.contains(st)){
            found =true;
        }
        for(TreeNode t:head.children){
            searchHelper(st, t,found);
        }

    }
    public int increment(String s) {
        // TO be completed by students
        return 0;
    }

    public int decrement(String s) {
        // TO be completed by students
        return 0;
    }

    public int getHeight() {
        // TO be completed by students
        return root.height;
    }

    public int getVal(String s) {
        // TO be completed by students
        return 0;
    }

    public static void main(String[]args){
        Tree t = new Tree();
        String a = "abc",b="cdr";
        System.out.println(t.lexigoComp(b,a));
    }
}
