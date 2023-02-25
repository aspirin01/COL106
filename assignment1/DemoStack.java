// package com.gradescope.assignment1;

import java.util.EmptyStackException;

// import com.gradescope.assignment1.AbstractDemoStack;

public class DemoStack
// extends AbstractDemoStack
 {
    Character[] base;
    private int strIndex = 0;
    private int N = 10;

    public DemoStack() {
        super();
        base = new Character[10];
    }

    public void push(Character i) {
        /*
         * To be filled in by the student
         * Input: Character to be inserted onto top of stack
         */
        if (strIndex == N) {
            Character[] temp = base;
            N = N * 2;
            base = new Character[N];
            for (int j = 0; j < strIndex; j++) {
                base[j] = temp[j];
            }
        }
        base[strIndex] = i;
            strIndex++;
    }

    public Character pop() throws EmptyStackException {
        if (strIndex ==0){
            throw new EmptyStackException();
        }
        /*
         * To be filled in by the student
         * Return: Character present at the top of the stack
         */
        char value = base[strIndex - 1];
        strIndex--;
        if (strIndex < N / 4 && strIndex > 32) {
            Character[] temp = base;
            N = N / 2;
            base = new Character[N];
            for (int i = 0; i < strIndex; i++) {
                base[i] = temp[i];
            }
        }
        return value;
    }

    public Boolean is_empty() {
        /*
         * To be filled in by the student
         * Return: Stack is empty or not
         */
        if (strIndex == 0) {
            return true;
        }
        return false;
    }

    public Integer size() {
        /*
         * To be filled in by the student
         * Return: Number of elements which are present in stack
         */
        return strIndex;
    }

    public Character top() throws EmptyStackException {
        if (strIndex ==0){
            throw new EmptyStackException();
        }
        /*
         * To be filled in by the student
         * Return: Character present at the top of the stack
         */
        return base[strIndex - 1];
    }

    public Character[] return_base_array() {
        /*
         * To be filled in the by the student
         * Return: Return reference to the base array storing the elements of stack
         */
        return base;
    }
}
