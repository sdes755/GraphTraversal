package nz.ac.auckland.se281.datastructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * A graph that is composed of a set of verticies and edges.
 *
 * <p>You must NOT change the signature of the existing methods or constructor of this class.
 *
 * @param <T> The type of each vertex, that have a total ordering.
 */
public class Graph<T extends Comparable<T>> {

  /**
   * Initializes the Graph with the given set of vertices and edges.
   *
   * @param vertices the set of vertices in the graph
   * @param edges the set of edges in the graph
   */
  private Set<Edge<T>> edges;

  private Set<T> verticies;

  public Graph(Set<T> verticies, Set<Edge<T>> edges) {
    this.verticies = verticies;
    this.edges = edges;
  }

  /**
   * Returns a set of root vertices in the graph.
   *
   * @return the set of root vertices
   */
  public Set<T> getRoots() {
    // Getting the sources and destination and initialising the sets and arraylists

    ArrayList<T> source = getSource();
    ArrayList<T> destination = getDestination();
    Set<T> roots = new TreeSet<>(new NumericalComparator());

    // Running  a for loop to check for self loops, and if it is a self loop, add it to the roots.
    // But it must only have a self loop, no out going connections.

    for (int i = 0; i < source.size(); i++) {
      T src = source.get(i);
      T dest = destination.get(i);
      if (src == dest && Collections.frequency(source, src) == 1) {
        roots.add(src);
      }
    }

    // Checking if it is a source, but not a destination

    for (T vertex : verticies) {
      if (!destination.contains(vertex) && source.contains(vertex)) {
        roots.add(vertex);
      }

      // Getting the smallest value in the equivalance class

      Set<T> equivalenceClass = getEquivalenceClass(vertex);
      for (T equivalence : equivalenceClass) {
        roots.add(equivalence);
        break;
      }
    }

    return roots;
  }

