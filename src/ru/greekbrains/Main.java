package ru.greekbrains;

import java.util.Arrays;

public class Main {
    static int size = 10000000;
    static int halfSize = size / 2;

    public static void main(String[] args) {
        float[] a = firstMetod();
        float[] b = secondMetod();
        for (int i = 0; i < size - 1; i++) {
            if (a[i] != b[i]) {
                System.out.println("Не сошлось");
            }
        }
    }

    public static float[] firstMetod() {
        float[] arr = new float[size];
        long q = System.currentTimeMillis();
        Arrays.fill(arr, 1.0f);
        long a = System.currentTimeMillis();
        System.out.println(a - q);
        q = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        a = System.currentTimeMillis();
        System.out.println("Время одного потока: " + (a - q) + " мс");
        return arr;
    }

    public static float[] secondMetod() {
        float[] arr = new float[size];
        float[] arr1 = new float[halfSize];
        float[] arr2 = new float[halfSize];
        for (int i = 0; i < arr.length; i++) arr[i] = 1.0f;
        System.arraycopy(arr, 0, arr1, 0, halfSize);
        System.arraycopy(arr, halfSize, arr2, 0, halfSize);
        System.out.println(arr1.length);
        System.out.println(arr2.length);
        long b = System.currentTimeMillis();
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < arr1.length; i++) {
                arr1[i] = (float) (arr1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });
        Thread thread2 = new Thread(() -> {
            int j = halfSize;
            for (int i = 0; i < arr2.length; i++) {
                arr2[i] = (float) (arr2[i] * Math.sin(0.2f + j / 5) * Math.cos(0.2f + j / 5) * Math.cos(0.4f + j / 2));
                j++;
            }
        });
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.arraycopy(arr1, 0, arr, 0, halfSize);
        System.arraycopy(arr2, 0, arr, halfSize, halfSize);
        System.out.println(arr.length);
        System.out.println("Время после склейки: " + (System.currentTimeMillis() - b) + " мс");
        return arr;
    }
}