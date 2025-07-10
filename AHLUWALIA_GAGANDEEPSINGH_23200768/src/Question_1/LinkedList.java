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
 * @param <E>
 */
public class LinkedList<E extends Comparable> {

    public int size = 0;
    public Node<E> head;

    public LinkedList() {
        head = null;
        size = 0;
    }

    // adds a new element at the head of the linked list.
    public void addHead(E data) {
        Node<E> newNode = new Node<>(data);
        newNode.next = head;
        head = newNode;
        size++;
    }

    public Node getHead() {
        return head;
    }

    // adds a new element at the end of the linked list.
    public void add(E data) {
        Node<E> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            Node<E> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    // checks if the linked list contains a given element.
    public boolean contains(Node<E> node) {
        if (node == null) {
            return false;
        }
        Node<E> current = head;
        while (current != null) {
            if (current.data.equals(node.data)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // generalized method for removing nodes based on their data values, if it is present.
    public void remove(Node<E> node) {
        if (node == null) {
            return;
        }

        // case 1: Removing the head node
        if (head != null && head.data.equals(node.data)) {
            head = head.next;
            size--;
            return;
        }

        // case 2: Removing a non-head node
        Node<E> current = head;
        while (current != null && current.next != null) {
            if (current.next.data.equals(node.data)) {
                current.next = current.next.next;
                size--;
                return;
            }
            current = current.next;
        }
    }

    // removes the element at the specified position in this list.
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        // Case 1: removing the head node
        if (index == 0) {
            head = head.next;
            size--;
            return;
        }

        // Case 2: removing a non-head node
        Node<E> current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        if (current.next != null) {
            current.next = current.next.next;
            size--;
        }
    }

    // specialized for removing the first node from the list.
    public Node<E> removeFromHead() {
        if (head == null) {
            throw new NoSuchElementException("List is empty.");
        }
        Node<E> removedNode = head;
        head = head.next;
        size--;
        removedNode.next = null;
        return removedNode;
    }

    // removes the last node from the linked list.
    public Node<E> removeFromTail() {
        if (head == null) {
            throw new NoSuchElementException("List is empty.");
        }
        if (head.next == null) {
            Node<E> temp = head;
            head = null;
            size = 0;
            return temp;
        }

        Node<E> current = head;
        while (current.next.next != null) {
            current = current.next;
        }
        Node<E> temp = current.next;
        current.next = null;
        size--;
        return temp;
    }

    // inserts the specified element into the correct position in a sorted list.
    public void addInOrder(E data) {
        Node<E> newNode = new Node(data);
        if (head == null || head.data.compareTo(data) >= 0) {
            newNode.next = head;
            head = newNode;
        } else {
            Node<E> current = head;
            while (current.next != null && current.next.data.compareTo(data) < 0) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
        }
        size++;
    }

    // retrieves the node at the specified index in the linked list.
    public Node getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node<E> current = head;
        for (int i = 0; current != null && i < index; i++) {
            current = current.next;
        }
        return current;
    }

    // returns the data at the specified position in this list.
    public E getData(int index) {
        Node<E> node = getNode(index);
        if (node != null) {
            return node.data;
        }
        return null;
    }

    // prints all the elements of the linked list to the console.
    public void printLinkedList() {
        Node<E> current = head;
        while (current != null) {
            System.out.print(current.data);
            if (current.next != null) {
                System.out.print(" ");
            } else {
                System.out.println();
            }
            current = current.next;
        }
    }

    // extra method to get size
    public int getSize() {
        return size;
    }

}
