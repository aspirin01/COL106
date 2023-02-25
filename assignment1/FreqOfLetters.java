// package com.gradescope.assignment1;

// import com.gradescope.assignment1.AbstractFreqOfLetters;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FreqOfLetters
// extends AbstractFreqOfLetters
{
    public Integer[] count_letters(String fname) throws FileNotFoundException, IOException {
        Integer[] result = new Integer[26];
        for(int i=0;i<result.length;i++){
            result[i]=0;
        }
        FileReader f = new FileReader(fname);
        BufferedReader b=new BufferedReader(f);
        int i;
        while((i=b.read())!=-1){
            if(i>=97 && i<=122)result[i-97]++;
        }
        b.close();
        f.close();
        return result;
    }
}