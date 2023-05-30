package nz.ac.auckland.se281.datastructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A graph that is composed of a set of verticies and edges.
 *
 * <p>You must NOT change the signature of the existing methods or constructor of this class.
 *
 * @param <T> The type of each vertex, that have a total ordering.
 */
public class Graph<T extends Comparable<T>> {
  // intialise vertices and edges
  private Set<T> verticies;
  private Set<Edge<T>> edges;
  private Set<T> roots = new HashSet<T>();
  private ArrayList<T> source = new ArrayList<T>();
  private ArrayList<T> destination = new ArrayList<T>();
  private ArrayList<T> selfloops = new ArrayList<T>();

  public Graph(Set<T> verticies, Set<Edge<T>> edges) {
    this.verticies = verticies;
    this.edges = edges;
  }

  public Set<T> getRoots() {
    // TODO: Task 1.
    source = getSource();
    destination = getDestination();
    for (int i = 0; i < source.size(); i++) {
      if (source.get(i) == destination.get(i)
          && Collections.frequency(source, source.get(i)) == 1) {
        roots.add(source.get(i));
      }
    }
    for (T vertex : verticies) {
      if (!destination.contains(vertex)) {
        roots.add(vertex);
      }
    }
    return roots;
  }

  public boolean isReflexive() {
    // TODO: Task 1.
    source = getSource();
    destination = getDestination();
    for (int i = 0; i < source.size(); i++) {
      if (source.get(i) == destination.get(i)) {
        selfloops.add(source.get(i));
      }
    }
    if (selfloops.size() != 0) {
      return true;
    } else {
      return false;
    }
  }

  public boolean isSymmetric() {
    // TODO: Task 1.
    throw new UnsupportedOperationException();
  }

  public boolean isTransitive() {
    // TODO: Task 1.
    throw new UnsupportedOperationException();
  }

  public boolean isAntiSymmetric() {
    // TODO: Task 1.
    throw new UnsupportedOperationException();
  }

  public boolean isEquivalence() {
    // TODO: Task 1.
    throw new UnsupportedOperationException();
  }

  public Set<T> getEquivalenceClass(T vertex) {
    // TODO: Task 1.
    throw new UnsupportedOperationException();
  }

  public List<T> iterativeBreadthFirstSearch() {
    // TODO: Task 2.
    throw new UnsupportedOperationException();
  }

  public List<T> iterativeDepthFirstSearch() {
    // TODO: Task 2.
    throw new UnsupportedOperationException();
  }

  public List<T> recursiveBreadthFirstSearch() {
    // TODO: Task 3.
    throw new UnsupportedOperationException();
  }

  public List<T> recursiveDepthFirstSearch() {
    // TODO: Task 3.
    throw new UnsupportedOperationException();
  }

  public ArrayList<T> getSource() {
    for (Edge<T> edge : edges) {
      source.add(edge.getSource());
    }
    return source;
  }

  public ArrayList<T> getDestination() {
    for (Edge<T> edge : edges) {
      destination.add(edge.getDestination());
    }
    return destination;
  }
}
