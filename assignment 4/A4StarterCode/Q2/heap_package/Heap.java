package heap_package;
import java.util.ArrayList;

public class Heap{

	protected Node root;								// root of the heap
	protected Node[] nodes_array;                    // Stores the address of node corresponding to the keys
	private int max_size;                           // Maximum number of nodes heap can have
	private static final String NullKeyException = "NullKey";      // Null key exception
	private static final String NullRootException = "NullRoot";    // Null root exception
	private static final String KeyAlreadyExistsException = "KeyAlreadyExists";   // Key already exists exception

	/*
	   1. Can use helper methods but they have to be kept private.
	   2. Not allowed to use any data structure.
	*/

	public Heap(int max_size, int[] keys_array, int[] values_array) throws Exception{

		/*
		   1. Create Max Heap for elements present in values_array.
		   2. keys_array.length == values_array.length and keys_array.length number of nodes should be created.
		   3. Store the address of node created for keys_array[i] in nodes_array[keys_array[i]].
		   4. Heap should be stored based on the value i.e. root element of heap should
		      have value which is maximum value in values_array.
		   5. max_size denotes maximum number of nodes that could be inserted in the heap.
		   6. keys will be in range 0 to max_size-1.
		   7. There could be duplicate keys in keys_array and in that case throw KeyAlreadyExistsException.
		*/

		/*
		   For eg. keys_array = [1,5,4,50,22] and values_array = [4,10,5,23,15] :
		   => So, here (key,value) pair is { (1,4), (5,10), (4,5), (50,23), (22,15) }.
		   => Now, when a node is created for element indexed 1 i.e. key = 5 and value = 10,
		   	  that created node address should be saved in nodes_array[5].
		*/

		/*
		   n = keys_array.length
		   Expected Time Complexity : O(n).
		*/

		this.max_size = max_size;
		this.nodes_array = new Node[this.max_size];

		// To be filled in by the student
		heapBuilder(keys_array, values_array, keys_array.length);
	}

	public ArrayList<Integer> getMax() throws Exception{

		/*
		   1. Returns the keys with maximum value in the heap.
		   2. There could be multiple keys having same maximum value. You have
		      to return all such keys in ArrayList (order doesn't matter).
		   3. If heap is empty, throw NullRootException.

		   Expected Time Complexity : O(1).
		*/
		if(this.root==null){
			throw new Exception(NullRootException);
		}
		ArrayList<Integer> max_keys = new ArrayList<Integer>();    // Keys with maximum values in heap.

		// To be filled in by the student
		maxHelper(max_keys);
		// System.out.println(max_keys);
		return max_keys;

	}

	public void insert(int key, int value) {

		/*
		   1. Insert a node whose key is "key" and value is "value" in heap
		      and store the address of new node in nodes_array[key].
		   2. If key is already present in heap, throw KeyAlreadyExistsException.

		   Expected Time Complexity : O(logn).
		*/
		// To be filled in by the student
		insertHelper(key,value);
	}


	public ArrayList<Integer> deleteMax() throws Exception{

		/*
		   1. Remove nodes with the maximum value in the heap and returns their keys.
		   2. There could be multiple nodes having same maximum value. You have
		      to delete all such nodes and return all such keys in ArrayList (order doesn't matter).
		   3. If heap is empty, throw NullRootException.

		   Expected Average Time Complexity : O(logn).
		*/

		ArrayList<Integer> max_keys = new ArrayList<Integer>();   // Keys with maximum values in heap that will be deleted.

		// To be filled in by the student
		if(this.root==null){
			throw new Exception(NullRootException);
		}
		deleteHelper(max_keys);
		return max_keys;
	}

	public void update(int key, int diffvalue) throws Exception{

		/*
		   1. Update the heap by changing the value of the node whose key is "key" to value+diffvalue.
		   2. If key doesn't exists in heap, throw NullKeyException.

		   Expected Time Complexity : O(logn).
		*/

		// To be filled in by the student
		if(this.nodes_array[key]==null){
			throw new Exception(NullKeyException);
		}
		// this.nodes_array[key].value+=diffvalue;
		// System.out.println("Value: "+key+" "+ this.nodes_array[key].value);
		// for(int i=0;i<this.nodes_array.length;i++){
		// 	if(this.nodes_array[i]!=null){
		// 		System.out.println("Key: "+i+" "+ this.nodes_array[i].key);
		// 	}
		// }
		updateHelper(key,diffvalue);


	}

	public int getMaxValue() throws Exception{

		/*
		   1. Returns maximum value in the heap.
		   2. If heap is empty, throw NullRootException.

		   Expected Time Complexity : O(1).
		*/

		// To be filled in by the student
		if(this.root==null){
			throw new Exception(NullRootException);
		}
		return this.root.value;

	}

	public ArrayList<Integer> getKeys() throws Exception{

		/*
		   1. Returns keys of the nodes stored in heap.
		   2. If heap is empty, throw NullRootException.

		   Expected Time Complexity : O(n).
		*/

		ArrayList<Integer> keys = new ArrayList<Integer>();   // Stores keys of nodes in heap

		// To be filled in by the student
		if(this.root==null){
			throw new Exception(NullRootException);
		}
		getKeysHelper(this.root,keys);

		return keys;
	}

