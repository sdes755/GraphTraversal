package nz.ac.auckland.se281.datastructures;

import java.util.LinkedList;

public class QueueStructure<T> {
  // Initialising the elements

  private LinkedList<T> elements;

  // Creating the constructor

  public QueueStructure() {
    elements = new LinkedList<>();
  }

  // Creating the enqueue method adding to the end of the queue

  public void enqueue(T item) {
    elements.addLast(item);
  }

  // Creating the dequeue method removing the first element of the queue

  public T dequeue() {
    if (isEmpty()) {
      throw new IllegalStateException("Queue is empty");
    }
    return elements.removeFirst();
  }

  // returing the first element of the queue

  public T peek() {
    if (isEmpty()) {
      throw new IllegalStateException("Queue is empty");
    }
    return elements.getFirst();
  }

  // Checking if the queue is empty

  public boolean isEmpty() {
    return elements.isEmpty();
  }

  // Returning the size of the queue
  
  public int size() {
    return elements.size();
  }
}
