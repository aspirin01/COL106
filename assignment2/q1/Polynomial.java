public class Polynomial extends LinkedList{

    public Polynomial add(Polynomial p){
     //to be implemented by the student
     Polynomial result = new Polynomial();
     Node temp1 = this.head;
     Node temp2 = p.head;

     int s1=this.len(),s2=p.len();
    //  this loop is to start adding from the point where both the polynomials have the same degree
     while(s1>s2){
            result.insert(temp1.data);
            temp1 = temp1.next;
            s1--;
     }
    //  this loop is to start adding from the point when both the polynomials have the same degree
    while(s2>s1){
            result.insert(temp2.data);
            temp2 = temp2.next;
            s2--;
    }
    // this is the loop where we add the coefficients of the polynomials
     while(temp1!=null && temp2!=null){
         int sum = temp1.data + temp2.data;
         result.insert(sum);
         temp1 = temp1.next;
         temp2 = temp2.next;
     }
    //  if initial coefficient of the result is 0 then we remove it and move to the first non zero coefficient and return the result
     while(result.head.next!=null && result.head.data==0){

        // System.out.println(result.head.data);
        result.head = result.head.next;
        result.size--;
        // System.out.println(result.len());
     }
     return result;
 }


    public Polynomial mult(Polynomial p){
        //to be implemented by the student
        Polynomial result = new Polynomial();
        int s = p.len()+this.len()-1;
        Node temp1=this.head;
        Node temp2=p.head;
        for(int i=0;i<s;i++){
             result.insert(0);
         }
         Node res_index=result.head;
        while(temp1!=null){
            Node temp=res_index;
            temp2=p.head;
            int prod =0;
            // result.display();
            while(temp2!=null){
                prod=temp1.data*temp2.data;
                temp.data+=prod;
                temp=temp.next;
                temp2=temp2.next;
            }
            temp1 =temp1.next;
            res_index=res_index.next;
        }
        //  if initial coefficient of the result is 0 then we remove it and move to the first non zero coefficient and return the result
     while(result.head.next!=null && result.head.data==0){

        // System.out.println(result.head.data);
        result.head = result.head.next;
        result.size--;
        // System.out.println(result.len());
     }
     return result;
    }

    // Temporary
    public static void main(String[] args) {
        Polynomial p1 = new Polynomial();
        p1.insert(-1);
        p1.insert(0);
        p1.insert(1);
        // p1.insert();
        p1.display();
        Polynomial p2 = new Polynomial();
        p2.insert(1);
        p2.insert(0);
        p2.insert(-1);
        p2.display();
        p1.mult(p2).display();
    }


}