package ru.nsu.fit.karaseva.tree;

import java.util.*;

public class Tree<T extends Comparable<T>> implements Iterable<T> {
  public static final char DFS = 'd';
  public static final char BFS = 'b';
  private char iterationType = DFS;
  Node<T> root;
  boolean isSaveState = false;

  /**
   * Tree constructor.
   *
   * @param root - value of root of tree.
   * @throws NullPointerException if root is null.
   */
  public Tree(T root) {
    if (root == null) {
      throw new NullPointerException();
    }
    this.root = new Node<>(root);
  }

  /**
   * Method that returns value of the root.
   *
   * @return value of root of current tree.
   */
  public T getRoot() {
    return root.getValue();
  }

  /**
   * Method that changes root.
   *
   * @param root - value of new root.
   */
  public void setRoot(T root) {
    this.root.setRoot(root);
    isSaveState = false;
  }

  /**
   * Method that adds child to Node "node" with value "value".
   *
   * @param way - is way to added child.
   * @param value - value of added node.
   * @return true - if Node with name node exist and value was added, otherwise - false;
   * @throws NullPointerException - if one of parameters is null.
   */
  public boolean addChild(T value, T... way) throws NullPointerException {
    if (value == null) {
      throw new NullPointerException();
    }
    Node<T> currentNode = root;
    if (way[0] == null) {
      throw new NullPointerException();
    }
    if (way[0] != currentNode.getValue()) {
      return false;
    }
    for (int i = 1; i < way.length - 1; ++i) {
      if (way[i] == null) {
        throw new NullPointerException();
      }
      currentNode = currentNode.getChild(way[i]);
      if (currentNode == null) {
        return false;
      }
    }
    currentNode.addChild(value);
    isSaveState = false;
    return true;
  }

  /**
   * Method that copies Tree as new child of current tree, all action on old tree doesn't effect on
   * current tree.
   *
   * @param subTree - child to add.
   * @throws IllegalArgumentException if child with subtree.value already exist.
   */
  public void addSubTree(Tree<T> subTree) throws IllegalArgumentException {
    if (root.isExists(subTree.root.getValue())) {
      throw new IllegalArgumentException();
    }
    root.addChild(subTree.root.copy());
  }

  void addNode(Node<T> subTree) throws NullPointerException {
    if (subTree == null) {
      throw new NullPointerException();
    }
    root.addChild(subTree);
  }

  /**
   * Method that finds, deletes and returns a subTree.
   *
   * @param value - root of finding subtree.
   * @return Tree with value as a root.
   * @throws NullPointerException value is null.
   */
  public boolean deleteChild(T value, T... way) throws NullPointerException {
    if (value == null) {
      throw new NullPointerException();
    }
    Node<T> currentNode = root;
    if (way[0] != currentNode.getValue()) {
      return false;
    }
    isSaveState = false;
    for (int i = 1; i < way.length - 1; ++i) {
      currentNode = currentNode.getChild(way[i]);
      if (currentNode == null) {
        return false;
      }
    }
    return currentNode.deleteChild(value);
  }

  private void saveState() {
    isSaveState = true;
    root.saveState();
  }

  private boolean isChanged() {
    if (isSaveState) {
      return !root.isNotChanged();
    }
    return true;
  }

  /**
   * Change iteration type.
   *
   * @param iterationType changes type of the iterator.
   */
  public void setIterationType(char iterationType) {
    this.iterationType = iterationType;
    isSaveState = false;
  }

  /**
   * Method that returns Iterator.
   *
   * @return If iterationType is DFS returns iterator, working with DFS, else return BFS-iterator.
   * @throws NoSuchElementException if iterator is empty.
   * @throws ConcurrentModificationException if you'll try take element from iterator after tree's
   *     modification.
   */
  public Iterator<T> iterator() {
    saveState();
    List<Node<T>> queue = new LinkedList<>();
    queue.add(root);
    if (iterationType == DFS) {
      return dfs(queue);
    } else {
      return bfs(queue);
    }
  }

  private Iterator<T> dfs(List<Node<T>> queue)
      throws ConcurrentModificationException, NoSuchElementException {
    return new Iterator<T>() {
      public boolean hasNext() {
        return queue.size() > 0;
      }

      public T next() {
        if (isChanged()) {
          throw new ConcurrentModificationException();
        }
        if (queue.size() < 1) {
          throw new NoSuchElementException();
        }
        Node<T> curr = queue.remove(0);
        int currSize = curr.getChildren().size();
        for (int i = 0; i < currSize; i++) {
          queue.add(i, curr.getChildren().get(i));
        }
        return curr.getValue();
      }
    };
  }

  private Iterator<T> bfs(List<Node<T>> queue)
      throws ConcurrentModificationException, NoSuchElementException {
    return new Iterator<T>() {
      public boolean hasNext() {
        return queue.size() > 0;
      }

      public T next() {
        if (isChanged()) {
          throw new ConcurrentModificationException();
        }
        if (queue.size() < 1) {
          throw new NoSuchElementException();
        }
        Node<T> next = queue.get(0);
        queue.addAll(next.getChildren());
        queue.remove(0);
        return next.getValue();
      }
    };
  }
}