  /**
   * Checks if the graph is reflexive.
   *
   * @return true if the graph is reflexive, false otherwise
   */
  public boolean isReflexive() {

    // Getting sources and destination and initialising

    ArrayList<T> destination = getDestination();
    ArrayList<T> source = getSource();
    Set<T> selfloops = new HashSet<T>();

    // Checking if the graph is reflexive and for self loops

    for (int i = 0; i < source.size(); i++) {
      if (source.get(i) == destination.get(i)) {
        selfloops.add(source.get(i));
      }
    }

    // If the selfoops are equal to the vertices, then it is reflexive

    if (selfloops.equals(verticies)) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Checks if the graph is symmetric.
   *
   * @return true if the graph is symmetric, false otherwise
   */
  public boolean isSymmetric() {
    Set<T> reflexive = getSymmetric();

    // If the symmetric is equal to the vertices, then it is symmetric

    return reflexive.equals(verticies);
  }

  /**
   * Checks if the graph is transitive.
   *
   * @return true if the graph is transitive, false otherwise
   */
  public boolean isTransitive() {

    // Getting the sources and destinations and intialising.

    ArrayList<T> destination = getDestination();
    ArrayList<T> source = getSource();
    Set<T> transitive = new HashSet<T>();

    // check if the graph is transitive. So if the source is equal to a destination and that
    // destination is a source to another destination,
    // if the original source is connected to it, then it is transitive.

    for (int i = 0; i < source.size(); i++) {
      if (source.get(i) != destination.get(i)) {
        for (int j = 0; j < source.size(); j++) {
          if (destination.get(i) == source.get(j)
              && source.get(j) != destination.get(j)
              && destination.get(j) != source.get(i)) {
            for (int k = 0; k < source.size(); k++) {
              if (destination.get(j) == destination.get(k) && source.get(i) == source.get(k)) {
                transitive.add(source.get(i));
              }
            }
          }
        }
      }
    }

    // If the graph is not transitive, then return false

    if (transitive.size() == 0) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * Checks if the graph is antisymmetric.
   *
   * @return true if the graph is antisymmetric, false otherwise
   */
  public boolean isAntiSymmetric() {
    Set<T> reflexive = getSymmetric();

    // If the graph has no symmetric values, then it is antisymmetric

    if (reflexive.size() == 0) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Checks if the graph is an equivalence relation.
   *
   * @return true if the graph is an equivalence relation, false otherwise
   */
  public boolean isEquivalence() {
    if (isReflexive() && isSymmetric() && isTransitive()) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Returns the equivalence class of a given vertex.
   *
   * @param vertex the vertex to find the equivalence class for
   * @return the set of vertices in the equivalence class
   */
  public Set<T> getEquivalenceClass(T vertex) {

    // Initialising the sets and arraylists

    Set<T> equivalenceClass = new TreeSet<>(new NumericalComparator());
    ArrayList<T> source = getSource();
    ArrayList<T> destination = getDestination();

    // First check is to make sure the graph is equivalent.

    if (!isEquivalence()) {
      return equivalenceClass;
    }

    // Next we add all the connections to the inputted vertex.

    for (int i = 0; i < source.size(); i++) {
      if (vertex.equals(source.get(i))) {
        equivalenceClass.add(destination.get(i));
      }
    }
    return equivalenceClass;
  }

  /**
   * Performs an iterative breadth-first search traversal of the graph.
   *
   * @return the list of vertices in the traversal order
   */
  public List<T> iterativeBreadthFirstSearch() {

    // Initialising the sets and arraylists

    Set<T> roots = getRoots();

    ArrayList<T> source = getSource();
    ArrayList<T> destination = getDestination();
    List<T> traversal = new ArrayList<T>();
    ArrayList<T> visited = new ArrayList<T>();
    QueueStructure<T> queue = new QueueStructure<T>();

    // Runnign through each root and adding it to the queue

    for (T root : roots) {
      for (int i = 0; i < source.size(); i++) {
        if (source.get(i).equals(root)) {
          queue.enqueue(source.get(i));
          break;
        }
      }

      // Ensuring the traversal runs till the queue is empty, so it traverses each vertex

      while (!queue.isEmpty()) {
        T vertex = queue.peek();
        queue.dequeue();

        // Once a vertex is visited it is added to the list

        if (!visited.contains(vertex)) {
          visited.add(vertex);
          traversal.add(vertex);
        }

        // Checking if it is the same vertex as the source. If it is, then it is added to the queue

        for (int i = 0; i < source.size(); i++) {
          if (source.get(i).hashCode() == vertex.hashCode()) {
            if (source.get(i).equals(vertex)) {
              if (!visited.contains(destination.get(i))) {
                queue.enqueue(destination.get(i));
              }
            }
          }
        }
      }
    }
    return traversal;
  }

  /**
   * Performs an iterative depth-first search traversal of the graph.
   *
   * @return the list of vertices in the traversal order
   */
  public List<T> iterativeDepthFirstSearch() {

    // Initialising the sets and arraylists

    Set<T> roots = getRoots();
    List<T> traversalResult = new ArrayList<>();
    StackStructure<T> stack = new StackStructure<T>();
    ArrayList<T> source = getSource();
    ArrayList<T> destination = getDestination();
    ArrayList<T> visited = new ArrayList<>();

    // Running through each root and adding it to the stack

    for (T root : roots) {
      stack.push(root);

      // Ensuring the traversal runs till the stack is empty, so it traverses each vertex

      while (!stack.isEmpty()) {
        T vertex = stack.peek();

        // Once a vertex is visited it is added to the list

        if (!visited.contains(vertex)) {
          visited.add(vertex);
          traversalResult.add(vertex);
        }

        // Checking if all the neighbours of the current vertex is explored

        boolean allNeighborsVisited = true;
        List<T> neighbors = new ArrayList<>();
        for (int i = 0; i < source.size(); i++) {
          if (source.get(i).equals(vertex) && !visited.contains(destination.get(i))) {
            neighbors.add(destination.get(i));
          }
        }

        for (T neighbor : neighbors) {
          if (!visited.contains(neighbor)) {
            stack.push(neighbor);
            allNeighborsVisited = false;
            break;
          }
        }

        // If explored, then we pop the stack

        if (allNeighborsVisited) {
          stack.pop();
        }
      }
    }

    return traversalResult;
  }

  /**
   * Performs a recursive breadth-first search traversal of the graph.
   *
   * @return the list of vertices in the traversal order
   */
  public List<T> recursiveBreadthFirstSearch() {
    // Initialising the sets and arraylists

    Set<T> roots = getRoots();
    ArrayList<T> source = getSource();
    ArrayList<T> destination = getDestination();
    List<T> bfsTraversal = new ArrayList<T>();
    ArrayList<T> visited = new ArrayList<T>();

    // Initialising the queue using our implementation of queue

    QueueStructure<T> queue = new QueueStructure<T>();

    // Running through each root and for each root the helper method is called

    for (T root : roots) {
      recursiveBreadthFirstSearchHelper(root, source, destination, queue, visited, bfsTraversal);
    }

    return bfsTraversal;
  }

  /**
   * Performs a recursive depth-first search traversal of the graph.
   *
   * @return the list of vertices in the traversal order
   */
  public List<T> recursiveDepthFirstSearch() {

    // Initialising the sets and arraylists

    Set<T> roots = getRoots();
    List<T> traversalResult = new ArrayList<>();
    ArrayList<T> source = getSource();
    ArrayList<T> destination = getDestination();
    ArrayList<T> visited = new ArrayList<>();

    // Initialising the stack using our implementation of stack

    StackStructure<T> stack = new StackStructure<T>();

    // Running through each root and for each root the helper method is called

    for (T root : roots) {
      recursiveDepthFirstSearchHelper(root, traversalResult, visited, source, destination, stack);
    }

    return traversalResult;
  }

  /**
   * Retrieves the source vertices of the edges in the graph.
   *
   * @return an ArrayList containing the source vertices
   */
  public ArrayList<T> getSource() {
    ArrayList<T> source = new ArrayList<T>();
    for (Edge<T> edge : edges) {
      source.add(edge.getSource());
    }
    return source;
  }

  /**
   * Retrieves the destination vertices of the edges in the graph.
   *
   * @return an ArrayList containing the destination vertices
   */
  public ArrayList<T> getDestination() {
    ArrayList<T> destination = new ArrayList<T>();
    for (Edge<T> edge : edges) {
      destination.add(edge.getDestination());
    }
    return destination;
  }

  /**
   * A comparator class for comparing numerical values of elements in the graph. Suggested by online
   * resources
   */
  class NumericalComparator implements Comparator<T> {

    /**
     * Compares two numerical values.
     *
     * @param obj1 the first numerical value
     * @param obj2 the second numerical value
     * @return the comparison result (-1 if obj1 < obj2, 0 if obj1 == obj2, 1 if obj1 > obj2)
     */
    @Override
    public int compare(T obj1, T obj2) {

      // Assuming T is a numeric type, you can compare them using their numerical values
      // Adjust this logic according to the actual type of T

      return Double.compare(
          Double.parseDouble(obj1.toString()), Double.parseDouble(obj2.toString()));
    }
  }

  /**
   * Helper method for performing recursive breadth-first search (BFS) traversal of the graph.
   *
   * @param vertex the current vertex being visited
   * @param source the list of source vertices of the edges
   * @param destination the list of destination vertices of the edges
   * @param queue the queue data structure for BFS traversal
   * @param visited the list of visited vertices
   * @param bfsTraversal the list for storing the BFS traversal result
   */
  public void recursiveBreadthFirstSearchHelper(
      T vertex,
      ArrayList<T> source,
      ArrayList<T> destination,
      QueueStructure<T> queue,
      ArrayList<T> visited,
      List<T> bfsTraversal) {

    // Once a vertex is visited it is added to the list

    if (!visited.contains(vertex)) {
      visited.add(vertex);
      bfsTraversal.add(vertex);
    }

    // Checking if it is the same vertex as the source. If it is, then it is added to the queue

    for (int i = 0; i < source.size(); i++) {
      if (source.get(i).hashCode() == vertex.hashCode() && source.get(i).equals(vertex)) {
        T destinationVertex = destination.get(i);
        if (!visited.contains(destinationVertex)) {
          queue.enqueue(destinationVertex);
        }
      }
    }

    // Checking if the queue is empty, if not then we dequeue the next vertex and run the method
    // again recursively

    if (!queue.isEmpty()) {
      T nextVertex = queue.peek();
      queue.dequeue();
      recursiveBreadthFirstSearchHelper(
          nextVertex, source, destination, queue, visited, bfsTraversal);
    }
  }

  /**
   * Helper method for performing recursive depth-first search (DFS) traversal of the graph.
   *
   * @param vertex the current vertex being visited
   * @param traversalResult the list for storing the DFS traversal result
   * @param visited the list of visited vertices
   * @param source the list of source vertices of the edges
   * @param destination the list of destination vertices of the edges
   * @param stack the stack data structure for DFS traversal
   */
  public void recursiveDepthFirstSearchHelper(
      T vertex,
      List<T> traversalResult,
      List<T> visited,
      ArrayList<T> source,
      ArrayList<T> destination,
      StackStructure<T> stack) {

    // Once a vertex is visited it is added to the list

    if (!visited.contains(vertex)) {
      visited.add(vertex);
      traversalResult.add(vertex);
    }

    // Checking if all the neighbours of the current vertex is explored

    List<T> neighbors = new ArrayList<>();
    for (int i = 0; i < source.size(); i++) {
      if (source.get(i).equals(vertex) && !visited.contains(destination.get(i))) {
        neighbors.add(destination.get(i));
      }
    }

    // If not, then we push the vertex to the stack

    for (T neighbor : neighbors) {
      if (!visited.contains(neighbor)) {
        stack.push(neighbor);
        recursiveDepthFirstSearchHelper(
            neighbor, traversalResult, visited, source, destination, stack);
      }
    }

    // If explored, then we pop the stack and run the method again recursively

    if (!stack.isEmpty()) {
      stack.pop();
      if (!stack.isEmpty()) {
        T nextVertex = stack.peek();
        recursiveDepthFirstSearchHelper(
            nextVertex, traversalResult, visited, source, destination, stack);
      }
    }
  }

  /**
   * Retrieves a set of vertices that have symmetric edges in the graph.
   *
   * @return a Set containing the symmetric vertices
   */
  public Set<T> getSymmetric() {
    // Initializing variables
    ArrayList<T> destination = getDestination();
    ArrayList<T> source = getSource();
    Set<T> reflexive = new HashSet<T>();
    T check;
    T symmetric;
    // Running a for loop to check if it has a symmetric edge

    for (int j = 0; j < source.size(); j++) {
      for (int i = 0; i < source.size(); i++) {
        check = source.get(j);
        symmetric = destination.get(j);
        if (source.get(i) == symmetric && destination.get(i) == check) {
          reflexive.add(check);
        }
      }
    }
    return reflexive;
  }
}
