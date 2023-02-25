// package com.gradescope.assignment1;

// import com.gradescope.assignment1.AbstractMethodOverloading;
import java.lang.Math;

public class MethodOverloading
// extends AbstractMethodOverloading
{

    public double calculate(int a) {
        return a * a;
    }

    public double calculate(int a, int b) {
        return a * b;
    }

    public double calculate(int a, int b, int c) {
        if (a + b > c && a + c > b && b + c > a) {
            double s = (a + b + c) / 2;
            return Math.sqrt(s * (s - a) * (s - b) * (s - c));
        }

        return 0;
    }

}
