import java.util.ArrayList;
import java.util.Collections;

public class SkipList {

    public SkipListNode head;
    public SkipListNode tail;
    public int height;
    public Randomizer randomizer;
    private final int NEG_INF = Integer.MIN_VALUE;
    private final int POS_INF = Integer.MAX_VALUE;

    SkipList() {
        /*
         * DO NOT EDIT THIS FUNCTION
         */
        this.head = new SkipListNode(NEG_INF, 1);
        this.tail = new SkipListNode(POS_INF, 1);
        this.head.next.add(0, this.tail);
        this.tail.next.add(0, null);
        this.height = 1;
        this.randomizer = new Randomizer();
    }

    public boolean delete(int num) {
        // TO be completed by students
        SkipListNode pred =this.head, succ =this.tail;
        boolean ans=false;
        for(int i=this.height-1;i>-1;i--){
            // System.out.println(pred.value+" "+ pred.next.get(i).value);
            while (pred.next.get(i).value < num) {
                pred = pred.next.get(i);
            }
            // System.out.println(i);
            succ = pred.next.get(i);
            if(succ.value==num){
                // System.out.println("found");
                pred.next.set(i,succ.next.get(i));
                // return true;
                ans=true;
            }

        }
        deleteEmptyList();

        // print();
        // System.out.println();
        return ans;
    }
    private void deleteEmptyList(){
        // delete all the head->tail pointing sublists
        for(int i=this.height-1;i>0;i--){
            // System.out.println(this.head.height+" existing");

            if(this.head.next.get(i)==this.tail){
                // System.out.println("empty");
                // this.head.next.set(i,null);
                this.head.next.remove(i);
                // System.out.println(ptr);
                // this.tail.next.set(i,null);
                this.height--;
                this.head.height--;
                this.tail.height--;
            }
            else{
                break;
            }

        }
    }


    public boolean search(int num) {
        // TO be completed by students
        SkipListNode pred =this.head, succ =this.tail;

        for(int i=this.height-1;i>-1;i--){
            // System.out.println(pred.value+" "+ pred.next.get(i).value);
            while (pred.next.get(i).value < num) {
                pred = pred.next.get(i);
            }
            // System.out.println(i);
            succ = pred.next.get(i);
            if(succ.value==num){
                // System.out.println("found");
                return true;
            }
        }
        // System.out.println("not found");

        return false;
    }

    public Integer upperBound(int num) {
        // TO be completed by students
        SkipListNode pred =this.head, succ =this.tail;

        for(int i=this.height-1;i>-1;i--){
            // System.out.println(pred.value+" "+ pred.next.get(i).value);
            while (pred.next.get(i).value <= num) {
                pred = pred.next.get(i);
            }
            // System.out.println(i);
            succ = pred.next.get(i);
        }
        return succ.value;
    }

    public void insert(int num) {
        // TO be completed by students
        int height_in = 1;
        while (this.randomizer.binaryRandomGen() == true) {
            height_in++;

            if (height_in > this.height) {
                this.height = height_in;
                this.head.next.add(this.height - 1, this.tail);
                this.head.height++;
                this.tail.next.add(this.height - 1, null);
                this.tail.height++;

                break;
            }
        }
        // System.out.println("height in "+ height_in);

        SkipListNode elem = new SkipListNode(num, height_in);
        for (int i = 0; i < height_in; i++) {
            elem.next.add(i, this.tail);
        }
        SkipListNode pred =this.head, succ =this.tail;

        for (int i = this.height - 1; i > height_in - 1; i--) {
            // calculating the lower bound in the skiplist and then using it for further
            // bounds
            while (pred.next.get(i).value < elem.value) {
                pred = pred.next.get(i);
            }
            succ = pred.next.get(i);
            // System.out.println("1st loop");
            // System.out.println("Predessor: " + pred.value + " & i= " + i);
            // System.out.println("successor: " + succ.value + " & i= " + i);
            // System.out.println("elem: " + elem.value + " & i= " + i);
        }
        // print();

        for (int i = height_in - 1; i > -1; i--) {
            // System.out.println();
            // System.out.println("2nd loop");
            // System.out.println("Predessor: " + pred.value + " & i= " + i);
            // System.out.println("successor: " + succ.value + " & i= " + i);
            // System.out.println("elem: " + elem.value + " & i= " + i);
            // System.out.println(height_in);
            while (pred.next.get(i).value < elem.value) {
                pred = pred.next.get(i);
            }
            succ = pred.next.get(i);
            elem.next.set(i, succ);
            pred.next.set(i, elem);

        }

    }

    public void print() {
        /*
         * DO NOT EDIT THIS FUNCTION
         */
        for (int i = this.height; i >= 1; --i) {
            SkipListNode it = this.head;
            while (it != null) {
                if (it.height >= i) {
                    System.out.print(it.value + "\t");
                } else {
                    System.out.print("\t");
                }
                it = it.next.get(0);
            }
            System.out.println("null");
        }
    }


}