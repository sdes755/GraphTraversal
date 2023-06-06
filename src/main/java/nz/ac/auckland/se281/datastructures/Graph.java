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
  // intialise vertices and edges as well as checks for symmetry
  private Set<T> verticies;
  private Set<Edge<T>> edges;
  private T check;
  private T symmetric;

  // Creating the constructor
  public Graph(Set<T> verticies, Set<Edge<T>> edges) {
    this.verticies = verticies;
    this.edges = edges;
  }

  // This method gets the roots of the graphs
  public Set<T> getRoots() {
    // Getting the sources and destination and initialising the sets and arraylists
    ArrayList<T> source = getSource();
    ArrayList<T> destination = getDestination();
    Set<T> equivalenceClass = new HashSet<>();
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
      equivalenceClass = getEquivalenceClass(vertex);
      for (T equivalence : equivalenceClass) {
        roots.add(equivalence);
        break;
      }
    }

    return roots;
  }

  // isReflexive checks the reflexivity of the graph
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

  // Checking if the graph is symmetric using the helper method.
  public boolean isSymmetric() {
    Set<T> reflexive = new HashSet<T>();
    reflexive = getSymmetric();
    // If the symmetric is equal to the vertices, then it is symmetric
    return reflexive.equals(verticies);
  }

  // isTransitive checks if the graph is transitive
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

  // Checking if the graph is antisymmetric using the helper nethod
  public boolean isAntiSymmetric() {
    Set<T> reflexive = new HashSet<T>();
    reflexive = getSymmetric();
    // If the graph has no symmetric values, then it is antisymmetric
    if (reflexive.size() == 0) {
      return true;
    } else {
      return false;
    }
  }

  // Checking the equivalence of the graph
  public boolean isEquivalence() {
    if (isReflexive() && isSymmetric() && isTransitive()) {
      return true;
    } else {
      return false;
    }
  }

  // getEquivalenceClass get the equivalnce class of the graph
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

  // Runs an iterative breadth first search on the graph
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

  // Runs a iterative depth first search on the graph
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

  // Recursive implementation of the breadth first search
  public List<T> recursiveBreadthFirstSearch() {
    Set<T> roots = getRoots();
    ArrayList<T> source = getSource();
    ArrayList<T> destination = getDestination();
    List<T> bfsTraversal = new ArrayList<T>();
    ArrayList<T> visited = new ArrayList<T>();
    QueueStructure<T> queue = new QueueStructure<T>();

    for (T root : roots) {
      recursiveBreadthFirstSearchHelper(root, source, destination, queue, visited, bfsTraversal);
    }

    return bfsTraversal;
  }

  // Recursive implementation of the depth first search
  public List<T> recursiveDepthFirstSearch() {
    Set<T> roots = getRoots();
    List<T> traversalResult = new ArrayList<>();
    ArrayList<T> source = getSource();
    ArrayList<T> destination = getDestination();
    ArrayList<T> visited = new ArrayList<>();
    StackStructure<T> stack = new StackStructure<T>();

    for (T root : roots) {
      recursiveDepthFirstSearchHelper(root, traversalResult, visited, source, destination, stack);
    }

    return traversalResult;
  }

  // helper methods to get Source
  public ArrayList<T> getSource() {
    ArrayList<T> source = new ArrayList<T>();
    for (Edge<T> edge : edges) {
      source.add(edge.getSource());
    }
    return source;
  }

  // helper methods to get Destination
  public ArrayList<T> getDestination() {
    ArrayList<T> destination = new ArrayList<T>();
    for (Edge<T> edge : edges) {
      destination.add(edge.getDestination());
    }
    return destination;
  }

  // Online suggested method to help order the roots and classes which need to be numeric properly.
  class NumericalComparator implements Comparator<T> {
    @Override
    public int compare(T obj1, T obj2) {
      // Assuming T is a numeric type, you can compare them using their numerical values
      // Adjust this logic according to the actual type of T
      return Double.compare(
          Double.parseDouble(obj1.toString()), Double.parseDouble(obj2.toString()));
    }
  }

  // Helper method for the recurisve breadth first search
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

  // Helper method for the recursive depth first search
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

  // helper method for the symmetry methods.
  public Set<T> getSymmetric() {
    // Initializing variables
    ArrayList<T> destination = getDestination();
    ArrayList<T> source = getSource();
    Set<T> reflexive = new HashSet<T>();
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