	// Write helper functions(if any) here (They have to be private).

	private void maxHelper( ArrayList<Integer> arr){
		ArrayList<Node>temp = new ArrayList<>();
		temp.add(this.root);
		int i=0;
		Node iter = this.root;
		while(i<temp.size()){
			iter = temp.get(i);
			// System.out.println(iter.key);
			arr.add(iter.key);
			if(iter.left!=null  && iter.left.value == iter.value){
				temp.add(iter.left);
			}
			if(iter.right!=null  && iter.right.value == iter.value){
				temp.add(iter.right);
			}
			i++;
		}
		return;
	}
	private void deleteHelper(ArrayList<Integer> arr){
		ArrayList<Node>temp = new ArrayList<>();
		temp.add(this.root);
		int i=0;
		Node iter = this.root;
		while(i<temp.size()){
			iter = temp.get(i);
			// System.out.println(iter.key);
			arr.add(iter.key);
			if(iter.left!=null  && iter.left.value == iter.value){
				temp.add(iter.left);
			}
			if(iter.right!=null  && iter.right.value == iter.value){
				temp.add(iter.right);
			}
			i++;
		}
		// System.out.println(arr);
		for(Node n:temp){
			iter = n;
			// find the last node in bfs of this heap
			iter=findLeaf(iter);
			if(iter.left!=null)iter = iter.left;
			// swap iter and n
			this.nodes_array[n.key]=null;
			n.key = iter.key;
			n.value = iter.value;
			if(iter.parent.right!=null && iter.parent.right==iter)iter.parent.right =null;
			else iter.parent.left =null;
			heightBalancer(iter.parent);
			isCompleteBalancer(iter.parent);
			// print2D();
		}
		arrangeDelete(this.root);

		return;
	}
	private Node findLeaf(Node n){
		if(n.left==null && n.right==null){
			return n;
		}
		Node node = root;
		while (node.right != null) {
			int leftHeight = height2(node.left);
			int rightHeight = height2(node.right);
			if (leftHeight == rightHeight) {
				// Both left and right subtrees are complete, so the last node must be in the right subtree
				node = node.right;
			} else {
				// The left subtree is complete, but the right subtree is not, so the last node must be in the left subtree
				node = node.left;
			}
		}
		return node;
	}
	private int height2(Node n) {
        if (n == null)
            return 0;
        else{
            return n.height;
        }
    }
	private void insertHelper(int key, int value){
		Node newNode = new Node(key,value,null);
		// System.out.println(this.root);

		if(this.root==null){
			this.root=newNode;
			nodes_array[key]=this.root;
			return;
		}
		// using is_complete for getting the location to insert in the heap
		Node temp = this.root;
		// temp is the position where the node where the new node is to be inserted
		temp = insPos(temp);
		// insert the node
		if(temp.left==null){
			temp.left = newNode;
			newNode.parent = temp;
		}
		else if(temp.right==null){
			temp.right = newNode;
			newNode.parent = temp;
		}

		isCompleteBalancer(newNode);
		this.nodes_array[key]  = newNode;
		arrangeInsert(newNode);
		heightBalancer(temp);
		// System.out.println(key+ " "+value);
		// for(int i=0;i<this.nodes_array.length;i++){
		// 	if(this.nodes_array[i]!=null){
		// 		System.out.println("Key: "+i+" "+ this.nodes_array[i].key);
		// 	}
		// }
		// System.out.println("height: "+ height2(temp.left)+ " "+height2(temp.right) +" "+temp.height +" :val "+temp.value);
	}

	private void arrangeInsert(Node head){
		// this function will arrange the heap after insertion
		if(head.parent==null){
			return;
		}
		// heapify the tree after insertion

		if(head.value>head.parent.value){
			int temp_val = head.value,temp_key=head.key;
			head.value = head.parent.value;
			head.key = head.parent.key;
			head.parent.value = temp_val;
			head.parent.key = temp_key;
			this.nodes_array[head.key] = head;
			this.nodes_array[head.parent.key] = head.parent;
			arrangeInsert(head.parent);
		}
		return;
	}

	private void arrangeDelete(Node head){
		if(head.left==null && head.right==null){
			return;
		}
		int maxm = head.left.value,side = 0;
		if(head.right!=null && maxm<head.right.value){
			maxm = head.right.value;
			side = 1;
		}

		if(head.value<maxm){
			int temp_val = head.value,temp_key=head.key;
			if(side ==0){
				head.value = head.left.value;
				head.key = head.left.key;
				head.left.value = temp_val;
				head.left.key = temp_key;
				this.nodes_array[head.key] = head;
				this.nodes_array[head.left.key] = head.left;
				arrangeDelete(head.left);
			}
			else{
				head.value = head.right.value;
				head.key = head.right.key;
				head.right.value = temp_val;
				head.right.key = temp_key;
				this.nodes_array[head.key] = head;
				this.nodes_array[head.right.key] = head.right;
				arrangeDelete(head.right);
			}
			return;
		}

	}


