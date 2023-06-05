package nz.ac.auckland.se281.datastructures;

import java.util.LinkedList;

public class QueueStructure<T> {
  private LinkedList<T> elements;

  public QueueStructure() {
    elements = new LinkedList<>();
  }

  public void enqueue(T item) {
    elements.addLast(item);
  }

  public T dequeue() {
    if (isEmpty()) {
      throw new IllegalStateException("Queue is empty");
    }
    return elements.removeFirst();
  }

  public T peek() {
    if (isEmpty()) {
      throw new IllegalStateException("Queue is empty");
    }
    return elements.getFirst();
  }

  public boolean isEmpty() {
    return elements.isEmpty();
  }

  public int size() {
    return elements.size();
  }
}
