package killiandebacker.com;

public class Main {

    public static void main(String[] args) {
        RunTests();
    }

    private static void RunTests(){

        // INSERTION ASC
        int[] arr =  testArray();

        System.out.println("TEST ARRAY");

        printArray(arr);

        System.out.println("INSERTION ASC");

        InsertionSort(arr, arr.length, true);

        printArray(arr);

        // INSERTION DESC
        arr =  testArray();

        System.out.println("INSERTION DESC");

        InsertionSort(arr, arr.length, false);

        printArray(arr);

        // SELECTION ASC
        arr =  testArray();

        System.out.println("SELECTION ASC");

        SelectionSort(arr, arr.length, true);

        printArray(arr);

        // SELECTION DESC
        arr =  testArray();

        System.out.println("SELECTION DESC");

        SelectionSort(arr, arr.length, false);

        printArray(arr);



    }

    private static int[] testArray(){
        int[] arr = { 1, 14, 32, 7, 4, 3, 1, 3, 4 };
        return arr;
    }

    private static void printArray(int[] arr){
        System.out.print("{ ");
        for(int num : arr) {
            System.out.print(num + ", ");
        }
        System.out.print("}");
        System.out.println();
    }

    private static void InsertionSort(int[] arr, int count, boolean asc){
        if(asc){
            InsertionSortAsc(arr, count);
        }
        else{
            InsertionSortDesc(arr, count);
        }
    }

    private static void InsertionSortAsc(int[] arr, int count){
        int minmax = arr[0];
        int curr;

        for(int i = 1; i < count; i++){
            minmax = arr[i];
            // if previous greateer
            if(arr[i] < arr[i - 1]){
                // loop till current arr[j] less
                for(int j = i - 1; j > 0; j--){
                    if(arr[j] <= arr[j + 1]){
                        break;
                    }
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
    }

    private static void InsertionSortDesc(int[] arr, int count){
        int minmax = arr[0];
        int curr;

        for(int i = 1; i < count; i++){
            minmax = arr[i];
            // if previous greateer
            if(arr[i] > arr[i - 1]){
                // loop till current arr[j] less
                for(int j = i - 1; j >= 0; j--){
                    if(arr[j] >= arr[j + 1]){
                        break;
                    }
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
    }

    private static void SelectionSort(int[] arr, int count, boolean asc){
        int minmax;
        int loc;
        int curr;

        for(int i = 0; i < count; i++){
            loc = i;
            minmax = arr[i];
            for(int j = i; j < count; j++){
                curr = arr[j];
                if(asc && curr < minmax){
                    minmax = curr;
                    loc = j;
                }
                else if(!asc && curr > minmax){
                    minmax = curr;
                    loc = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[loc];
            arr[loc] = temp;
        }
    }
}