	private void isCompleteBalancer(Node head){
		// balances the is_complete property of the tree after insertion
		if(head.parent==null){
			return;
		}
		if(head.parent.left!=null && head.parent.right!=null){
			if(head.parent.left.height==head.parent.right.height)
			head.parent.is_complete=head.parent.left.is_complete && head.parent.right.is_complete;
			// System.out.print(head.value+ " " +head.is_complete+" ");
		}
		else{
			// System.out.print(head.value+ " " +head.is_complete+" ");
			head.parent.is_complete=false;
		}
		isCompleteBalancer(head.parent);
	}



	// this helper function will return the position where the node is to be inserted
	private Node insPos(Node head){
		// get pos by checking if the left and right are complete or not in O(logn) time
		// Whether the binary sub-tree rooted at this node is full or not
		// if not complete then return the node
		while(head.is_complete==false&& head.right!=null){
			if(head.left.is_complete==false){

				head=head.left;
			}else{
				head=head.right;
			}
		}
		while(head.left!=null && head.is_complete){

			head = head.left;
		}
		return head;
	}

	private void heightBalancer(Node head){
		// this function will balance the height of the tree after insertion
		if(head==null){
			return;
		}
		head.height = height2(head.left)+1;
		heightBalancer(head.parent);
	}

	private void updateHelper(int key, int value){
		// this function will update the value of the node with key
		Node temp = this.nodes_array[key];
		temp.value += value;
		arrangeInsert(temp);
		arrangeDelete(temp);
		heightBalancer(temp);
	}

	private void getKeysHelper(Node head,ArrayList<Integer> arr){
		// this function will return the keys of the nodes in the heap
		if(head==null){
			return;
		}
		arr.add(head.key);
		getKeysHelper(head.left,arr);
		getKeysHelper(head.right,arr);
	}




// This helper will build the initial heap from the given array
	private void heapBuilder(int[] keys,int[] values,int n) throws Exception{
		for(int i=n/2-1;i>=0;i--){
			heapify(keys,values,i);
		}
		this.root= new Node(keys[0],values[0],null);
		this.nodes_array[keys[0]] = this.root;
		initialTree(this.root,keys,values,keys.length,0);
		for(int i=n/2-1;i>=0;i--){
			// System.out.println("Key: "+keys[i]+" "+this.nodes_array[keys[i]].height+" "+this.nodes_array[keys[i]].is_complete);
			this.nodes_array[keys[i]].height = height2(this.nodes_array[keys[i]].left)+1;
			if(height2(this.nodes_array[keys[i]].left)==height2(this.nodes_array[keys[i]].right)&& this.nodes_array[keys[i]].left!=null && this.nodes_array[keys[i]].right!=null)
			this.nodes_array[keys[i]].is_complete = this.nodes_array[keys[i]].left.is_complete && this.nodes_array[keys[i]].right.is_complete;
			else this.nodes_array[keys[i]].is_complete = false;
		}
		// print2D();
	}

	private void initialTree(Node head,int[]keys,int[]values,int n,int i) throws Exception{
		// create a complete binary tree from the array
			if(head==null || i>=n){
				return;
			}
			if(2*i+1<n){
				head.left = new Node(keys[2*i+1],values[2*i+1],head);
				if(this.nodes_array[keys[2*i+1]]!=null){
					throw new Exception(KeyAlreadyExistsException);
				}
				this.nodes_array[keys[2*i+1]] = head.left;

			}

			if(2*i+2<n){
				head.right = new Node(keys[2*i+2],values[2*i+2],head);
				if(this.nodes_array[keys[2*i+2]]!=null){
					throw new Exception(KeyAlreadyExistsException);
				}
				this.nodes_array[keys[2*i+2]] = head.right;
			}
			initialTree(head.left,keys,values,n,2*i+1);
			initialTree(head.right,keys,values,n,2*i+2);
	}

	private void heapify(int[] keys,int[] values,int i ){
		// implement heapify algorithm
		int largest = i;
		int left = 2*i+1,right = 2*i+2;
		if(left<keys.length && values[left]>values[largest]){
			largest = left;
		}
		if(right<keys.length && values[right]>values[largest]){
			largest = right;
		}
		if(largest!=i){
			swap(keys,i,largest);
			swap(values,i,largest);
			heapify(keys,values,largest);
		}

	}

	private void swap(int[]arr,int i,int j){
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}


// end of heap builder

	public void print2DUtil(Node head, int space)
    {
        // Base case
		// System.out.println("line246");
        if (head == null)
            return;
		int COUNT = 5;
		// System.out.println("Hello");

        // Increase distance between levels
        space += COUNT;

        // Process right child first
        print2DUtil(head.right, space);

        // Print current node after space
        // count
        System.out.print("\n");
        for (int i = COUNT; i < space; i++)
            System.out.print(" ");
        System.out.print(head.value+","+head.key + "\n");

        // Process left child
        print2DUtil(head.left, space);
    }

    // Wrapper over print2DUtil()
    public void print2D()
    {
        // Pass initial space count as 0
        print2DUtil(root, 0);
    }



}
