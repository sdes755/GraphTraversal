package nz.ac.auckland.se281.datastructures;

public class Node<T> {
  // Initialising the data and next node
  private T data;
  private Node<T> next;
  // Creating the constructor
  public Node(T data) {
    this.data = data;
    this.next = null;
  }
  // Getter for the data
  public T getData() {
    return data;
  }
  // Setter for the data
  public void setData(T data) {
    this.data = data;
  }
  // Getter for the next node
  public Node<T> getNext() {
    return next;
  }
  // Setter for the next node
  public void setNext(Node<T> next) {
    this.next = next;
  }
  // Overide method for the toString representation of the node
  @Override
  public String toString() {
    return data.toString();
  }
}
