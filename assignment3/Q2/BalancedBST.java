public class BalancedBST extends BST {

    public void insert(int key){
        // TO be completed by students

        root=this.insertHelper(root, key);
        // printFn();
    }
    private BSTNode insertHelper(BSTNode head, int key){
        if(head==null){
            return (new BSTNode(key));
        }
        else if(head.value>key){
            head.left = this.insertHelper(head.left, key);
        }
        else if(head.value<key){
            head.right = this.insertHelper(head.right, key);
        }
        else{
            // handle the case with multiple keys
            return head;
        }
        head.height=Math.max(height2(head.left),height2(head.right))+1;
        int heightDiff = height2(head.left)-height2(head.right);
        // System.out.println(heightDiff);

        // here are the four cases of rotation possible: LL, LR, RR, RL
        if(heightDiff>1){
            // LL case
            if(key<head.left.value){
                return cwr(head);
            }
            // LR case
            head.left = acwr(head.left);
            return cwr(head);
        }

        else if(heightDiff<-1){
            // RR case
            if(key>head.right.value){
                return acwr(head);
            }
            // System.out.println(key+" "+ head.right.value);
            // RL case
            head.right= cwr(head.right);
            return acwr(head);
        }
        // no rotation performed
        else{
            return head;
        }
    }
    // clockwise rotation
    private BSTNode cwr(BSTNode z){
        BSTNode y = z.left;
        BSTNode x = y.right;
        // performing the clockwise rotation
        y.right = z;
        z.left = x;
        // updating the heights
        z.height = 1+ Math.max(height2(z.left),height2(z.right));
        y.height = 1+ Math.max(height2(y.left),height2(y.right));
        return y;

    }
    // anticlockwise rotation
    private BSTNode acwr(BSTNode z){
        // System.out.println(z.value+" "+ z.right);
        BSTNode y = z.right;
        BSTNode x = y.left;
        // performing the clockwise rotation

        y.left = z;
        z.right = x;
        // updating the heights
        z.height = 1+ Math.max(height2(z.left),height2(z.right));
        y.height = 1+ Math.max(height2(y.left),height2(y.right));
        return y;
    }

    public boolean delete(int key){
        // TO be completed by students
        // first line deletes the key by using the inherited function delete from BST class, and then a private balancer function is called in order to balance the tree which was deleted.
        if(super.delete(key)==true){
            root=this.deleteBalancer(root);
            return true;
        }
	    return false;
    }
    private BSTNode deleteBalancer(BSTNode head){
        if (root == null)
            return root;
        head.height=Math.max(height2(head.left),height2(head.right))+1;
        int heightDiff = height2(head.left)-height2(head.right);
        // System.out.println(heightDiff);
        if(heightDiff>1){
            if(height2(head.left)>=0){
                return cwr(head);
            }
            head.left = acwr(head.left);
            return cwr(head);
        }

        else if(heightDiff<-1){
            if(height2(head.right)<=0){
                return acwr(head);
            }
            // System.out.println(key+" "+ head.right.value);
            head.right= cwr(head.right);
            return acwr(head);
        }
        else{
            return head;
        }
    }

    public static void main(String[] args) {
        BalancedBST root = new BalancedBST();
        root.insert(8);
        root.insert(9);
        root.insert(11);
        root.insert(13);
        root.insert(1);
        root.insert(12);
        root.insert(6);
        root.delete(6);
        // System.out.println(root.preorder());
        // System.out.println(root.inorder());
        // System.out.println(root.postorder());
        // boolean lol =root.delete(1);
        // System.out.println(lol);
        root.printFn();
        // System.out.println(root.inorder());

    }
}
