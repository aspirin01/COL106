import java.util.*;

public class Bakery {
    static int solve(ArrayList<Integer> cakes){
        // TO be completed by students
        int answer = 0;
        int POS_INF = Integer.MAX_VALUE;

        SkipList bakerySL = new SkipList();
        for(int i=0;i<cakes.size();i++){
            if(bakerySL.upperBound(cakes.get(i))==POS_INF){
                bakerySL.insert(cakes.get(i));
                answer++;
            }
            else{
                bakerySL.delete(bakerySL.upperBound(cakes.get(i)));
                bakerySL.insert(cakes.get(i));
            }

        }
        return answer;
    }

}
