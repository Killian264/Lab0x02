package com.company;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.*;

import java.util.Random;

public class Main {

    public static void main(String[] args) {

        // user must cancel

        int keyStart = 16;
        int countStart = 100;

        String[] testStrings = {"Selection", "Insertion", "Radix", "QuickSort", "MergeSort"};

        for(int i = 0; i < testStrings.length; i++){
            int key = keyStart;
            int count = countStart;
            System.out.println("key = " + key);
            System.out.println("sort type = " + testStrings[i]);
            padder("N"); padder("Time"); padder("Doubling Ratio");
            System.out.println();

            long prevTimeTaken = 1;
            while(true){
                padder(Integer.toString(count));
                prevTimeTaken = Run(count, key, testStrings[i], prevTimeTaken);
                prevTimeTaken = prevTimeTaken >  0 ? prevTimeTaken : 1;
                if(prevTimeTaken > 10000000000L){
                    break;
                }
                count *= 2;
            }
        }
    }

    private static long Run(int count, int key, String sortType, long prev){
        String[] arr =  GenerateTestList(count, key, false);

        long timeStampBefore = getCpuTime();

        if(sortType == "Insertion"){
            InsertionSortList(arr);
        }
        else if(sortType == "Selection"){
            SelectionSortList(arr);
        }
        else if(sortType == "Radix"){
            RadixSortList(arr, key);
        }
        else if(sortType == "QuickSort"){
            StringQuickSort sorter = new StringQuickSort();
            arr = sorter.sort(arr);
        }
        else if(sortType == "MergeSort"){
            MergeSort sorter = new MergeSort();
            sorter.mergeSort(arr, 0, arr.length - 1);
        }


        long timeStampAfter = getCpuTime();
        long time = timeStampAfter - timeStampBefore;

        double actual = (time / prev);

        numberPadder(time);
        padder(Double.toString(actual) + "x");
        System.out.println();

        return time;
    }

    // Testing you can ignore this
    private static void doTests(){
        int key = 16;
        int count = 100;
        while(true){
            System.out.println("Generating list of length " + count  + ", key width of " + key + ": ");
            String[] arr =  GenerateTestList(count, key, true);
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
            System.out.print("Verifying...  ");
            if(IsSorted(arr)){
                System.out.println("Sorted");
            }
            else{
                System.out.println("ERROR NOT SORTED");
            }

            arr =  GenerateTestList(count, key, true);

            System.out.println("Sorting with Radix Sort");
            arr = RadixSortList(arr, key);
            System.out.print("Verifying...  ");
            if(IsSorted(arr)){
                System.out.println("Sorted");
            }
            else{
                System.out.println("ERROR NOT SORTED");
            }

            arr =  GenerateTestList(count, key, true);

            System.out.println("Sorting with Quick Sort");
            StringQuickSort sorter = new StringQuickSort();
            arr = sorter.sort(arr);
            System.out.print("Verifying...  ");
            if(IsSorted(arr)){
                System.out.println("Sorted");
            }
            else{
                System.out.println("ERROR NOT SORTED");
            }
            arr =  GenerateTestList(count, key, true);

            System.out.println("Sorting with Quick Sort");
            MergeSort sorter2 = new MergeSort();
            sorter2.mergeSort(arr, 0, arr.length - 1);
            System.out.print("Verifying...  ");
            if(IsSorted(arr)){
                System.out.println("Sorted");
            }
            else{
                System.out.println("ERROR NOT SORTED");
            }
            count *= 2;
        }
    }
    //print array
    private static void printArray(String[] arr){
        System.out.println();
        for(String str : arr){
            System.out.print(str + ", ");
        }
        System.out.println();
    }

    // ARRAY GENERATORS
    private static String[] GenerateTestList(int N, int k, boolean useAlphabet){
        String[] strArr = new String[N];
        for (int i = 0; i < N; i++) {
            strArr[i] = GenerateTestString(k, useAlphabet);
        }
        return strArr;
    }


