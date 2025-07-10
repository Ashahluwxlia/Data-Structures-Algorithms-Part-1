//Name - Gagandeep Singh Ahluwalia
//ID - 23200768

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Question_1;

import java.util.NoSuchElementException;

/**
 *
 * @author xhu
 */
public class Stack<E extends Comparable> {

    // internal storage of stack elements
    LinkedList<E> stack = new LinkedList();

    //pushes (add) an element onto the top of the stack.
    public void push(E data) {
        stack.addHead(data);
    }

    //removes and returns the element at the top of the stack.
    public E pop() {
        if (stack.getSize() == 0) {
            throw new NoSuchElementException("Cannot pop from an empty stack.");
        }
        return stack.removeFromHead().data;
    }

    //prints all elements in the stack from top to bottom.
    public void printStack() {
        stack.printLinkedList();
    }

    //defines the size of the stack
    public int getSize() {
        return stack.getSize();
    }

}
