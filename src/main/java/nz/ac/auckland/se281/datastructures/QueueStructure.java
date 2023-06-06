package nz.ac.auckland.se281.datastructures;

import java.util.LinkedList;

public class QueueStructure<T> {

  // Initializing the elements

  private LinkedList<T> elements;

  /** Constructs an empty queue. */
  public QueueStructure() {
    elements = new LinkedList<>();
  }

  /**
   * Adds an item to the end of the queue.
   *
   * @param item the item to be added to the queue
   */
  public void enqueue(T item) {
    elements.addLast(item);
  }

  /**
   * Removes and returns the first item in the queue.
   *
   * @return the first item in the queue
   * @throws IllegalStateException if the queue is empty
   */
  public T dequeue() {
    if (isEmpty()) {
      throw new IllegalStateException("Queue is empty");
    }
    return elements.removeFirst();
  }

  /**
   * Returns the first item in the queue without removing it.
   *
   * @return the first item in the queue
   * @throws IllegalStateException if the queue is empty
   */
  public T peek() {
    if (isEmpty()) {
      throw new IllegalStateException("Queue is empty");
    }
    return elements.getFirst();
  }

  /**
   * Checks whether the queue is empty.
   *
   * @return true if the queue is empty, false otherwise
   */
  public boolean isEmpty() {
    return elements.isEmpty();
  }

  /**
   * Returns the number of items in the queue.
   *
   * @return the number of items in the queue
   */
  public int size() {
    return elements.size();
  }
}
