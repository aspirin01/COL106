public class LinkedList{

    public Node head,tail;
    public int size=0;
    public LinkedList(){
        head = null;
    }

    public void insert(int c){
        //to be completed by the student
        Node temp = new Node(c);
        size++;
        if(head==null){
            head = temp;
            tail = head;
        }

        else{
            tail.next = temp;
            tail = temp;
        }
    }

    public int len(){
        //to be completed by the student
        return size;
    }
    public void display(){
        if(head==null){
            return;
        }
        Node temp = head;
        while(temp!=null){
            System.out.print(temp.data+" ");
            temp = temp.next;
        }
        System.out.println();
    }
}


