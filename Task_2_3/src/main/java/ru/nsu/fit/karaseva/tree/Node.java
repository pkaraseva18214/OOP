package ru.nsu.fit.karaseva.tree;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
/**
 * Class that implements nodes of the tree.
 *
 * @param <T> type of the nodes (values).
 */
class Node<T extends Comparable<T>> {
  private boolean isStateSave = false;
  private T value;
  private List<Node<T>> children;
  private Set<T> savedNodes = new HashSet<>();

  /**
   * Constructor of the Nodes.
   *
   * @param root - root of the tree.
   * @throws NullPointerException if root is null.
   */
  public Node(T root) throws NullPointerException {
    if (root == null) {
      throw new NullPointerException();
    }
    isStateSave = false;
    value = root;
    children = new LinkedList<>();
  }

  /**
   * Method that returns value of the node.
   *
   * @return value of the node.
   */
  public T getValue() {
    return value;
  }

  void setRoot(T root) {
    value = root;
  }

  void addChild(T elem) throws NullPointerException {
    if (elem == null) {
      throw new NullPointerException();
    }
    if (savedNodes.contains(elem)) {
      throw new IllegalArgumentException();
    }
    children.add(new Node<T>(elem));
    isStateSave = false;
    savedNodes.add(elem);
  }

  void addChild(Node<T> child) {
    if (child == null) {
      throw new NullPointerException();
    }
    if (savedNodes.contains(child.value)) {
      throw new IllegalArgumentException();
    }
    children.add(child);
    isStateSave = false;
    savedNodes.add(child.value);
  }

  boolean isExists(T value) {
    return savedNodes.contains(value);
  }

  boolean deleteChild(T value) throws NullPointerException {
    if (value == null) {
      throw new NullPointerException();
    }
    Node<T> child;
    for (int i = 0; i < children.size(); ++i) {
      child = children.get(i);
      if (child.value.compareTo(value) == 0) {
        isStateSave = false;
        children.remove(i);
        savedNodes.remove(value);
        return true;
      }
    }
    return false;
  }

  /**
   * Method that returns list of children of the node.
   *
   * @return list of children.
   */
  List<Node<T>> getChildren() {
    return children;
  }

  Node<T> getChild(T value) {
    if (value == null) {
      throw new NullPointerException();
    }
    for (Node<T> child : children) {
      if (child.value.equals(value)) {
        return child;
      }
    }
    return null;
  }

  Node<T> copy() {
    Node<T> ret = new Node<>(value);
    List<Node<T>> copySubNodes = new LinkedList<>();
    Set<T> saved = new HashSet<>(savedNodes);
    for (Node<T> curr : children) {
      Node<T> currCopy = curr.copy();
      copySubNodes.add(currCopy);
    }
    ret.children = copySubNodes;
    ret.savedNodes = saved;
    return ret;
  }

  void saveState() {
    isStateSave = true;
    for (Node<T> child : children) {
      child.saveState();
    }
  }

  boolean isNotChanged() {
    if (isStateSave) {
      for (Node<T> child : children) {
        boolean res = child.isNotChanged();
        if (!res) {
          return false;
        }
      }
    } else return false;
    return true;
  }
}
