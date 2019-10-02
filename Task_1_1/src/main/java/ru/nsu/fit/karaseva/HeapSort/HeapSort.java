package ru.nsu.fit.karaseva.HeapSort;

public class HeapSort {
    private static void makeHeap(int[] arr, int i, int amountOfElements) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (l < amountOfElements && arr[i] < arr[l]) {
            largest = l;
        }
        if (r < amountOfElements && arr[largest] < arr[r]) {
            largest = r;
        }
        if (i != largest) {
            swap(arr, i, largest);
            makeHeap(arr, largest, amountOfElements);
        }
    }

    public static int[] heapSort(int[] arr) {
        if (arr == null)
            return null;
        int amountOfElements = arr.length;
        for (int i = arr.length / 2; i >= 0; i--) {
            HeapSort.makeHeap(arr, i, amountOfElements);
        }
        while (amountOfElements > 1) {
            swap(arr, 0, amountOfElements - 1);
            amountOfElements--;
            makeHeap(arr, 0, amountOfElements);
        }
        return arr;
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
