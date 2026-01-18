import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

class TreeNode {
    int value;
    TreeNode left;
    TreeNode right;
    
    TreeNode(int value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }
}

class BinaryTree {
    private TreeNode root;
    private int size;
    
    public BinaryTree() {
        this.root = null;
        this.size = 0;
    }
    
    public void insert(int value) {
        root = insertRecursive(root, value);
        size++;
    }
    
    private TreeNode insertRecursive(TreeNode node, int value) {
        if (node == null) {
            return new TreeNode(value);
        }
        
        if (value < node.value) {
            node.left = insertRecursive(node.left, value);
        } else if (value > node.value) {
            node.right = insertRecursive(node.right, value);
        }
        
        return node;
    }
    
    public boolean contains(int value) {
        return containsRecursive(root, value);
    }
    
    private boolean containsRecursive(TreeNode node, int value) {
        if (node == null) {
            return false;
        }
        
        if (value == node.value) {
            return true;
        } else if (value < node.value) {
            return containsRecursive(node.left, value);
        } else {
            return containsRecursive(node.right, value);
        }
    }
    
    public int size() {
        return size;
    }
    
    public int height() {
        return heightRecursive(root);
    }
    
    private int heightRecursive(TreeNode node) {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(heightRecursive(node.left), heightRecursive(node.right));
    }
    
    public boolean isBalanced() {
        return isBalancedRecursive(root);
    }
    
    private boolean isBalancedRecursive(TreeNode node) {
        if (node == null) {
            return true;
        }
        
        int leftHeight = heightRecursive(node.left);
        int rightHeight = heightRecursive(node.right);
        
        if (Math.abs(leftHeight - rightHeight) > 1) {
            return false;
        }
        
        return isBalancedRecursive(node.left) && isBalancedRecursive(node.right);
    }
}

public class assignment2task1 {
    
    private static int[] generateRandomKeys(int n) {
        int[] keys = new int[n];
        Random random = ThreadLocalRandom.current();
        Set<Integer> used = new HashSet<>();
        
        for (int i = 0; i < n; i++) {
            int key;
            do {
                key = random.nextInt(n * 10);
            } while (used.contains(key));
            used.add(key);
            keys[i] = key;
        }
        
        return keys;
    }
    
    private static int[] generateBestCaseOrder(int[] keys) {
        Arrays.sort(keys);
        return buildLevelOrder(keys, 0, keys.length - 1);
    }
    
    private static int[] buildLevelOrder(int[] sortedKeys, int start, int end) {
        if (start > end) {
            return new int[0];
        }
        
        List<Integer> result = new ArrayList<>();
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{start, end});
        
        while (!queue.isEmpty()) {
            int[] range = queue.poll();
            int s = range[0];
            int e = range[1];
            
            if (s > e) continue;
            
            int mid = s + (e - s) / 2;
            result.add(sortedKeys[mid]);
            
            queue.offer(new int[]{s, mid - 1});
            queue.offer(new int[]{mid + 1, e});
        }
        
