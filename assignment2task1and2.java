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
    
    public boolean delete(int value) {
        if (!contains(value)) {
            return false;
        }
        root = deleteRecursive(root, value);
        size--;
        return true;
    }
    
    private TreeNode deleteRecursive(TreeNode node, int value) {
        if (node == null) {
            return null;
        }
        
        if (value < node.value) {
            node.left = deleteRecursive(node.left, value);
        } else if (value > node.value) {
            node.right = deleteRecursive(node.right, value);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }
            
            node.value = minValue(node.right);
            node.right = deleteRecursive(node.right, node.value);
        }
        
        return node;
    }
    
    private int minValue(TreeNode node) {
        int min = node.value;
        while (node.left != null) {
            min = node.left.value;
            node = node.left;
        }
        return min;
    }
}

class TernaryTreeNode {
    int key1;
    int key2;
    TernaryTreeNode left;
    TernaryTreeNode middle;
    TernaryTreeNode right;
    boolean hasTwoKeys;
    
    TernaryTreeNode(int value) {
        this.key1 = value;
        this.key2 = Integer.MAX_VALUE;
        this.hasTwoKeys = false;
        this.left = null;
        this.middle = null;
        this.right = null;
    }
}

class TernaryTree {
    private TernaryTreeNode root;
    private int size;
    
    public TernaryTree() {
        this.root = null;
        this.size = 0;
    }
    
    public void insert(int value) {
        root = insertRecursive(root, value);
        size++;
    }
    
    private TernaryTreeNode insertRecursive(TernaryTreeNode node, int value) {
        if (node == null) {
            return new TernaryTreeNode(value);
        }
        
        if (!node.hasTwoKeys) {
            if (value < node.key1) {
                node.left = insertRecursive(node.left, value);
            } else if (value > node.key1) {
                node.key2 = value;
                node.hasTwoKeys = true;
            }
        } else {
            if (value < node.key1) {
                node.left = insertRecursive(node.left, value);
            } else if (value > node.key1 && value < node.key2) {
                node.middle = insertRecursive(node.middle, value);
            } else if (value > node.key2) {
                node.right = insertRecursive(node.right, value);
            }
        }
        
        return node;
    }
    
    public int size() {
        return size;
    }
    
    public int height() {
        return heightRecursive(root);
    }
    
    private int heightRecursive(TernaryTreeNode node) {
        if (node == null) {
            return -1;
        }
        int leftHeight = heightRecursive(node.left);
        int middleHeight = heightRecursive(node.middle);
        int rightHeight = heightRecursive(node.right);
        return 1 + Math.max(Math.max(leftHeight, middleHeight), rightHeight);
    }
}

class AVLTreeNode {
    int value;
    AVLTreeNode left;
    AVLTreeNode right;
    int height;
    
    AVLTreeNode(int value) {
        this.value = value;
        this.left = null;
        this.right = null;
        this.height = 0;
    }
}

class AVLTree {
    private AVLTreeNode root;
    private int size;
    
    public AVLTree() {
        this.root = null;
        this.size = 0;
    }
    
    public void insert(int value) {
        root = insertRecursive(root, value);
        size++;
    }
    
    private AVLTreeNode insertRecursive(AVLTreeNode node, int value) {
        if (node == null) {
            return new AVLTreeNode(value);
        }
        
        if (value < node.value) {
            node.left = insertRecursive(node.left, value);
        } else if (value > node.value) {
            node.right = insertRecursive(node.right, value);
        } else {
            return node;
        }
        
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        
        int balance = getBalance(node);
        
        if (balance > 1 && value < node.left.value) {
            return rightRotate(node);
        }
        
        if (balance < -1 && value > node.right.value) {
            return leftRotate(node);
        }
        
        if (balance > 1 && value > node.left.value) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        
        if (balance < -1 && value < node.right.value) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        
        return node;
    }
    
    private int getHeight(AVLTreeNode node) {
        if (node == null) {
            return -1;
        }
        return node.height;
    }
    
    private int getBalance(AVLTreeNode node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }
    
