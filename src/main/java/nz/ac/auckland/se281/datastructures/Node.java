package nz.ac.auckland.se281.datastructures;

/**
 * This class represents a node in a linked list or any other data structure that requires a
 * node-like structure. It stores a value of type T and a reference to the next node in the structure.
 *
 * @param <T> the type of value stored in the node
 */
public class Node<T> {

  // Initializing the data and next node

  private T data;
  private Node<T> next;

  /**
   * Constructs a new Node with the given data.
   *
   * @param data the data to be stored in the node
   */
  public Node(T data) {
    this.data = data;
    this.next = null;
  }

  /**
   * Retrieves the data stored in this node.
   *
   * @return the data stored in the node
   */
  public T getData() {
    return data;
  }

  /**
   * Sets the data to be stored in this node.
   *
   * @param data the data to be set
   */
  public void setData(T data) {
    this.data = data;
  }

  /**
   * Retrieves the next node in the linked list.
   *
   * @return the next node
   */
  public Node<T> getNext() {
    return next;
  }

  /**
   * Sets the next node in the linked list.
   *
   * @param next the next node to be set
   */
  public void setNext(Node<T> next) {
    this.next = next;
  }

  /**
   * Returns a string representation of the node.
   *
   * @return a string representation of the node's data
   */
  @Override
  public String toString() {
    return data.toString();
  }
}