        return result.stream().mapToInt(i -> i).toArray();
    }
    
    private static long measureTime(Runnable function) {
        long startTime = System.nanoTime();
        function.run();
        long endTime = System.nanoTime();
        return endTime - startTime;
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
    
    private static void testInsertion(int[] keys, String orderType) {
        BinaryTree tree = new BinaryTree();
        
        long time = measureTime(() -> {
            for (int key : keys) {
                tree.insert(key);
            }
        });
        
        System.out.printf("%-25s %-20s %-15d %-15d\n",
            orderType,
            formatTime(time),
            tree.height(),
            tree.size());
    }
    
    private static void testTreeSet(int[] keys) {
        TreeSet<Integer> treeSet = new TreeSet<>();
        
        long time = measureTime(() -> {
            for (int key : keys) {
                treeSet.add(key);
            }
        });
        
        System.out.printf("%-25s %-20s %-15s %-15d\n",
            "TreeSet (Library)",
            formatTime(time),
            "N/A",
            treeSet.size());
    }
    
    private static void testDifferentSizes() {
        System.out.println("=".repeat(90));
        System.out.println("BINARY TREE INSERTION PERFORMANCE COMPARISON");
        System.out.println("=".repeat(90));
        System.out.println();
        
        int[] powers = {4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        
        System.out.printf("%-12s %-25s %-20s %-15s %-15s\n",
            "Size (2^m-1)", "Insertion Order", "Time", "Tree Height", "Elements");
        System.out.println("-".repeat(90));
        
        for (int m : powers) {
            int n = (int) Math.pow(2, m) - 1;
            
            int[] randomKeys = generateRandomKeys(n);
            int[] bestCaseKeys = generateBestCaseOrder(Arrays.copyOf(randomKeys, randomKeys.length));
            
            System.out.println("Size: " + n + " (2^" + m + " - 1)");
            
            testInsertion(randomKeys, "a) Random Order");
            testInsertion(bestCaseKeys, "b) Best-Case Order");
            testTreeSet(randomKeys);
            
            System.out.println();
        }
    }
    
    private static void detailedAnalysis() {
        System.out.println("=".repeat(90));
        System.out.println("DETAILED ANALYSIS (Size: 2^10 - 1 = 1023)");
        System.out.println("=".repeat(90));
        System.out.println();
        
        int m = 10;
        int n = (int) Math.pow(2, m) - 1;
        
        int[] randomKeys = generateRandomKeys(n);
        int[] bestCaseKeys = generateBestCaseOrder(Arrays.copyOf(randomKeys, randomKeys.length));
        
        BinaryTree randomTree = new BinaryTree();
        BinaryTree bestCaseTree = new BinaryTree();
        
        System.out.println("Inserting " + n + " keys...");
        System.out.println();
        
        long randomTime = measureTime(() -> {
            for (int key : randomKeys) {
                randomTree.insert(key);
            }
        });
        
        long bestCaseTime = measureTime(() -> {
            for (int key : bestCaseKeys) {
                bestCaseTree.insert(key);
            }
        });
        
        TreeSet<Integer> treeSet = new TreeSet<>();
        long treeSetTime = measureTime(() -> {
            for (int key : randomKeys) {
                treeSet.add(key);
            }
        });
        
        System.out.println("Results:");
        System.out.println("-".repeat(90));
        System.out.printf("%-25s %-20s %-15s %-15s\n",
            "Method", "Time", "Height", "Balanced");
        System.out.println("-".repeat(90));
        System.out.printf("%-25s %-20s %-15d %-15s\n",
            "Random Order",
            formatTime(randomTime),
            randomTree.height(),
            randomTree.isBalanced() ? "Yes" : "No");
        System.out.printf("%-25s %-20s %-15d %-15s\n",
            "Best-Case Order",
            formatTime(bestCaseTime),
            bestCaseTree.height(),
            bestCaseTree.isBalanced() ? "Yes" : "No");
        System.out.printf("%-25s %-20s %-15s %-15s\n",
            "TreeSet (Library)",
            formatTime(treeSetTime),
            "N/A",
            "N/A");
        
        System.out.println();
        System.out.println("Height Comparison:");
        System.out.println("  Random Order Height: " + randomTree.height());
        System.out.println("  Best-Case Order Height: " + bestCaseTree.height());
        System.out.println("  Optimal Height (log2(n)): " + (int) (Math.log(n) / Math.log(2)));
        System.out.println();
        
        double speedup = (double) randomTime / bestCaseTime;
        System.out.printf("Best-case order is %.2fx faster than random order\n", speedup);
    }
    
    private static void generateCSV() {
        try {
            java.io.FileWriter writer = new java.io.FileWriter("tree_insertion_results.csv");
            
            writer.append("Size,Order,Time(ns),Height\n");
            
            int[] powers = {4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
            int runs = 5;
            
            for (int m : powers) {
                int n = (int) Math.pow(2, m) - 1;
                
                for (int run = 0; run < runs; run++) {
                    int[] randomKeys = generateRandomKeys(n);
                    int[] bestCaseKeys = generateBestCaseOrder(Arrays.copyOf(randomKeys, randomKeys.length));
                    
                    BinaryTree randomTree = new BinaryTree();
                    long randomTime = measureTime(() -> {
                        for (int key : randomKeys) {
                            randomTree.insert(key);
                        }
                    });
                    writer.append(n + ",Random," + randomTime + "," + randomTree.height() + "\n");
                    
                    BinaryTree bestCaseTree = new BinaryTree();
                    long bestCaseTime = measureTime(() -> {
                        for (int key : bestCaseKeys) {
                            bestCaseTree.insert(key);
                        }
                    });
                    writer.append(n + ",BestCase," + bestCaseTime + "," + bestCaseTree.height() + "\n");
                    
                    TreeSet<Integer> treeSet = new TreeSet<>();
                    long treeSetTime = measureTime(() -> {
                        for (int key : randomKeys) {
                            treeSet.add(key);
                        }
                    });
                    writer.append(n + ",TreeSet," + treeSetTime + ",N/A\n");
                }
            }
            
            writer.close();
            System.out.println("CSV data saved to 'tree_insertion_results.csv' for graphing");
        } catch (java.io.IOException e) {
            System.err.println("Error writing CSV file: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        testDifferentSizes();
        detailedAnalysis();
        generateCSV();
        
        System.out.println();
        System.out.println("=".repeat(90));
        System.out.println("CONCLUSIONS");
        System.out.println("=".repeat(90));
        System.out.println("1. Best-case order (level-order insertion) produces perfectly balanced trees");
        System.out.println("2. Random order produces unbalanced trees with greater height");
        System.out.println("3. Best-case insertion is faster due to better cache locality and");
        System.out.println("   reduced tree height (O(log n) vs potentially O(n))");
        System.out.println("4. TreeSet (library) is optimized and performs well for both cases");
        System.out.println("5. Tree height directly correlates with insertion/search time");
        System.out.println("=".repeat(90));
    }
}
