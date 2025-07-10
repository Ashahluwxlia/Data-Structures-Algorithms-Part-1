//Name - Gagandeep Singh Ahluwalia
//ID - 23200768

package Question_1;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Question_1.LinkedList;
import java.util.NoSuchElementException;

/**
 *
 * @author xhu
 * @param <E>
 */
public class Queue<E extends Comparable> {

    // internal storage for the queue.
    private LinkedList<E> queue = new LinkedList();

    // adds an element to the end of the queue.
    public void enqueue(E data) {
        queue.add(data);
    }

    // removes and returns the element at the front of the queue.
    public E dequeue() {
        if (queue.getSize() == 0) {
            throw new NoSuchElementException("Cannot dequeue from an empty queue.");
        }
        return queue.removeFromHead().data;
    }

    // removes and returns the element at the front of the queue.
    public void printQueue() {
        queue.printLinkedList();
    }

    // returns the number of elements in the queue.
    public int getSize() {
        return queue.size;
    }
}
