import java.util.*;

public class BST {

    public BSTNode root;

    public BST() {
        root = null;
    }
    public int height2(BSTNode n) {
        if (n == null)
            return 0;
        else{
            return n.height;
        }
    }
    private BSTNode insertRecn(BSTNode r,int num){
        if(r==null){
            return new BSTNode(num);
        }
        else if(r.value>num){
            r.left= insertRecn(r.left, num);
        }

        else if(r.value<num){
            r.right= insertRecn(r.right, num);
        }
        else{
            // System.out.println("Num already exists!");
            return r;
        }
        // updating the heights

        r.height=Math.max(height2(r.left),height2(r.right))+1;
        return r;
    }

    public void insert(int num) {
        // TO be completed by students
        root = insertRecn(root, num);
        // printTree(root, 0);

    }

    public boolean delete(int num) {
        // TO be completed by students
        boolean ans =false;
        if(search(num))ans= true;

        root = deleteRecn(root, num);

       return ans;
    }


    public BSTNode deleteRecn(BSTNode head, int num){
        if(head==null){
            return head;
        }
        if(head.value<num){
            head.right = deleteRecn(head.right, num);
        }
        else if(head.value>num){
            head.left = deleteRecn(head.left, num);
        }
        else{
            if(head.left==null){
                return head.right;
            }
            if(head.right==null){
                return head.left;
            }
            // find the predessor and replace
            // head = head.left;
            BSTNode temp = head.left;
            int predVal = temp.value;
            while(temp.right!=null){
                temp=temp.right;
                predVal =temp.value;
            }
            head.value = predVal;
            head.left = deleteRecn(head.left, predVal);
        }
        int lh=0,rh=0;
        if(head.left!=null){
            lh=head.left.height;
        }
        if(head.right!=null){
            rh=head.right.height;
        }
        head.height=Math.max(lh,rh)+1;
        return head;
    }
    public boolean search(int num) {
        // TO be completed by students

        BSTNode temp = root;
        while (temp != null) {
            if (temp.value > num) {
                temp = temp.left;
            } else if (temp.value < num) {
                temp = temp.right;
            } else {
                // System.out.println(temp.value + " found");
                return true;
            }

        }
        // System.out.println(num + " not found");

        return false;
    }

    public void recursive(BSTNode r,ArrayList<Integer>arr,int order){
        if(r==null)return;
        // pre order
        if(order==0){
            arr.add(r.value);
            recursive(r.left, arr,order);
            recursive(r.right, arr,order);
        }
        // in order
        else if(order==1){
            recursive(r.left, arr,order);
            arr.add(r.value);
            recursive(r.right, arr,order);
        }
        // post order
        else if(order==2){
            recursive(r.left, arr,order);
            recursive(r.right, arr,order);
            arr.add(r.value);
        }
    }


    public ArrayList<Integer> inorder() {
        // TO be completed by students
        ArrayList<Integer> al = new ArrayList<>();
        recursive(root, al,1);
        return al;
    }

    public ArrayList<Integer> preorder() {
        // TO be completed by students
        ArrayList<Integer> al = new ArrayList<>();
        recursive(root, al,0);
        return al;
    }

    public ArrayList<Integer> postorder() {
        // TO be completed by students
        ArrayList<Integer> al = new ArrayList<>();
        recursive(root, al,2);
        return al;
    }

    public void printTree(BSTNode node, int level) {
        if (node != null) {
            printTree(node.right, level + 1);
            for (int i = 0; i < level; i++) {
                System.out.print("  ");
            }
            System.out.println(node.value);
            printTree(node.left, level + 1);
        }
    }
    public void printFn(){
        printTree(root,0);
    }

}
