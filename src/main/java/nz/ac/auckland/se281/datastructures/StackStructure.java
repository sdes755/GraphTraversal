package nz.ac.auckland.se281.datastructures;

public class StackStructure<T> {

  // Initialising the top and size

  private Node<T> top;
  private int size;

  // Creating the constructor

  public StackStructure() {
    top = null;
    size = 0;
  }

  // Creating the push method to push element to the top of the stack

  public void push(T data) {
    Node<T> newNode = new Node<T>(data);
    newNode.setNext(top);
    top = newNode;
    size++;
  }

  // Creating the pop method

  public T pop() {
    if (isEmpty()) {
      throw new IllegalStateException("Stack is empty");
    }
    // Removes the first value of the stack and size decreases

    T poppedData = top.getData();
    top = top.getNext();
    size--;
    return poppedData;
  }

  // Returns the top of the stack

  public T peek() {
    if (isEmpty()) {
      throw new IllegalStateException("Stack is empty");
    }

    return top.getData();
  }

  // Returns the size of the stack

  public int size() {
    return size;
  }

  // Checks if the stack is empty

  public boolean isEmpty() {
    return size == 0;
  }

  // Overide method for the toString representation of the stack

  @Override
  public String toString() {

    // Creating a string builder to append the elements of the stack

    StringBuilder sb = new StringBuilder();
    Node<T> current = top;

    // Creating the string to start of with square brackets

    sb.append("[");
    while (current != null) {

      // If the next element is null then it will not append a comma

      if (current.getNext() == null) {
        sb.append(current.getData());
      } else {

        // If the next element is not null then it will append a comma

        sb.append(current.getData()).append(", ");
      }

      // Iterates through the stack

      current = current.getNext();
    }

    // Appending the end of the string with square brackets

    sb.append("]");
    return sb.toString();
  }
}
