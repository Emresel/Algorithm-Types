import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class assignment1task1partA {
    
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
    
    public static void insertionSort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
    
    public static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }
    
    private static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        
        int[] leftArr = new int[n1];
        int[] rightArr = new int[n2];
        
        for (int i = 0; i < n1; i++) {
            leftArr[i] = arr[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArr[j] = arr[mid + 1 + j];
        }
        
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (leftArr[i] <= rightArr[j]) {
                arr[k] = leftArr[i];
                i++;
            } else {
                arr[k] = rightArr[j];
                j++;
            }
            k++;
        }
        
        while (i < n1) {
            arr[k] = leftArr[i];
            i++;
            k++;
        }
        
        while (j < n2) {
            arr[k] = rightArr[j];
            j++;
            k++;
        }
    }
    
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }
    
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        
        return i + 1;
    }
    
    private static int[] copyArray(int[] arr) {
        return Arrays.copyOf(arr, arr.length);
    }
    
    private static int[] generateRandomArray(int size) {
        int[] arr = new int[size];
        Random random = ThreadLocalRandom.current();
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(10000);
        }
        return arr;
    }
    
    private static int[] generateSortedArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = i;
        }
        return arr;
    }
    
    private static int[] generateReverseSortedArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = size - i;
        }
        return arr;
    }
    
    private static long measureTime(Runnable sortFunction) {
        long startTime = System.nanoTime();
        sortFunction.run();
        long endTime = System.nanoTime();
        return endTime - startTime;
    }
    
    private static long testSort(String algorithmName, int[] originalArr, String inputType) {
        int[] arr = copyArray(originalArr);
        long time = 0;
        
        switch (algorithmName) {
            case "Bubble Sort":
                time = measureTime(() -> bubbleSort(arr));
                break;
            case "Insertion Sort":
                time = measureTime(() -> insertionSort(arr));
                break;
            case "Merge Sort":
                time = measureTime(() -> mergeSort(arr, 0, arr.length - 1));
                break;
            case "Quick Sort":
                time = measureTime(() -> quickSort(arr, 0, arr.length - 1));
                break;
        }
        
        if (!isSorted(arr)) {
            System.err.println("ERROR: " + algorithmName + " failed to sort correctly!");
        }
        
        return time;
    }
    
    private static boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }
    
    public static void main(String[] args) {
        String[] algorithms = {"Bubble Sort", "Insertion Sort", "Merge Sort", "Quick Sort"};
        
        int[] smallSizes = {5, 10, 15, 20, 25, 30, 35, 40, 45, 50};
        
        int[] largeSizes = {100, 200, 300, 500, 750, 1000, 1500, 2000, 3000, 5000};
        
        System.out.println("=".repeat(80));
        System.out.println("SORTING ALGORITHM PERFORMANCE COMPARISON");
        System.out.println("=".repeat(80));
        System.out.println();
        
        System.out.println("SMALL INPUT SIZES (5-50)");
        System.out.println("-".repeat(80));
        testSizes(smallSizes, algorithms, "Random");
        System.out.println();
        
        System.out.println("LARGE INPUT SIZES (>100)");
        System.out.println("-".repeat(80));
        testSizes(largeSizes, algorithms, "Random");
        System.out.println();
        
        System.out.println("DETAILED ANALYSIS (Size: 1000)");
        System.out.println("-".repeat(80));
        analyzeCases(1000, algorithms);
        System.out.println();
        
        generateCSV(smallSizes, largeSizes, algorithms);
        System.out.println("CSV data saved to 'sorting_results.csv' for graphing");
    }
    
    private static void testSizes(int[] sizes, String[] algorithms, String inputType) {
        System.out.printf("%-15s", "Size");
        for (String alg : algorithms) {
            System.out.printf("%-20s", alg);
        }
        System.out.println();
        System.out.println("-".repeat(80));
        
        for (int size : sizes) {
            System.out.printf("%-15d", size);
            
            int runs = size < 100 ? 100 : 10;
            long[][] times = new long[algorithms.length][runs];
            
            for (int run = 0; run < runs; run++) {
                int[] arr = generateRandomArray(size);
                for (int i = 0; i < algorithms.length; i++) {
                    times[i][run] = testSort(algorithms[i], arr, inputType);
                }
            }
            
            for (int i = 0; i < algorithms.length; i++) {
                long sum = 0;
                for (int run = 0; run < runs; run++) {
                    sum += times[i][run];
                }
                long avgTime = sum / runs;
                System.out.printf("%-20s", formatTime(avgTime));
            }
            System.out.println();
        }
    }
    
    private static void analyzeCases(int size, String[] algorithms) {
        System.out.printf("%-20s", "Input Type");
        for (String alg : algorithms) {
            System.out.printf("%-20s", alg);
        }
        System.out.println();
        System.out.println("-".repeat(80));
        
        String[] cases = {"Random", "Sorted", "Reverse Sorted"};
        int runs = 10;
        
        for (String caseType : cases) {
            System.out.printf("%-20s", caseType);
            
            for (String alg : algorithms) {
                long totalTime = 0;
                for (int run = 0; run < runs; run++) {
                    int[] arr;
                    switch (caseType) {
                        case "Random":
                            arr = generateRandomArray(size);
                            break;
                        case "Sorted":
                            arr = generateSortedArray(size);
                            break;
                        case "Reverse Sorted":
                            arr = generateReverseSortedArray(size);
                            break;
                        default:
                            arr = generateRandomArray(size);
                    }
                    totalTime += testSort(alg, arr, caseType);
                }
                long avgTime = totalTime / runs;
                System.out.printf("%-20s", formatTime(avgTime));
            }
            System.out.println();
        }
    }
    
    private static String formatTime(long nanoseconds) {
        if (nanoseconds < 1000) {
            return nanoseconds + " ns";
        } else if (nanoseconds < 1_000_000) {
            return String.format("%.2f μs", nanoseconds / 1000.0);
        } else if (nanoseconds < 1_000_000_000) {
            return String.format("%.2f ms", nanoseconds / 1_000_000.0);
        } else {
            return String.format("%.2f s", nanoseconds / 1_000_000_000.0);
        }
    }
    
    private static void generateCSV(int[] smallSizes, int[] largeSizes, String[] algorithms) {
        try {
            java.io.FileWriter writer = new java.io.FileWriter("sorting_results.csv");
            
            writer.append("Size,Algorithm,Time(ns)\n");
            
            for (int size : smallSizes) {
                int runs = 100;
                for (String alg : algorithms) {
                    long totalTime = 0;
                    for (int run = 0; run < runs; run++) {
                        int[] arr = generateRandomArray(size);
                        totalTime += testSort(alg, arr, "Random");
                    }
                    long avgTime = totalTime / runs;
                    writer.append(size + "," + alg + "," + avgTime + "\n");
                }
            }
            
            for (int size : largeSizes) {
                int runs = 10;
                for (String alg : algorithms) {
                    long totalTime = 0;
                    for (int run = 0; run < runs; run++) {
                        int[] arr = generateRandomArray(size);
                        totalTime += testSort(alg, arr, "Random");
                    }
                    long avgTime = totalTime / runs;
                    writer.append(size + "," + alg + "," + avgTime + "\n");
                }
            }
            
            writer.close();
        } catch (java.io.IOException e) {
            System.err.println("Error writing CSV file: " + e.getMessage());
        }
    }
}
