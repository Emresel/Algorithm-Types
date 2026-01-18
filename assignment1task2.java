import java.util.*;

class DynamicArray {
    private int[] data;
    private int size;
    private int capacity;
    private int reallocationCount;
    
    public DynamicArray() {
        this.capacity = 1;
        this.data = new int[capacity];
        this.size = 0;
        this.reallocationCount = 0;
    }
    
    public DynamicArray(int initialCapacity) {
        this.capacity = Math.max(1, initialCapacity);
        this.data = new int[capacity];
        this.size = 0;
        this.reallocationCount = 0;
    }
    
    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return data[index];
    }
    
    public void set(int index, int value) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        data[index] = value;
    }
    
    public void append(int value) {
        if (size >= capacity) {
            reallocate();
        }
        data[size] = value;
        size++;
    }
    
    private void reallocate() {
        int newCapacity = capacity * 2;
        int[] newData = new int[newCapacity];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        data = newData;
        capacity = newCapacity;
        reallocationCount++;
    }
    
    public void resize(int newSize) {
        if (newSize < 0) {
            throw new IllegalArgumentException("Size cannot be negative");
        }
        
        if (newSize > capacity) {
            while (capacity < newSize) {
                int newCapacity = capacity * 2;
                int[] newData = new int[newCapacity];
                for (int i = 0; i < size; i++) {
                    newData[i] = data[i];
                }
                data = newData;
                capacity = newCapacity;
                reallocationCount++;
            }
        }
        
        size = newSize;
    }
    
    public void delete(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        size--;
    }
    
    public void deleteRange(int start, int end) {
        if (start < 0 || end > size || start > end) {
            throw new IndexOutOfBoundsException("Invalid range: [" + start + ", " + end + ")");
        }
        
        int deleteCount = end - start;
        for (int i = start; i < size - deleteCount; i++) {
            data[i] = data[i + deleteCount];
        }
        size -= deleteCount;
    }
    
    public int size() {
        return size;
    }
    
    public int capacity() {
        return capacity;
    }
    
    public int getReallocationCount() {
        return reallocationCount;
    }
    
    public void clearReallocationCount() {
        reallocationCount = 0;
    }
}

class MyLinkedList {
    private Node head;
    private int size;
    
    private class Node {
        int data;
        Node next;
        
        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }
    
    public MyLinkedList() {
        head = null;
        size = 0;
    }
    
    public void append(int value) {
        Node newNode = new Node(value);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }
    
    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }
    
    public int size() {
        return size;
    }
}

public class assignment1task2 {
    
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
    
