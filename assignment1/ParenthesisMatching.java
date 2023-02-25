// package com.gradescope.assignment1;

// import com.gradescope.assignment1.AbstractParenthesisMatching;
// import com.gradescope.assignment1.DemoStack;

public class ParenthesisMatching
//  extends AbstractParenthesisMatching
  {
    public Boolean match_parenthesis(String s){
        if(s=="")return true;
        DemoStack stack = new DemoStack();
        for(int i = 0 ; i< s.length(); i++){
            char c = s.charAt(i);
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else if (c == ')' || c == '}' || c == ']') {
                if (stack.is_empty()) {
                    return false;
                }
                char top = stack.pop();
                if (c == ')' && top != '(') {
                    return false;
                } else if (c == '}' && top != '{') {
                    return false;
                } else if (c == ']' && top != '[') {
                    return false;
                }
            }
        }
        if(stack.is_empty())return true;
        return false;
    }
}
