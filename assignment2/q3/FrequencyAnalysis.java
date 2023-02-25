import java.util.ArrayList;

import Includes.*;




public class FrequencyAnalysis {
    //sizes of hash-tables are updated
    static final int[] hashTableSizes = {173, 6733, 100003};
    COL106Dictionary<String, Integer> dict1 = new COL106Dictionary<String, Integer>(hashTableSizes[0]);
    COL106Dictionary<String, Integer> dict2 = new COL106Dictionary<String, Integer>(hashTableSizes[1]);
    COL106Dictionary<String, Integer> dict3 = new COL106Dictionary<String, Integer>(hashTableSizes[2]);
    void fillDictionaries(String inputString) throws NullKeyException, KeyAlreadyExistException, KeyNotFoundException {
        /*
         * To be filled in by the student
         */
        // inputString = inputString.trim().replaceAll("[-+.^:,()?']","").replaceAll("\\s+", " ").toLowerCase(); // removing all the punctuations
        // if(inputString == ""){
        //     // throw new NullKeyException();
        //     return;
        // }
        // filling the dictionaries with the words and frequency = 1 initially and then updating the frequencies in distinctWordsFrequencies
        int i=0;
        inputString=inputString.toLowerCase();
        String temp="";
        System.out.println("Starting: "+ inputString.length());
        while(i<inputString.length()){
            if(inputString.charAt(i) >= 'a' && inputString.charAt(i) <= 'z'){
                // System.out.println(temp);
                temp+=inputString.charAt(i);
            }
            else{
                if((inputString.charAt(i) == ' ')&& temp!=""){
                    try{
                        dict1.insert(temp, 1);
                        dict2.insert(temp, 1);
                        dict3.insert(temp, 1);
                    }
                    catch(KeyAlreadyExistException e){
                        // System.out.println(temp);
                        try{
                            dict1.update(temp, dict1.get(temp) + 1);
                            dict2.update(temp, dict2.get(temp) + 1);
                            dict3.update(temp, dict3.get(temp) + 1);
                        }
                        catch(KeyNotFoundException e1){
                            System.out.println("Key not found");
                        }
                    }
                // }
                    temp="";
                }
            }
            i++;
        }
        if(i==inputString.length()&& temp!=""){
            try{
                dict1.insert(temp, 1);
                dict2.insert(temp, 1);
                dict3.insert(temp, 1);
            }
            catch(KeyAlreadyExistException e){
                // System.out.println(temp);
                dict1.update(temp, dict1.get(temp) + 1);
                dict2.update(temp, dict2.get(temp) + 1);
                dict3.update(temp, dict3.get(temp) + 1);
            }
        }
        System.out.println("Inserted successfully");
    }
    // void display(){
    //     dict1.displayHashTable();
    //     System.out.println();
    //     dict2.displayHashTable();
    //     System.out.println();
    //     dict3.displayHashTable();
    // }


    long[] profile() {
        /*
         * To be filled in by the student
         */
        return new long[4];
    }

    int[] noOfCollisions() {
        /*
         * To be filled in by the student
         */
        int[] collisions = {dict1.getCollisionSize(),dict2.getCollisionSize(),dict3.getCollisionSize()};

        // int[] collisions = {0,0,0};
        return collisions;
    }

    String[] hashTableHexaDecimalSignature() {
        /*
         * To be filled in by the student
        */
        String[] ans = new String[3];
        ans[0]="";
        for(int i=0;i<hashTableSizes[0];i++){
            if(dict1.hashTable[i]==null){
                ans[0] += "0";
            }
            else{
                ans[0] += "1";
            }
        }
        ans[1]="";

        for(int i=0;i<hashTableSizes[1];i++){
            if(dict2.hashTable[i]==null){
                ans[1] += "0";
            }
            else{
                ans[1] += "1";
            }
        }
        ans[2]="";

        for(int i=0;i<hashTableSizes[2];i++){
            if(dict3.hashTable[i]==null){
                ans[2] += "0";
            }
            else{
                ans[2] += "1";
            }
        }
        for(int i=0;i<3;i++){
            // System.out.println(ans[i]);
            ans[i] = binToHex(ans[i]);
        }
        return ans;
    }
    String binToHex(String b){
        String ans = "";
        if(b.length()%4==1){
            b = "000"+b;
        }
        else if(b.length()%4==2){
            b = "00"+b;
        }
        else if(b.length()%4==3){
            b = "0"+b;
        }

        for(int i=0;i<b.length();i+=4){
            String temp = b.substring(i,i+4);
            switch (temp) {
                case "0000":
                    ans += "0";
                    break;
                case "0001":
                    ans += "1";
                    break;
                case "0010":
                    ans += "2";
                    break;
                case "0011":
                    ans += "3";
                    break;
                case "0100":
                    ans += "4";
                    break;
                case "0101":
                    ans += "5";
                    break;
                case "0110":
                    ans += "6";
                    break;
                case "0111":
                    ans += "7";
                    break;
                case "1000":
                    ans += "8";
                    break;
                case "1001":
                    ans += "9";
                    break;
                case "1010":
                    ans += "A";
                    break;
                case "1011":
                    ans += "B";
                    break;
                case "1100":
                    ans += "C";
                    break;
                case "1101":
                    ans += "D";
                    break;
                case "1110":
                    ans += "E";
                    break;
                case "1111":
                    ans += "F";
                    break;
                default:
                    break;
            }
        }
        return ans;

    }

    String[] distinctWords() {
        /*
         * To be filled in by the student
         */
        // System.out.println(dict1.keys(String.class).length);
        return dict1.keys(String.class);
    }

    Integer[] distinctWordsFrequencies() {
        /*
         * To be filled in by the student
         */


        return dict1.values(Integer.class);
    }

    // public static void main(String[] args){
    //     FrequencyAnalysis fa = new FrequencyAnalysis();
    //     String s="""
    //     The Indian Institute of Technology Delhi      (IIT Delhi) is a public institute of technology located in New Delhi, India. It is one of the twenty-three Indian Institutes of Technology created to be Centres of Excellence for India’s training, research and development in science, engineering and technology.""";
    //     try{
    //         fa.fillDictionaries(s);
    //     }
    //     catch(NullKeyException | KeyAlreadyExistException | KeyNotFoundException e){
    //     }
    //     fa.display();
    //     Integer[]ans = fa.distinctWordsFrequencies();
    //     String[] ans1 = fa.distinctWords();
    //     for(int i=0;i<ans.length;i++){
    //         System.out.println(ans[i]+" "+ans1[i]);
    //     }
    //     System.out.println(fa.binToHex("101010"));

    //     String[] ans2 = fa.hashTableHexaDecimalSignature();
    //     for(int i=0;i<ans2.length;i++){
    //         // System.out.println(i);
    //         // System.out.println(ans2[i]);
    //     }
    // }


}