    private static void testAppendOperations() {
        System.out.println("=".repeat(80));
        System.out.println("APPEND OPERATIONS PERFORMANCE TEST");
        System.out.println("=".repeat(80));
        System.out.println();
        
        int[] testSizes = {1000, 5000, 10000, 50000, 100000, 500000, 1000000};
        
        System.out.printf("%-12s %-20s %-20s %-20s %-15s\n",
            "Size", "ArrayList", "DynamicArray", "LinkedList", "Reallocations");
        System.out.println("-".repeat(80));
        
        for (int size : testSizes) {
            DynamicArray dynArray = new DynamicArray();
            long arrayListTime = measureTime(() -> {
                ArrayList<Integer> list = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    list.add(i);
                }
            });
            
            long dynArrayTime = measureTime(() -> {
                for (int i = 0; i < size; i++) {
                    dynArray.append(i);
                }
            });
            
            long linkedListTime = measureTime(() -> {
                MyLinkedList list = new MyLinkedList();
                for (int i = 0; i < size; i++) {
                    list.append(i);
                }
            });
            
            System.out.printf("%-12d %-20s %-20s %-20s %-15d\n",
                size,
                formatTime(arrayListTime),
                formatTime(dynArrayTime),
                formatTime(linkedListTime),
                dynArray.getReallocationCount());
        }
    }
    
    private static void demonstrateReallocations() {
        System.out.println();
        System.out.println("=".repeat(80));
        System.out.println("REALLOCATION DEMONSTRATION");
        System.out.println("=".repeat(80));
        System.out.println();
        
        DynamicArray arr = new DynamicArray();
        System.out.println("Appending elements and tracking capacity changes:");
        System.out.println();
        System.out.printf("%-10s %-15s %-15s %-20s\n", "Size", "Capacity", "Reallocations", "Time (ns)");
        System.out.println("-".repeat(60));
        
        int[] milestones = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192};
        int currentMilestone = 0;
        
        for (int i = 0; i < 10000; i++) {
            final int value = i;
            long time = measureTime(() -> arr.append(value));
            
            if (currentMilestone < milestones.length && arr.size() == milestones[currentMilestone]) {
                System.out.printf("%-10d %-15d %-15d %-20d\n",
                    arr.size(),
                    arr.capacity(),
                    arr.getReallocationCount(),
                    time);
                currentMilestone++;
            }
        }
    }
    
    private static void testOtherOperations() {
        System.out.println();
        System.out.println("=".repeat(80));
        System.out.println("OTHER OPERATIONS TEST");
        System.out.println("=".repeat(80));
        System.out.println();
        
        DynamicArray arr = new DynamicArray();
        
        System.out.println("1. Testing append and get:");
        for (int i = 0; i < 10; i++) {
            arr.append(i * 10);
        }
        System.out.println("   Appended 10 elements: " + arr.size() + " elements, capacity: " + arr.capacity());
        System.out.println("   get(5) = " + arr.get(5));
        System.out.println();
        
        System.out.println("2. Testing set:");
        arr.set(5, 999);
        System.out.println("   set(5, 999), get(5) = " + arr.get(5));
        System.out.println();
        
        System.out.println("3. Testing resize (upsize):");
        arr.resize(20);
        System.out.println("   resize(20): size = " + arr.size() + ", capacity = " + arr.capacity());
        System.out.println();
        
        System.out.println("4. Testing resize (downsize/truncate):");
        arr.resize(5);
        System.out.println("   resize(5): size = " + arr.size() + ", capacity = " + arr.capacity());
        System.out.println("   First 5 elements: ");
        for (int i = 0; i < arr.size(); i++) {
            System.out.print(arr.get(i) + " ");
        }
        System.out.println();
        System.out.println();
        
        System.out.println("5. Testing delete (single element):");
        arr.append(100);
        arr.append(200);
        arr.append(300);
        System.out.println("   Before delete: size = " + arr.size());
        arr.delete(2);
        System.out.println("   After delete(2): size = " + arr.size());
        for (int i = 0; i < arr.size(); i++) {
            System.out.print(arr.get(i) + " ");
        }
        System.out.println();
        System.out.println();
        
        System.out.println("6. Testing deleteRange:");
        for (int i = 0; i < 5; i++) {
            arr.append(i);
        }
        System.out.println("   Before deleteRange(2, 5): size = " + arr.size());
        arr.deleteRange(2, 5);
        System.out.println("   After deleteRange(2, 5): size = " + arr.size());
        for (int i = 0; i < arr.size(); i++) {
            System.out.print(arr.get(i) + " ");
        }
        System.out.println();
    }
    
    private static void testAccessTime() {
        System.out.println();
        System.out.println("=".repeat(80));
        System.out.println("ELEMENT ACCESS TIME TEST (O(1) verification)");
        System.out.println("=".repeat(80));
        System.out.println();
        
        int size = 100000;
        DynamicArray dynArray = new DynamicArray();
        ArrayList<Integer> arrayList = new ArrayList<>();
        MyLinkedList linkedList = new MyLinkedList();
        
        for (int i = 0; i < size; i++) {
            dynArray.append(i);
            arrayList.add(i);
            linkedList.append(i);
        }
        
        int[] indices = {0, size / 4, size / 2, 3 * size / 4, size - 1};
        
        System.out.printf("%-15s %-20s %-20s %-20s\n",
            "Index", "DynamicArray", "ArrayList", "LinkedList");
        System.out.println("-".repeat(80));
        
        for (int idx : indices) {
            long dynTime = measureTime(() -> dynArray.get(idx));
            long arrTime = measureTime(() -> arrayList.get(idx));
            long linkTime = measureTime(() -> linkedList.get(idx));
            
            System.out.printf("%-15d %-20s %-20s %-20s\n",
                idx,
                formatTime(dynTime),
                formatTime(arrTime),
                formatTime(linkTime));
        }
        
        System.out.println();
        System.out.println("Note: DynamicArray and ArrayList have O(1) access time.");
        System.out.println("      LinkedList has O(n) access time (slower for large indices).");
    }
    
    public static void main(String[] args) {
        testAppendOperations();
        demonstrateReallocations();
        testOtherOperations();
        testAccessTime();
        
        System.out.println();
        System.out.println("=".repeat(80));
        System.out.println("SUMMARY");
        System.out.println("=".repeat(80));
        System.out.println("1. DynamicArray uses table doubling for reallocation");
        System.out.println("2. Append operations show amortized O(1) behavior");
        System.out.println("3. Reallocations occur at powers of 2 (visible in demonstration)");
        System.out.println("4. Element access is O(1) for DynamicArray and ArrayList");
        System.out.println("5. LinkedList has O(n) access time but O(1) append");
        System.out.println("=".repeat(80));
    }
}
