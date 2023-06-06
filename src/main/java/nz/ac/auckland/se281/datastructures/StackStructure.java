package nz.ac.auckland.se281.datastructures;

/**
 * This class represents a stack data structure that follows the Last-In-First-Out (LIFO) principle.
 * It allows elements of type T to be added and removed from the top of the stack.
 *
 * @param <T> the type of elements stored in the stack
 */
public class StackStructure<T> {

  // Initializing the top and size

  private Node<T> top;
  private int size;

  /** Constructs an empty stack. */
  public StackStructure() {
    top = null;
    size = 0;
  }

  /**
   * Pushes an element onto the top of the stack.
   *
   * @param data the data to be pushed onto the stack
   */
  public void push(T data) {
    Node<T> newNode = new Node<T>(data);
    newNode.setNext(top);
    top = newNode;
    size++;
  }

  /**
   * Removes and returns the element at the top of the stack.
   *
   * @return the element at the top of the stack
   * @throws IllegalStateException if the stack is empty
   */
  public T pop() {
    if (isEmpty()) {
      throw new IllegalStateException("Stack is empty");
    }
    T poppedData = top.getData();
    top = top.getNext();
    size--;
    return poppedData;
  }

  /**
   * Returns the element at the top of the stack without removing it.
   *
   * @return the element at the top of the stack
   * @throws IllegalStateException if the stack is empty
   */
  public T peek() {
    if (isEmpty()) {
      throw new IllegalStateException("Stack is empty");
    }
    return top.getData();
  }

  /**
   * Returns the number of elements in the stack.
   *
   * @return the number of elements in the stack
   */
  public int size() {
    return size;
  }

  /**
   * Checks whether the stack is empty.
   *
   * @return true if the stack is empty, false otherwise
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * Returns a string representation of the stack.
   *
   * @return a string representation of the stack
   */
  @Override
  public String toString() {
    // Creating a string builder
    StringBuilder sb = new StringBuilder();
    Node<T> current = top;
    // Appending the data to the string builder starting off with a square bracket
    sb.append("[");
    while (current != null) {

      // If the next node is null, do not append a comma

      if (current.getNext() == null) {
        sb.append(current.getData());
      } else {

        // If the next node is not null, append a comma

        sb.append(current.getData()).append(", ");
      }

      // Moving to the next node
      current = current.getNext();
    }

    // Appending the closing square bracket
    sb.append("]");
    return sb.toString();
  }
}
