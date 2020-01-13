package ru.nsu.fit.karaseva.tree;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.Assert;
import org.junit.Test;

public class TreeTests {

  @Test
  public void test1() {
    Node<String> nsu = new Node<>("НГУ");
    Node<String> fit = new Node<>("ФИТ");
    fit.addChild("СИ");
    nsu.addChild("ММФ");
    nsu.addChild("ФФ");
    nsu.addChild("ФЕН");
    nsu.addChild(fit);
    Node<String> fit2 = nsu.getChild("ФИТ");
    Assert.assertNotNull(nsu.getChild("ФИТ"));
    nsu.deleteChild("ФИТ");
    Assert.assertNull(nsu.getChild("ФИТ"));
    Assert.assertFalse(nsu.deleteChild("ФИТ"));
  }

  @Test
  public void test2() {
    Node<Integer> nsu = new Node<>(5);
    try {
      nsu.addChild((Integer) null);
      Assert.fail();
    } catch (NullPointerException ignored) {
    }
    try {
      nsu.getChild(null);
      Assert.fail();
    } catch (NullPointerException ignored) {
    }
    try {
      nsu.deleteChild(null);
      Assert.fail();
    } catch (NullPointerException ignored) {
    }
  }

  @Test
  public void test3() {
    Tree<String> nsu = new Tree<>("НГУ");
    nsu.addChild("ФЕН", "НГУ");
    nsu.addChild("ФИТ", "НГУ");
    nsu.addChild("СИ", "НГУ", "ФИТ");
    nsu.setIterationType(Tree.BFS);
    Iterator<String> iter = nsu.iterator();
    Assert.assertEquals("НГУ", iter.next());
    Assert.assertEquals("ФЕН", iter.next());
    Assert.assertEquals("ФИТ", iter.next());
    Assert.assertEquals("СИ", iter.next());
    nsu.setIterationType(Tree.DFS);
    Assert.assertFalse(iter.hasNext());
  }

  @Test
  public void test4() {
    Tree<String> nsu = new Tree<>("НГУ");
    nsu.addChild("ФИТ", "НГУ");
    nsu.addChild("КОИ", "НГУ", "ФИТ");
    Assert.assertTrue(nsu.deleteChild("ФИТ", "НГУ"));
    Assert.assertFalse(nsu.deleteChild("ФИТ", "НГУ"));
  }

  @Test
  public void test5() {
    Tree<String> nsu = new Tree<>("НГУ");
    nsu.addChild("ММФ", "НГУ");
    nsu.addChild("ФЕН", "НГУ");
    Tree<String> fit = new Tree<>("ФИТ");
    fit.addChild("СИ", "ФИТ");
    fit.addChild("КОИ", "ФИТ");
    nsu.addSubTree(fit);
    nsu.setIterationType(Tree.BFS);
    Iterator<String> iter = nsu.iterator();
    Assert.assertEquals("НГУ", iter.next());
    Assert.assertEquals("ММФ", iter.next());
    Assert.assertEquals("ФЕН", iter.next());
    fit.deleteChild("КОИ", "ФИТ");
    Assert.assertEquals("ФИТ", iter.next());
    Assert.assertEquals("СИ", iter.next());
    Assert.assertEquals("КОИ", iter.next());
    nsu.setIterationType(Tree.DFS);
    Assert.assertFalse(iter.hasNext());
  }

  @Test
  public void test6() {
    Tree<String> nsu = new Tree<>("НГУ");
    try {
      nsu.addChild("НГУ", (String) null);
      Assert.fail();
    } catch (NullPointerException ignored) {
    }
    try {
      nsu.addChild(null, "ФИЯ");
      Assert.fail();
    } catch (NullPointerException ignored) {
    }
  }

  @Test
  public void test7() {
    Tree<String> religion = new Tree<>("1");
    religion.setIterationType(Tree.BFS);
    Node<String> r0 = new Node<>("2");
    Node<String> r1 = new Node<>("3");
    r1.addChild("4");
    r1.addChild("5");
    r0.addChild(r1);
    r1 = new Node<String>("6");
    r1.addChild("7");
    r0.addChild(r1);
    r1 = new Node<>("8");
    r1.addChild("9");
    r1.addChild("10");
    r0.addChild(r1);
    religion.addNode(r0);
    r0 = new Node<>("11");
    r0.addChild("12");
    r0.addChild("13");
    religion.addNode(r0);
    Iterator<String> iter = religion.iterator();
    Assert.assertEquals("1", iter.next());
    Assert.assertEquals("2", iter.next());
    Assert.assertEquals("11", iter.next());
    Assert.assertEquals("3", iter.next());
    Assert.assertEquals("6", iter.next());
    Assert.assertEquals("8", iter.next());
    Assert.assertEquals("12", iter.next());
    Assert.assertEquals("13", iter.next());
    Assert.assertEquals("4", iter.next());
    Assert.assertEquals("5", iter.next());
    Assert.assertEquals("7", iter.next());
    Assert.assertEquals("9", iter.next());
    Assert.assertEquals("10", iter.next());

    Assert.assertFalse(iter.hasNext());
  }

  @Test
  public void test8() {
    Tree<String> nsu = new Tree<>("НГУ");
    nsu.addChild("НГУ", "ММФ");
    nsu.addChild("НГУ", "ИФ");
    nsu.addChild("НГУ", "ФИТ");
    nsu.addChild("НГУ", "ФЕН");
    nsu.setIterationType(Tree.BFS);
    Iterator<String> iter = nsu.iterator();
    while (iter.hasNext()) Assert.assertNotNull(iter.next());
    try {
      iter.next();
      Assert.fail();
    } catch (NoSuchElementException ignored) {
    }
  }
}
