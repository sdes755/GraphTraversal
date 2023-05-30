package nz.ac.auckland.se281.datastructures;

import java.util.ArrayList;
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
  // create a new set of type T for the roots
  private Set<T> roots = new HashSet<T>();
  private ArrayList<T> source = new ArrayList<T>();
  private ArrayList<T> destination = new ArrayList<T>();

  public Graph(Set<T> verticies, Set<Edge<T>> edges) {
    this.verticies = verticies;
    this.edges = edges;
    System.out.println("verticies: " + verticies);
    // list out the edges
    for (Edge<T> edge : edges) {
      System.out.println("Source: " + edge.getSource());
      System.out.println("Destination: " + edge.getDestination());
    }
  }

  public Set<T> getRoots() {
    // TODO: Task 1.
    for (Edge<T> edge : edges) {
      source.add(edge.getSource());
      destination.add(edge.getDestination());
    }
    System.out.println(source);
    System.out.println(destination);
    for (T vertex : verticies) {
      if (!destination.contains(vertex)) {
        roots.add(vertex);
        // System.out.println(roots);
      }
    }
    // if (roots.isEmpty()) {
    //   throw new UnsupportedOperationException();
    // } else {
    return roots;
    // }
  }

  public boolean isReflexive() {
    // TODO: Task 1.
    throw new UnsupportedOperationException();
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
}
