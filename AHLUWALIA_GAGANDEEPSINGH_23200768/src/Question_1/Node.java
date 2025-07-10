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
 * @param <E>
 */
public class Node<E extends Comparable> {

    public E data;
    public Node<E> next;

    //default constructor
    public Node() {
        this.data = null;
        this.next = null;
    }

    //constructor with a data parameter
    public Node(E data) {
        this.data = data;
        this.next = null;
    }

   
    // compares this node's data to another node's for equality.
    public boolean equals(Node node) {

        return this.data.equals(node.data);
    }

    // compares this node's data to another's based on natural ordering.
    public int compareTo(Node node) {
        return this.data.compareTo(node.data);
    }

}