    private static String GenerateTestString(int k, boolean useAlphabet){
        char[] arr = new char[k];
        Random r = new Random();
        if(useAlphabet) {
            String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
            for (int i = 0; i < k; i++) {
                arr[i] = alphabet.charAt(r.nextInt(alphabet.length()));
            }
        }
        else{
            // for every character up to k generate a random int up to 255 and cast it to a character
            for (int i = 0; i < k; i++) {
                arr[i] = (char)(r.nextInt(255) + 1);
            }
        }
        String yee = new String(arr);
//        System.out.println(yee);
        return yee;
    }

    // SORT VALIDATION
    private static boolean IsSorted(String[] arr){
        String prev = arr[0];
        // for each string check if this string is greater than the last
        for(String str : arr){
            if(prev.compareTo(str) > 0){
                return false;
            }
            prev = str;
        }
        return true;
    }

    private static String[] RadixSortList(String[] arr, int k){
        int[] bucket = new int[256];
        int[] prefixSum = new int[256];

        // for each char in strings
        // this is as alphabetical order goes from most important to least important like aa > abb > abc
        for(int i = k-1; i >= 0; i--){
            // I could also just swap arr and output
            String[] output = new String[arr.length];
            // for each string in array
            for(int j = 0; j < arr.length; j++){
                // add one to the bucket if the string goes in the bucket
                bucket[(int)arr[j].charAt(i)] += 1;
            }
            // generate prefixSum
            int sum = 0;
            for(int j = 0; j < bucket.length; j++){
                prefixSum[j] = bucket[j] + sum;
                sum = prefixSum[j];
                bucket[j] = 0;
            }

            // build output using prefixSum
            for(int j = arr.length - 1; j >= 0; j--){
                int bucket_loc = (int)arr[j].charAt(i);
                prefixSum[bucket_loc] -= 1;
                output[prefixSum[bucket_loc]] = arr[j];
            }
            // swap
            arr = output;
        }
        return arr;
    }

    private static void InsertionSortList(String[] arr){

        // starting at element 1 as an array of element 1 will always be sorted
        // go through every element and ensure that 0 to i is sorted
        // if not move current element to its correct location
        for(int i = 1; i < arr.length; i++){
            // if previous greateer
            if(arr[i].compareTo(arr[i - 1]) < 0){
                // loop till current arr[j] less
                for(int j = i - 1; j >= 0; j--){
                    if(arr[j].compareTo(arr[j + 1]) <= 0){
                        break;
                    }
                    String temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
    }

    private static void SelectionSortList(String[] arr){
        String min;
        int loc;
        String curr;

        // for every element in the array
        for(int i = 0; i < arr.length; i++){
            loc = i;
            min = arr[i];
            // for every element i to the length of the array
            for(int j = i; j < arr.length; j++){
                // if the current is greater or less(depending on asc bool) set it to minmax
                curr = arr[j];
                if(curr.compareTo(min) < 0){
                    min = curr;
                    loc = j;
                }
            }
            // swap minmax to its correct position at i
            String temp = arr[i];
            arr[i] = arr[loc];
            arr[loc] = temp;
        }
    }

    private static void padder(String str){
        int maxPadding = 20;
        String padding = "";
        for(int i = str.length(); i < maxPadding; i++){
            padding += " ";
        }
        System.out.print("|" + str + padding + "|");
    }

    private static void numberPadder(long number){
        String appended = "";
        if(number < 8000000000000L && number > 8000000000L){
            appended = "s";
            number /= 1000000000;
        }
        else if(number < 8000000000L && number > 8000000){
            appended = "ms";
            number /= 1000000;
        }
        else if(number < 8000000 && number > 8000){
            appended = "us";
            number /= 1000;
        }
        else if(number < 8000){
            appended = "ns";
        }
        padder(Long.toString(number) + appended);
    }

    public static long getCpuTime(){
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported() ? bean.getCurrentThreadCpuTime() : 0L;
    }
}