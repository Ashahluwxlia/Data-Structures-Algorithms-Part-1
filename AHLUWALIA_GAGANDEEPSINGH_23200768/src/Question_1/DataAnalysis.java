//Name - Gagandeep Singh Ahluwalia
//ID - 23200768
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Question_1;

/**
 *
 * @author xhu
 */
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class DataAnalysis<E extends Comparable<E>> {
    // queue to hold elements in the order they are provided
    public Queue<E> queue = new LinkedList<>();
    
    // stack to hold elements in reverse order
    public Stack<E> stack = new Stack<>();
    
    // array to hold the original data
    public E[] data;

    // constructor to initialize data and populate the queue and stack
    public DataAnalysis(E[] data) {
        this.data = data;
        for (E element : data) {
            queue.add(element);
            stack.push(element);
        }
    }

    // method to check if the data is a palindrome
    public boolean isPalindrome() {
        // compare elements from the queue and stack
        while (!queue.isEmpty() && !stack.isEmpty()) {
            E queueElement = queue.poll();  // get the front element from the queue
            E stackElement = stack.pop();   // get the top element from the stack
            // if elements are not equal, it is not a palindrome
            if (!queueElement.equals(stackElement)) {
                return false;
            }
        }
        // if all elements matched, it is a palindrome
        return true;
    }
}