    private AVLTreeNode rightRotate(AVLTreeNode y) {
        AVLTreeNode x = y.left;
        AVLTreeNode T2 = x.right;
        
        x.right = y;
        y.left = T2;
        
        y.height = 1 + Math.max(getHeight(y.left), getHeight(y.right));
        x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));
        
        return x;
    }
    
    private AVLTreeNode leftRotate(AVLTreeNode x) {
        AVLTreeNode y = x.right;
        AVLTreeNode T2 = y.left;
        
        y.left = x;
        x.right = T2;
        
        x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));
        y.height = 1 + Math.max(getHeight(y.left), getHeight(y.right));
        
        return y;
    }
    
    public int size() {
        return size;
    }
    
    public int height() {
        return getHeight(root);
    }
    
    public boolean isBalanced() {
        return isBalancedRecursive(root);
    }
    
    private boolean isBalancedRecursive(AVLTreeNode node) {
        if (node == null) {
            return true;
        }
        
        int balance = getBalance(node);
        if (Math.abs(balance) > 1) {
            return false;
        }
        
        return isBalancedRecursive(node.left) && isBalancedRecursive(node.right);
    }
}

public class assignment2task1and2 {
    
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
    
    private static void testTernaryTree(int[] keys, String orderType) {
        TernaryTree tree = new TernaryTree();
        
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
    
    private static void testAVLTree(int[] keys, String orderType) {
        AVLTree tree = new AVLTree();
        
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
    
    private static void testDifferentSizes() {
        System.out.println("=".repeat(90));
        System.out.println("TREE INSERTION PERFORMANCE COMPARISON");
        System.out.println("=".repeat(90));
        System.out.println();
        
        int[] powers = {4, 5, 6, 7, 8, 9, 10, 11, 12};
        
        System.out.printf("%-12s %-25s %-20s %-15s %-15s\n",
            "Size (2^m-1)", "Tree Type", "Time", "Tree Height", "Elements");
        System.out.println("-".repeat(90));
        
        for (int m : powers) {
            int n = (int) Math.pow(2, m) - 1;
            
            int[] randomKeys = generateRandomKeys(n);
            int[] bestCaseKeys = generateBestCaseOrder(Arrays.copyOf(randomKeys, randomKeys.length));
            
            System.out.println("Size: " + n + " (2^" + m + " - 1)");
            
            testInsertion(randomKeys, "a) Binary Random");
            testInsertion(bestCaseKeys, "b) Binary Best-Case");
            testTreeSet(randomKeys);
            testTernaryTree(randomKeys, "d) Ternary Random");
            testTernaryTree(bestCaseKeys, "e) Ternary Best-Case");
            testAVLTree(randomKeys, "f) AVL Random");
            
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
        
        BinaryTree binaryRandomTree = new BinaryTree();
        BinaryTree binaryBestCaseTree = new BinaryTree();
        TernaryTree ternaryRandomTree = new TernaryTree();
        TernaryTree ternaryBestCaseTree = new TernaryTree();
        AVLTree avlTree = new AVLTree();
        
        System.out.println("Inserting " + n + " keys...");
        System.out.println();
        
        long binaryRandomTime = measureTime(() -> {
            for (int key : randomKeys) {
                binaryRandomTree.insert(key);
            }
        });
        
        long binaryBestCaseTime = measureTime(() -> {
            for (int key : bestCaseKeys) {
                binaryBestCaseTree.insert(key);
            }
        });
        
        long ternaryRandomTime = measureTime(() -> {
            for (int key : randomKeys) {
                ternaryRandomTree.insert(key);
            }
        });
        
        long ternaryBestCaseTime = measureTime(() -> {
            for (int key : bestCaseKeys) {
                ternaryBestCaseTree.insert(key);
            }
        });
        
        long avlTime = measureTime(() -> {
            for (int key : randomKeys) {
                avlTree.insert(key);
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
            "a) Binary Random",
            formatTime(binaryRandomTime),
            binaryRandomTree.height(),
            binaryRandomTree.isBalanced() ? "Yes" : "No");
        System.out.printf("%-25s %-20s %-15d %-15s\n",
            "b) Binary Best-Case",
            formatTime(binaryBestCaseTime),
            binaryBestCaseTree.height(),
            binaryBestCaseTree.isBalanced() ? "Yes" : "No");
        System.out.printf("%-25s %-20s %-15s %-15s\n",
            "c) TreeSet (Library)",
            formatTime(treeSetTime),
            "N/A",
            "N/A");
        System.out.printf("%-25s %-20s %-15d %-15s\n",
            "d) Ternary Random",
            formatTime(ternaryRandomTime),
            ternaryRandomTree.height(),
            "-");
        System.out.printf("%-25s %-20s %-15d %-15s\n",
            "e) Ternary Best-Case",
            formatTime(ternaryBestCaseTime),
            ternaryBestCaseTree.height(),
            "-");
        System.out.printf("%-25s %-20s %-15d %-15s\n",
            "f) AVL Tree",
            formatTime(avlTime),
            avlTree.height(),
            avlTree.isBalanced() ? "Yes" : "No");
        
        System.out.println();
        System.out.println("Height Comparison:");
        System.out.println("  Binary Random: " + binaryRandomTree.height());
        System.out.println("  Binary Best-Case: " + binaryBestCaseTree.height());
        System.out.println("  Ternary Random: " + ternaryRandomTree.height());
        System.out.println("  Ternary Best-Case: " + ternaryBestCaseTree.height());
        System.out.println("  AVL Tree: " + avlTree.height());
        System.out.println("  Optimal Height (log2(n)): " + (int) (Math.log(n) / Math.log(2)));
        System.out.println();
    }
    
    private static void testRemoval(int[] keysToRemove, BinaryTree tree) {
        long time = measureTime(() -> {
            for (int key : keysToRemove) {
                tree.delete(key);
            }
        });
        
        System.out.printf("%-25s %-20s %-15d %-15d\n",
            "BinaryTree Removal",
            formatTime(time),
            tree.height(),
            tree.size());
    }
    
    private static void testTreeSetRemoval(int[] keysToRemove, TreeSet<Integer> treeSet) {
        long time = measureTime(() -> {
            for (int key : keysToRemove) {
                treeSet.remove(key);
            }
        });
        
        System.out.printf("%-25s %-20s %-15s %-15d\n",
            "TreeSet Removal",
            formatTime(time),
            "N/A",
            treeSet.size());
    }
    
    private static void testRemovalPerformance() {
        System.out.println("=".repeat(90));
        System.out.println("KEY REMOVAL PERFORMANCE COMPARISON (Random Order)");
        System.out.println("=".repeat(90));
        System.out.println();
        
        int[] powers = {4, 5, 6, 7, 8, 9, 10, 11, 12};
        
        System.out.printf("%-12s %-25s %-20s %-15s %-15s\n",
            "Size (2^m-1)", "Removal Method", "Time", "Tree Height", "Remaining");
        System.out.println("-".repeat(90));
        
        for (int m : powers) {
            int n = (int) Math.pow(2, m) - 1;
            int removeCount = n / 2;
            
            int[] randomKeys = generateRandomKeys(n);
            int[] keysToRemove = Arrays.copyOf(randomKeys, removeCount);
            Collections.shuffle(Arrays.asList(keysToRemove));
            
            BinaryTree tree = new BinaryTree();
            for (int key : randomKeys) {
                tree.insert(key);
            }
            
            TreeSet<Integer> treeSet = new TreeSet<>();
            for (int key : randomKeys) {
                treeSet.add(key);
            }
            
            System.out.println("Size: " + n + " (2^" + m + " - 1), Removing: " + removeCount);
            
            BinaryTree treeCopy = new BinaryTree();
            for (int key : randomKeys) {
                treeCopy.insert(key);
            }
            testRemoval(keysToRemove, treeCopy);
            
            TreeSet<Integer> treeSetCopy = new TreeSet<>(treeSet);
            testTreeSetRemoval(keysToRemove, treeSetCopy);
            
            System.out.println();
        }
    }
    
    private static void detailedRemovalAnalysis() {
        System.out.println("=".repeat(90));
        System.out.println("DETAILED REMOVAL ANALYSIS (Size: 2^10 - 1 = 1023)");
        System.out.println("=".repeat(90));
        System.out.println();
        
        int m = 10;
        int n = (int) Math.pow(2, m) - 1;
        int runs = 10;
        
        System.out.println("Testing removal of " + (n / 2) + " keys from " + n + " keys:");
        System.out.println();
        
        long totalBinaryTreeTime = 0;
        long totalTreeSetTime = 0;
        
        for (int run = 0; run < runs; run++) {
            int[] randomKeys = generateRandomKeys(n);
            int removeCount = n / 2;
            int[] keysToRemove = Arrays.copyOf(randomKeys, removeCount);
            List<Integer> list = new ArrayList<>();
            for (int key : keysToRemove) {
                list.add(key);
            }
            Collections.shuffle(list);
            for (int i = 0; i < list.size(); i++) {
                keysToRemove[i] = list.get(i);
            }
            
            BinaryTree tree = new BinaryTree();
            for (int key : randomKeys) {
                tree.insert(key);
            }
            
            TreeSet<Integer> treeSet = new TreeSet<>();
            for (int key : randomKeys) {
                treeSet.add(key);
            }
            
            long binaryTreeTime = measureTime(() -> {
                for (int key : keysToRemove) {
                    tree.delete(key);
                }
            });
            
            long treeSetTime = measureTime(() -> {
                for (int key : keysToRemove) {
                    treeSet.remove(key);
                }
            });
            
            totalBinaryTreeTime += binaryTreeTime;
            totalTreeSetTime += treeSetTime;
        }
        
        long avgBinaryTreeTime = totalBinaryTreeTime / runs;
        long avgTreeSetTime = totalTreeSetTime / runs;
        
        System.out.println("Results (averaged over " + runs + " runs):");
        System.out.println("-".repeat(90));
        System.out.printf("%-25s %-20s\n", "Method", "Average Time");
        System.out.println("-".repeat(90));
        System.out.printf("%-25s %-20s\n", "BinaryTree", formatTime(avgBinaryTreeTime));
        System.out.printf("%-25s %-20s\n", "TreeSet", formatTime(avgTreeSetTime));
        System.out.println();
        
        double speedup = (double) avgBinaryTreeTime / avgTreeSetTime;
        System.out.printf("TreeSet is %.2fx %s than BinaryTree\n",
            speedup > 1 ? speedup : 1.0 / speedup,
            speedup > 1 ? "slower" : "faster");
    }
    
    private static void generateCSV() {
        try {
            java.io.FileWriter writer = new java.io.FileWriter("tree_insertion_results.csv");
            
            writer.append("Size,TreeType,Order,Time(ns),Height\n");
            
            int[] powers = {4, 5, 6, 7, 8, 9, 10, 11, 12};
            int runs = 5;
            
            for (int m : powers) {
                int n = (int) Math.pow(2, m) - 1;
                
                for (int run = 0; run < runs; run++) {
                    int[] randomKeys = generateRandomKeys(n);
                    int[] bestCaseKeys = generateBestCaseOrder(Arrays.copyOf(randomKeys, randomKeys.length));
                    
                    BinaryTree binaryRandomTree = new BinaryTree();
                    long binaryRandomTime = measureTime(() -> {
                        for (int key : randomKeys) {
                            binaryRandomTree.insert(key);
                        }
                    });
                    writer.append(n + ",Binary,Random," + binaryRandomTime + "," + binaryRandomTree.height() + "\n");
                    
                    BinaryTree binaryBestCaseTree = new BinaryTree();
                    long binaryBestCaseTime = measureTime(() -> {
                        for (int key : bestCaseKeys) {
                            binaryBestCaseTree.insert(key);
                        }
                    });
                    writer.append(n + ",Binary,BestCase," + binaryBestCaseTime + "," + binaryBestCaseTree.height() + "\n");
                    
                    TreeSet<Integer> treeSet = new TreeSet<>();
                    long treeSetTime = measureTime(() -> {
                        for (int key : randomKeys) {
                            treeSet.add(key);
                        }
                    });
                    writer.append(n + ",TreeSet,Random," + treeSetTime + ",N/A\n");
                    
                    TernaryTree ternaryRandomTree = new TernaryTree();
                    long ternaryRandomTime = measureTime(() -> {
                        for (int key : randomKeys) {
                            ternaryRandomTree.insert(key);
                        }
                    });
                    writer.append(n + ",Ternary,Random," + ternaryRandomTime + "," + ternaryRandomTree.height() + "\n");
                    
                    TernaryTree ternaryBestCaseTree = new TernaryTree();
                    long ternaryBestCaseTime = measureTime(() -> {
                        for (int key : bestCaseKeys) {
                            ternaryBestCaseTree.insert(key);
                        }
                    });
                    writer.append(n + ",Ternary,BestCase," + ternaryBestCaseTime + "," + ternaryBestCaseTree.height() + "\n");
                    
                    AVLTree avlTree = new AVLTree();
                    long avlTime = measureTime(() -> {
                        for (int key : randomKeys) {
                            avlTree.insert(key);
                        }
                    });
                    writer.append(n + ",AVL,Random," + avlTime + "," + avlTree.height() + "\n");
                }
            }
            
            writer.close();
            System.out.println("CSV data saved to 'tree_insertion_results.csv' for graphing");
        } catch (java.io.IOException e) {
            System.err.println("Error writing CSV file: " + e.getMessage());
        }
    }
    
    private static void generateRemovalCSV() {
        try {
            java.io.FileWriter writer = new java.io.FileWriter("tree_removal_results.csv");
            
            writer.append("Size,Method,Time(ns),Remaining\n");
            
            int[] powers = {4, 5, 6, 7, 8, 9, 10, 11, 12};
            int runs = 5;
            
            for (int m : powers) {
                int n = (int) Math.pow(2, m) - 1;
                int removeCount = n / 2;
                
                for (int run = 0; run < runs; run++) {
                    int[] randomKeys = generateRandomKeys(n);
                    int[] keysToRemove = Arrays.copyOf(randomKeys, removeCount);
                    List<Integer> list = new ArrayList<>();
                    for (int key : keysToRemove) {
                        list.add(key);
                    }
                    Collections.shuffle(list);
                    for (int i = 0; i < list.size(); i++) {
                        keysToRemove[i] = list.get(i);
                    }
                    
                    BinaryTree tree = new BinaryTree();
                    for (int key : randomKeys) {
                        tree.insert(key);
                    }
                    
                    long binaryTreeTime = measureTime(() -> {
                        for (int key : keysToRemove) {
                            tree.delete(key);
                        }
                    });
                    writer.append(n + ",BinaryTree," + binaryTreeTime + "," + tree.size() + "\n");
                    
                    TreeSet<Integer> treeSet = new TreeSet<>();
                    for (int key : randomKeys) {
                        treeSet.add(key);
                    }
                    
                    long treeSetTime = measureTime(() -> {
                        for (int key : keysToRemove) {
                            treeSet.remove(key);
                        }
                    });
                    writer.append(n + ",TreeSet," + treeSetTime + "," + treeSet.size() + "\n");
                }
            }
            
            writer.close();
            System.out.println("CSV data saved to 'tree_removal_results.csv' for graphing");
        } catch (java.io.IOException e) {
            System.err.println("Error writing CSV file: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        testDifferentSizes();
        detailedAnalysis();
        testRemovalPerformance();
        detailedRemovalAnalysis();
        generateCSV();
        generateRemovalCSV();
        
        System.out.println();
        System.out.println("=".repeat(90));
        System.out.println("CONCLUSIONS");
        System.out.println("=".repeat(90));
        System.out.println("INSERTION:");
        System.out.println("a) Binary Tree Random: Unbalanced, height varies");
        System.out.println("b) Binary Tree Best-Case: Perfectly balanced, optimal height");
        System.out.println("c) TreeSet: Optimized library implementation");
        System.out.println("d) Ternary Tree Random: 2 keys per node, 3 children");
        System.out.println("e) Ternary Tree Best-Case: Balanced ternary structure");
        System.out.println("f) AVL Tree: Self-balancing, maintains O(log n) height");
        System.out.println();
        System.out.println("KEY FINDINGS:");
        System.out.println("1. AVL tree maintains balanced structure automatically");
        System.out.println("2. Ternary trees can have different height characteristics");
        System.out.println("3. Best-case insertion order produces optimal heights");
        System.out.println("4. Tree height directly correlates with performance");
        System.out.println("5. Self-balancing trees (AVL) guarantee O(log n) operations");
        System.out.println();
        System.out.println("REMOVAL:");
        System.out.println("1. Removal time depends on tree structure (height)");
        System.out.println("2. TreeSet removal is generally faster due to optimized implementation");
        System.out.println("3. Unbalanced trees have worse removal performance");
        System.out.println("4. Removal complexity is O(h) where h is tree height");
        System.out.println("=".repeat(90));
    }
}
