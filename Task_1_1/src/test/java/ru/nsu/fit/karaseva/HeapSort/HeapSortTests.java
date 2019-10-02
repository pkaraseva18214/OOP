package ru.nsu.fit.karaseva.HeapSort;

import org.junit.Assert;
import org.junit.Test;

public class HeapSortTests {
    @Test
    public void test1() {
        int[] arr = {150, -23, 6, 0, 97, -24, -11, 10};
        int[] expected = {-24, -23, -11, 0, 6, 10, 97, 150};
        Assert.assertArrayEquals(expected, HeapSort.heapSort(arr));
        System.out.println("Test 1 passed successfully");
    }
    @Test
    public void test2() {
        int[] arr = {20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, -15};
        int[] expected = {-15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        Assert.assertArrayEquals(expected, HeapSort.heapSort(arr));
        System.out.println("Test 2 passed successfully");
    }
    @Test
    public void test3() {
        int[] arr = {-15, -18, -1789, -53, 12, 10003, 152, -46};
        int[] expected = {-1789, -53, -46, -18, -15, 12, 152, 10003};
        Assert.assertArrayEquals(expected, HeapSort.heapSort(arr));
        System.out.println("Test 3 passed successfully");
    }
    @Test
    public void test4() {
        int[] arr = {0, 1, 1, 56, 909, 123, 123, 123, 123, 15};
        int[] expected = {0, 1, 1, 15, 56, 123, 123, 123, 123, 909};
        Assert.assertArrayEquals(expected, HeapSort.heapSort(arr));
        System.out.println("Test 4 passed successfully");
    }
    @Test
    public void test5() {
        int[] arr = {10, 5666, 456, 91, -45, -789, -10, 0, 12, 9099, 666, 13, 4056};
        int[] expected = {-789, -45, -10, 0, 10, 12, 13, 91, 456, 666, 4056, 5666, 9099};
        Assert.assertArrayEquals(expected, HeapSort.heapSort(arr));
        System.out.println("Test 5 passed successfully");
    }
}
