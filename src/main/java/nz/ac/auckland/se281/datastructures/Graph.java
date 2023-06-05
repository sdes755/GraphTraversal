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
  // intialise vertices and edges
  private Set<T> verticies;
  private Set<Edge<T>> edges;
  private T check;
  private T symmetric;

  public Graph(Set<T> verticies, Set<Edge<T>> edges) {
    this.verticies = verticies;
    this.edges = edges;
  }

  public Set<T> getRoots() {
    ArrayList<T> source = getSource();
    ArrayList<T> destination = getDestination();
    Set<T> equivalenceClass = new HashSet<>();
    Set<T> roots = new TreeSet<>(new NumericalComparator());

    for (int i = 0; i < source.size(); i++) {
      T src = source.get(i);
      T dest = destination.get(i);
      if (src == dest && Collections.frequency(source, src) == 1) {
        roots.add(src);
      }
    }

    for (T vertex : verticies) {
      if (!destination.contains(vertex) && source.contains(vertex)) {
        roots.add(vertex);
      }

      equivalenceClass = getEquivalenceClass(vertex);
      for (T equivalence : equivalenceClass) {
        roots.add(equivalence);
        break;
      }
    }

    return roots;
  }

  public boolean isReflexive() {
    ArrayList<T> destination = new ArrayList<T>();
    ArrayList<T> source = new ArrayList<T>();
    Set<T> selfloops = new HashSet<T>();
    source = getSource();
    destination = getDestination();
    for (int i = 0; i < source.size(); i++) {
      if (source.get(i) == destination.get(i)) {
        selfloops.add(source.get(i));
      }
    }
    if (selfloops.equals(verticies)) {
      return true;
    } else {
      return false;
    }
  }

  public boolean isSymmetric() {
    ArrayList<T> destination = new ArrayList<T>();
    ArrayList<T> source = new ArrayList<T>();
    Set<T> reflexive = new HashSet<T>();
    source = getSource();
    destination = getDestination();
    for (int j = 0; j < source.size(); j++) {
      for (int i = 0; i < source.size(); i++) {
        check = source.get(j);
        symmetric = destination.get(j);
        if (source.get(i) == symmetric && destination.get(i) == check) {
          reflexive.add(check);
        }
      }
    }
    return reflexive.equals(verticies);
  }

  public boolean isTransitive() {
    ArrayList<T> destination = new ArrayList<T>();
    ArrayList<T> source = new ArrayList<T>();
    Set<T> transitive = new HashSet<T>();
    source = getSource();
    destination = getDestination();
    // check if the graph is transitive
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
    if (transitive.size() == 0) {
      return false;
    } else {
      return true;
    }
  }

  public boolean isAntiSymmetric() {
    ArrayList<T> destination = new ArrayList<T>();
    ArrayList<T> source = new ArrayList<T>();
    Set<T> reflexive = new HashSet<T>();
    source = getSource();
    destination = getDestination();
    for (int j = 0; j < source.size(); j++) {
      for (int i = 0; i < source.size(); i++) {
        check = source.get(j);
        symmetric = destination.get(j);
        if (source.get(i) == symmetric && destination.get(i) == check) {
          reflexive.add(check);
        }
      }
    }
    if (reflexive.size() == 0) {
      return true;
    } else {
      return false;
    }
  }

  public boolean isEquivalence() {
    if (isReflexive() && isSymmetric() && isTransitive()) {
      return true;
    } else {
      return false;
    }
  }

  public Set<T> getEquivalenceClass(T vertex) {
    Set<T> equivalenceClass = new HashSet<T>();
    ArrayList<T> source = new ArrayList<>();
    ArrayList<T> destination = new ArrayList<>();
    source = getSource();
    destination = getDestination();

    if (!isEquivalence()) {
      return equivalenceClass;
    }
    for (int i = 0; i < source.size(); i++) {
      if (vertex.equals(source.get(i))) {
        equivalenceClass.add(destination.get(i));
      }
    }
    return equivalenceClass;
  }

  public List<T> iterativeBreadthFirstSearch() {
    // TODO: Task 2.
    // throw new UnsupportedOperationException();
    Set<T> roots = getRoots();

    ArrayList<T> source = new ArrayList<T>();
    ArrayList<T> destination = new ArrayList<T>();
    List<T> bfs_traversal = new ArrayList<T>();
    ArrayList<T> visited = new ArrayList<T>();
    // create an object of the queue class
    QueueStructure<T> queue = new QueueStructure<T>();
    source = getSource();
    destination = getDestination();
    for (T root : roots) {
      for (int i = 0; i < source.size(); i++) {
        if (source.get(i).equals(root)) {
          queue.enqueue(source.get(i));
          break;
        }
      }
      while (!queue.isEmpty()) {
        T vertex = queue.peek();
        queue.dequeue();
        if (!visited.contains(vertex)) {
          visited.add(vertex);
          bfs_traversal.add(vertex);
        }
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
    return bfs_traversal;
  }

  public List<T> iterativeDepthFirstSearch() {
    Set<T> roots = getRoots();
    List<T> traversalResult = new ArrayList<>();
    StackStructure<T> stack = new StackStructure<T>();
    ArrayList<T> source = getSource();
    ArrayList<T> destination = getDestination();
    ArrayList<T> visited = new ArrayList<>();

    for (T root : roots) {
      stack.push(root);

      while (!stack.isEmpty()) {
        T vertex = stack.peek();

        if (!visited.contains(vertex)) {
          visited.add(vertex);
          traversalResult.add(vertex);
        }

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

        if (allNeighborsVisited) {
          stack.pop();
        }
      }
    }

    return traversalResult;
  }

  public List<T> recursiveBreadthFirstSearch() {
    Set<T> roots = getRoots();
    ArrayList<T> source = getSource();
    ArrayList<T> destination = getDestination();
    List<T> bfsTraversal = new ArrayList<T>();
    ArrayList<T> visited = new ArrayList<T>();
    QueueStructure<T> queue = new QueueStructure<T>();

    for (T root : roots) {
      recursiveBFS(root, source, destination, queue, visited, bfsTraversal);
    }

    return bfsTraversal;
  }

  public List<T> recursiveDepthFirstSearch() {
    Set<T> roots = getRoots();
    List<T> traversalResult = new ArrayList<>();
    ArrayList<T> source = getSource();
    ArrayList<T> destination = getDestination();
    ArrayList<T> visited = new ArrayList<>();

    for (T root : roots) {
      recursiveDFS(root, traversalResult, visited, source, destination);
    }

    return traversalResult;
  }

  public ArrayList<T> getSource() {
    ArrayList<T> source = new ArrayList<T>();
    for (Edge<T> edge : edges) {
      source.add(edge.getSource());
    }
    return source;
  }

  public ArrayList<T> getDestination() {
    ArrayList<T> destination = new ArrayList<T>();
    for (Edge<T> edge : edges) {
      destination.add(edge.getDestination());
    }
    return destination;
  }

  class NumericalComparator implements Comparator<T> {
    @Override
    public int compare(T obj1, T obj2) {
      // Assuming T is a numeric type, you can compare them using their numerical values
      // Adjust this logic according to the actual type of T
      return Double.compare(
          Double.parseDouble(obj1.toString()), Double.parseDouble(obj2.toString()));
    }
  }

  public void recursiveBFS(
      T vertex,
      ArrayList<T> source,
      ArrayList<T> destination,
      QueueStructure<T> queue,
      ArrayList<T> visited,
      List<T> bfsTraversal) {
    if (!visited.contains(vertex)) {
      visited.add(vertex);
      bfsTraversal.add(vertex);
    }

    for (int i = 0; i < source.size(); i++) {
      if (source.get(i).hashCode() == vertex.hashCode() && source.get(i).equals(vertex)) {
        T destinationVertex = destination.get(i);
        if (!visited.contains(destinationVertex)) {
          queue.enqueue(destinationVertex);
        }
      }
    }

    if (!queue.isEmpty()) {
      T nextVertex = queue.peek();
      queue.dequeue();
      recursiveBFS(nextVertex, source, destination, queue, visited, bfsTraversal);
    }
  }

  public void recursiveDFS(
      T vertex,
      List<T> traversalResult,
      List<T> visited,
      ArrayList<T> source,
      ArrayList<T> destination) {
    if (!visited.contains(vertex)) {
      visited.add(vertex);
      traversalResult.add(vertex);
    }

    List<T> neighbors = new ArrayList<>();
    for (int i = 0; i < source.size(); i++) {
      if (source.get(i).equals(vertex) && !visited.contains(destination.get(i))) {
        neighbors.add(destination.get(i));
      }
    }

    for (T neighbor : neighbors) {
      if (!visited.contains(neighbor)) {
        recursiveDFS(neighbor, traversalResult, visited, source, destination);
      }
    }
  }
}
