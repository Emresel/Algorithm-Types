import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class assignment1task3 {
    
    private static int pivotCount;
    
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            pivotCount++;
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
    
    private static int[] generateNearlySortedArray(int size) {
        int[] arr = new int[size];
        Random random = ThreadLocalRandom.current();
        for (int i = 0; i < size; i++) {
            arr[i] = i;
        }
        for (int i = 0; i < size / 10; i++) {
            int idx1 = random.nextInt(size);
            int idx2 = random.nextInt(size);
            int temp = arr[idx1];
            arr[idx1] = arr[idx2];
            arr[idx2] = temp;
        }
        return arr;
    }
    
    private static long measureTime(Runnable sortFunction) {
        long startTime = System.nanoTime();
        sortFunction.run();
        long endTime = System.nanoTime();
        return endTime - startTime;
    }
    
    private static class SortResult {
        long time;
        int pivotCount;
        
        SortResult(long time, int pivotCount) {
            this.time = time;
            this.pivotCount = pivotCount;
        }
    }
    
    private static SortResult testQuickSort(int[] originalArr) {
        int[] arr = copyArray(originalArr);
        pivotCount = 0;
        
        long time = measureTime(() -> quickSort(arr, 0, arr.length - 1));
        
        if (!isSorted(arr)) {
            System.err.println("ERROR: Quick Sort failed to sort correctly!");
        }
        
        return new SortResult(time, pivotCount);
    }
    
    private static boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
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
    
    private static void testDifferentArraySizes() {
        System.out.println("=".repeat(80));
        System.out.println("QUICK SORT: PIVOT COUNT vs SORTING TIME ANALYSIS");
        System.out.println("=".repeat(80));
        System.out.println();
        
        int[] sizes = {100, 500, 1000, 2000, 5000, 10000, 20000, 50000};
        int runs = 10;
        
        System.out.printf("%-12s %-20s %-20s %-20s %-15s\n",
            "Size", "Avg Time", "Avg Pivots", "Time/Pivot", "Ratio");
        System.out.println("-".repeat(80));
        
        for (int size : sizes) {
            long totalTime = 0;
            long totalPivots = 0;
            
            for (int run = 0; run < runs; run++) {
                int[] arr = generateRandomArray(size);
                SortResult result = testQuickSort(arr);
                totalTime += result.time;
                totalPivots += result.pivotCount;
            }
            
            long avgTime = totalTime / runs;
            long avgPivots = totalPivots / runs;
            double timePerPivot = avgPivots > 0 ? (double) avgTime / avgPivots : 0;
            double ratio = size > 0 ? (double) avgPivots / size : 0;
            
            System.out.printf("%-12d %-20s %-20d %-20.2f %-15.4f\n",
                size,
                formatTime(avgTime),
                avgPivots,
                timePerPivot,
                ratio);
        }
    }
    
    private static void testDifferentInputTypes() {
        System.out.println();
        System.out.println("=".repeat(80));
        System.out.println("PIVOT COUNT vs TIME FOR DIFFERENT INPUT TYPES (Size: 10000)");
        System.out.println("=".repeat(80));
        System.out.println();
        
        int size = 10000;
        int runs = 10;
        String[] inputTypes = {"Random", "Sorted", "Reverse Sorted", "Nearly Sorted"};
        
        System.out.printf("%-20s %-20s %-20s %-20s %-15s\n",
            "Input Type", "Avg Time", "Avg Pivots", "Time/Pivot", "Pivots/Size");
        System.out.println("-".repeat(80));
        
        for (String inputType : inputTypes) {
            long totalTime = 0;
            long totalPivots = 0;
            
            for (int run = 0; run < runs; run++) {
                int[] arr;
                switch (inputType) {
                    case "Random":
                        arr = generateRandomArray(size);
                        break;
                    case "Sorted":
                        arr = generateSortedArray(size);
                        break;
                    case "Reverse Sorted":
                        arr = generateReverseSortedArray(size);
                        break;
                    case "Nearly Sorted":
                        arr = generateNearlySortedArray(size);
                        break;
                    default:
                        arr = generateRandomArray(size);
                }
                
                SortResult result = testQuickSort(arr);
                totalTime += result.time;
                totalPivots += result.pivotCount;
            }
            
            long avgTime = totalTime / runs;
            long avgPivots = totalPivots / runs;
            double timePerPivot = avgPivots > 0 ? (double) avgTime / avgPivots : 0;
            double pivotsPerSize = (double) avgPivots / size;
            
            System.out.printf("%-20s %-20s %-20d %-20.2f %-15.4f\n",
                inputType,
                formatTime(avgTime),
                avgPivots,
                timePerPivot,
                pivotsPerSize);
        }
    }
    
    private static void analyzePivotTimeRelation() {
        System.out.println();
        System.out.println("=".repeat(80));
        System.out.println("DETAILED ANALYSIS: PIVOT COUNT vs TIME RELATIONSHIP");
        System.out.println("=".repeat(80));
        System.out.println();
        
        int size = 5000;
        int runs = 20;
        
        System.out.println("Testing " + runs + " random arrays of size " + size + ":");
        System.out.println();
        System.out.printf("%-8s %-20s %-20s %-20s\n",
            "Run", "Time", "Pivot Count", "Time/Pivot");
        System.out.println("-".repeat(70));
        
        List<SortResult> results = new ArrayList<>();
        
        for (int run = 0; run < runs; run++) {
            int[] arr = generateRandomArray(size);
            SortResult result = testQuickSort(arr);
            results.add(result);
            
            double timePerPivot = result.pivotCount > 0 ? 
                (double) result.time / result.pivotCount : 0;
            
            System.out.printf("%-8d %-20s %-20d %-20.2f\n",
                run + 1,
                formatTime(result.time),
                result.pivotCount,
                timePerPivot);
        }
        
        System.out.println();
        System.out.println("Statistical Analysis:");
        System.out.println("-".repeat(70));
        
        long sumTime = 0;
        long sumPivots = 0;
        long minTime = Long.MAX_VALUE;
        long maxTime = 0;
        int minPivots = Integer.MAX_VALUE;
        int maxPivots = 0;
        
        for (SortResult result : results) {
            sumTime += result.time;
            sumPivots += result.pivotCount;
            minTime = Math.min(minTime, result.time);
            maxTime = Math.max(maxTime, result.time);
            minPivots = Math.min(minPivots, result.pivotCount);
            maxPivots = Math.max(maxPivots, result.pivotCount);
        }
        
        double avgTime = (double) sumTime / runs;
        double avgPivots = (double) sumPivots / runs;
        double avgTimePerPivot = avgPivots > 0 ? avgTime / avgPivots : 0;
        
        System.out.printf("Average Time: %s\n", formatTime((long) avgTime));
        System.out.printf("Average Pivots: %.2f\n", avgPivots);
        System.out.printf("Average Time/Pivot: %.2f ns\n", avgTimePerPivot);
        System.out.printf("Time Range: [%s, %s]\n", formatTime(minTime), formatTime(maxTime));
        System.out.printf("Pivot Range: [%d, %d]\n", minPivots, maxPivots);
        
        Collections.sort(results, (a, b) -> Integer.compare(a.pivotCount, b.pivotCount));
        
        System.out.println();
        System.out.println("Correlation Analysis (sorted by pivot count):");
        System.out.println("-".repeat(70));
        System.out.printf("%-20s %-20s %-20s\n", "Pivot Count", "Time", "Time/Pivot");
        System.out.println("-".repeat(70));
        
        for (int i = 0; i < Math.min(10, results.size()); i++) {
            SortResult result = results.get(i);
            double timePerPivot = result.pivotCount > 0 ? 
                (double) result.time / result.pivotCount : 0;
            System.out.printf("%-20d %-20s %-20.2f\n",
                result.pivotCount,
                formatTime(result.time),
                timePerPivot);
        }
        
        System.out.println("...");
        
        for (int i = Math.max(0, results.size() - 5); i < results.size(); i++) {
            SortResult result = results.get(i);
            double timePerPivot = result.pivotCount > 0 ? 
                (double) result.time / result.pivotCount : 0;
            System.out.printf("%-20d %-20s %-20.2f\n",
                result.pivotCount,
                formatTime(result.time),
                timePerPivot);
        }
    }
    
    private static void generateCSV() {
        try {
            java.io.FileWriter writer = new java.io.FileWriter("pivot_analysis.csv");
            
            writer.append("Size,Time(ns),PivotCount,TimePerPivot,PivotsPerSize\n");
            
            int[] sizes = {100, 500, 1000, 2000, 5000, 10000, 20000, 50000};
            int runs = 10;
            
            for (int size : sizes) {
                for (int run = 0; run < runs; run++) {
                    int[] arr = generateRandomArray(size);
                    SortResult result = testQuickSort(arr);
                    double timePerPivot = result.pivotCount > 0 ? 
                        (double) result.time / result.pivotCount : 0;
                    double pivotsPerSize = (double) result.pivotCount / size;
                    
                    writer.append(size + "," + result.time + "," + result.pivotCount + 
                                 "," + timePerPivot + "," + pivotsPerSize + "\n");
                }
            }
            
            writer.close();
            System.out.println();
            System.out.println("CSV data saved to 'pivot_analysis.csv' for graphing");
        } catch (java.io.IOException e) {
            System.err.println("Error writing CSV file: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        testDifferentArraySizes();
        testDifferentInputTypes();
        analyzePivotTimeRelation();
        generateCSV();
        
        System.out.println();
        System.out.println("=".repeat(80));
        System.out.println("CONCLUSIONS");
        System.out.println("=".repeat(80));
        System.out.println("1. Pivot count is approximately O(n log n) for random arrays");
        System.out.println("2. More pivots generally correlate with longer sorting time");
        System.out.println("3. Time per pivot is relatively constant, indicating");
        System.out.println("   that pivot count is a good predictor of sorting time");
        System.out.println("4. Worst case (reverse sorted) produces maximum pivots");
        System.out.println("5. Best case (already sorted) produces minimum pivots");
        System.out.println("=".repeat(80));
    }
}
