package nz.ac.auckland.se281.datastructures;

/**
 * An edge in a graph that connects two verticies.
 *
 * <p>You must NOT change the signature of the constructor of this class.
 *
 * @param <T> The type of each vertex.
 */
public class Edge<T> {

  // Initializing the source and destination

  private T source;
  private T destination;

  /**
   * Constructs a new Edge with the given source and destination.
   *
   * @param source the source vertex of the edge
   * @param destination the destination vertex of the edge
   */
  public Edge(T source, T destination) {
    this.source = source;
    this.destination = destination;
  }

  /**
   * Returns the source vertex of the edge.
   *
   * @return the source vertex of the edge
   */
  public T getSource() {
    return source;
  }

  /**
   * Returns the destination vertex of the edge.
   *
   * @return the destination vertex of the edge
   */
  public T getDestination() {
    return destination;
  }

  /**
   * Computes the hash code for the edge.
   *
   * @return the computed hash code
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;

    // Creating the formula for hash code
    result = prime * result + ((source == null) ? 0 : source.hashCode());
    result = prime * result + ((destination == null) ? 0 : destination.hashCode());
    return result;
  }

  /**
   * Checks whether the specified object is equal to this edge.
   *
   * @param obj the object to compare with this edge
   * @return true if the specified object is equal to this edge, false otherwise
   */
  @Override
  public boolean equals(Object obj) {

    // Checking if it is the same instance as the object or if it is null
    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    // Casting the object to an edge
    Edge<T> other = (Edge<T>) obj;

    // Checking for equality
    if (source == null) {
      if (other.source != null) {
        return false;
      }
    } else if (!source.equals(other.source)) {
      return false;
    }
    if (destination == null) {
      if (other.destination != null) {
        return false;
      }
    } else if (!destination.equals(other.destination)) {
      return false;
    }
    return true;
  }
}
