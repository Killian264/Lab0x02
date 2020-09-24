package com.company;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.*;

import java.util.Random;

public class Main {

    public static void main(String[] args) {

        // user must cancel

        int key = 16;
        int count = 100;

        while(true){
            System.out.println("Generating list of length " + count  + ", key width of " + key + ": ");
            char[][] arr =  GenerateTestList(count, key, true);

            System.out.println("Sorting with Selection Sort");
            SelectionSortList(arr);
            System.out.print("Verifying...  ");
            if(IsSorted(arr)){
                System.out.println("Sorted");
            }
            else{
                System.out.println("ERROR NOT SORTED");
            }

            arr =  GenerateTestList(count, key, true);

            System.out.println("Sorting with Insertion Sort");
            InsertionSortList(arr);
            System.out.println("Verifying...");
            if(IsSorted(arr)){
                System.out.println("Sorted");
            }
            else{
                System.out.println("ERROR NOT SORTED");
            }
            count *= 2;

        }
    }

    private static void RunTests(){

//        System.out
//        // INSERTION ASC
//        char[] arr =  GenerateTestList(100, 10, true);
//
//        for(int )
//
//        InsertionSort(arr, arr.length);
//
//        printArray(arr);
//
//
//        // SELECTION DESC
//        arr =  GenerateTestList(100, 10, true);
//
//
//        SelectionSort(arr, arr.length);
//
//        printArray(arr);
    }


    private static void printArray(char[] arr){
        System.out.print("{ ");
        for(char num : arr) {
            System.out.print(num + ", ");
        }
        System.out.print("}");
        System.out.println();
    }

    // ARRAY GENERATORS
    private static char[][] GenerateTestList(int N, int k, boolean useAlphabet){
        char[][] strArr = new char[N][k];
        for (int i = 0; i < k; i++) {
            strArr[i] = GenerateTestString(k, useAlphabet);
        }
        return strArr;
    }


    private static char[] GenerateTestString(int k, boolean useAlphabet){
        char[] arr = new char[k];
        Random r = new Random();
        if(useAlphabet) {
            String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
            for (int i = 0; i < k; i++) {
                arr[i] = alphabet.charAt(r.nextInt(alphabet.length()));
            }
        }
        else{
            for (int i = 0; i < k; i++) {
                arr[i] = (char)(r.nextInt(255) + 1);
            }
        }
        return arr;
    }

    // SORT VALIDATION
    private static boolean IsSorted(char[][] arr){
        for(char[] str : arr){
            if(!IsSortedString(str) ){
                return false;
            }
        }
        return true;
    }

    private static boolean IsSortedString(char[] arr){
        for(int i = 1; i < arr.length; i++){
            if(arr[i] > arr[i-1]){
                return false;
            }
        }
        return true;
    }

    private static void InsertionSortList(char[][] arr){
        for(char[] str : arr) {
            if (!IsSortedString(str)) {
                InsertionSort(str);
            }
        }
    }

    // SORTS
    private static void InsertionSort(char[] arr){

        // starting at element 1 as an array of element 1 will always be sorted
        // go through every element and ensure that 0 to i is sorted
        // if not move current element to its correct location
        for(int i = 1; i < arr.length; i++){
            // if previous greateer
            if(arr[i] > arr[i - 1]){
                // loop till current arr[j] less
                for(int j = i - 1; j >= 0; j--){
                    // if char is in correct location break
                    if(arr[j] >= arr[j + 1]){
                        break;
                    }
                    // swap
                    char temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
    }

    private static void SelectionSortList(char[][] arr){
        for(char[] str : arr) {
            if (!IsSortedString(str)) {
                SelectionSort(str);
            }
        }
    }

    private static void SelectionSort(char[] arr){
        char max;
        int loc;
        char curr;

        // for every element in the array
        for(int i = 0; i < arr.length; i++){
            loc = i;
            max = arr[i];
            // for every element i to the length of the array
            for(int j = i; j < arr.length; j++){
                // if the current is greater or less(depending on asc bool) set it to minmax
                curr = arr[j];
                if(curr > max){
                    max = curr;
                    loc = j;
                }
            }
            // swap minmax to its correct position at i
            char temp = arr[i];
            arr[i] = arr[loc];
            arr[loc] = temp;
        }
    }
}