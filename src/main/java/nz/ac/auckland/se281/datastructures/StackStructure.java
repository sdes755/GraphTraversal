package nz.ac.auckland.se281.datastructures;

public class StackStructure<T> {
  private Node<T> top;
  private int size;

  public StackStructure() {
    top = null;
    size = 0;
  }

  public void push(T data) {
    Node<T> newNode = new Node<T>(data);
    newNode.setNext(top);
    top = newNode;
    size++;
  }

  public T pop() {
    if (isEmpty()) {
      throw new IllegalStateException("Stack is empty");
    }

    T poppedData = top.getData();
    top = top.getNext();
    size--;
    return poppedData;
  }

  public T peek() {
    if (isEmpty()) {
      throw new IllegalStateException("Stack is empty");
    }

    return top.getData();
  }

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    Node<T> current = top;
    sb.append("[");
    while (current != null) {
      if (current.getNext() == null) {
        sb.append(current.getData());
      } else {
        sb.append(current.getData()).append(", ");
      }
      current = current.getNext();
    }
    sb.append("]");
    return sb.toString();
  }
}
