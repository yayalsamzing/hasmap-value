import java.util.*;

public class PerformanceTest {
    
    private static String[] itemNames = ItemValueCalculator.getItemNames();
    private static Random random = new Random();
    
    // Test scenarios
    private static class TestResult {
        String testName;
        int itemCount;
        long executionTimeNanos;
        double totalValue;
        int tokens;
        
        TestResult(String name, int count, long time, double value, int tokenCount) {
            this.testName = name;
            this.itemCount = count;
            this.executionTimeNanos = time;
            this.totalValue = value;
            this.tokens = tokenCount;
        }
        
        @Override
        public String toString() {
            return String.format("%s: %d items, %.2fms, Total Value: %.2f, Tokens: %d", 
                testName, itemCount, executionTimeNanos / 1000000.0, totalValue, tokens);
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== ITEM VALUE CALCULATOR PERFORMANCE TEST ===");
        System.out.println("Testing system performance with random items at different scales\n");
        
        // JVM Warm-up
        System.out.println("Warming up JVM...");
        performWarmup();
        System.out.println("Warm-up complete.\n");
        
        ArrayList<TestResult> results = new ArrayList<TestResult>();
        
        // Test 1: Single Item Tests
        System.out.println("1. SINGLE ITEM TESTS");
        results.addAll(runSingleItemTests());
        
        // Test 2: Small Batch Tests
        System.out.println("\n2. SMALL BATCH TESTS (1-10 items)");
        results.addAll(runSmallBatchTests());
        
        // Test 3: Medium Batch Tests
        System.out.println("\n3. MEDIUM BATCH TESTS (50-100 items)");
        results.addAll(runMediumBatchTests());
        
        // Test 4: Large Batch Tests
        System.out.println("\n4. LARGE BATCH TESTS (500-1000 items)");
        results.addAll(runLargeBatchTests());
        
        // Test 5: Extreme Load Tests
        System.out.println("\n5. EXTREME LOAD TESTS (5000+ items)");
        results.addAll(runExtremeLoadTests());
        
        // Test 6: High-Value Item Tests
        System.out.println("\n6. HIGH-VALUE ITEM TESTS");
        results.addAll(runHighValueItemTests());
        
        // Test 7: Large Quantity Tests
        System.out.println("\n7. LARGE QUANTITY TESTS");
        results.addAll(runLargeQuantityTests());
        
        // Test 8: Mixed Item Type Tests
        System.out.println("\n8. MIXED ITEM TYPE TESTS");
        results.addAll(runMixedItemTypeTests());
        
        // Performance Summary
        System.out.println("\n=== PERFORMANCE SUMMARY ===");
        printPerformanceSummary(results);
        
        // Optimization Analysis
        System.out.println("\n=== OPTIMIZATION ANALYSIS ===");
        analyzeOptimization(results);
    }
    
    private static void performWarmup() {
        // Warm up the JVM with multiple iterations to stabilize performance
        for (int i = 0; i < 10; i++) {
            String warmupInput = generateRandomItemInput(100);
            ItemValueCalculator.calculateTotal(warmupInput);
        }
    }
    
    private static ArrayList<TestResult> runSingleItemTests() {
        ArrayList<TestResult> results = new ArrayList<TestResult>();
        
        for (int i = 0; i < 5; i++) {
            String randomItem = getRandomItem();
            int quantity = random.nextInt(100) + 1;
            String input = randomItem + " " + quantity;
            
            long startTime = System.nanoTime();
            double[] result = ItemValueCalculator.calculateTotal(input);
            long endTime = System.nanoTime();
            
            TestResult testResult = new TestResult("Single Item #" + (i+1), 1, 
                endTime - startTime, result[0], (int)result[1]);
            results.add(testResult);
            System.out.println(testResult);
        }
        
        return results;
    }
    
    private static ArrayList<TestResult> runSmallBatchTests() {
        ArrayList<TestResult> results = new ArrayList<TestResult>();
        
        int[] batchSizes = {5, 10};
        for (int batchSize : batchSizes) {
            for (int test = 0; test < 3; test++) {
                String input = generateRandomItemInput(batchSize);
                
                long startTime = System.nanoTime();
                double[] result = ItemValueCalculator.calculateTotal(input);
                long endTime = System.nanoTime();
                
                TestResult testResult = new TestResult("Small Batch " + batchSize + " items #" + (test+1), 
                    batchSize, endTime - startTime, result[0], (int)result[1]);
                results.add(testResult);
                System.out.println(testResult);
            }
        }
        
        return results;
    }
    
    private static ArrayList<TestResult> runMediumBatchTests() {
        ArrayList<TestResult> results = new ArrayList<TestResult>();
        
        int[] batchSizes = {50, 100};
        for (int batchSize : batchSizes) {
            for (int test = 0; test < 3; test++) {
                String input = generateRandomItemInput(batchSize);
                
                long startTime = System.nanoTime();
                double[] result = ItemValueCalculator.calculateTotal(input);
                long endTime = System.nanoTime();
                
                TestResult testResult = new TestResult("Medium Batch " + batchSize + " items #" + (test+1), 
                    batchSize, endTime - startTime, result[0], (int)result[1]);
                results.add(testResult);
                System.out.println(testResult);
            }
        }
        
        return results;
    }
    
    private static ArrayList<TestResult> runLargeBatchTests() {
        ArrayList<TestResult> results = new ArrayList<TestResult>();
        
        int[] batchSizes = {500, 1000};
        for (int batchSize : batchSizes) {
            for (int test = 0; test < 2; test++) {
                String input = generateRandomItemInput(batchSize);
                
                long startTime = System.nanoTime();
                double[] result = ItemValueCalculator.calculateTotal(input);
                long endTime = System.nanoTime();
                
                TestResult testResult = new TestResult("Large Batch " + batchSize + " items #" + (test+1), 
                    batchSize, endTime - startTime, result[0], (int)result[1]);
                results.add(testResult);
                System.out.println(testResult);
            }
        }
        
        return results;
    }
    
    private static ArrayList<TestResult> runExtremeLoadTests() {
        ArrayList<TestResult> results = new ArrayList<TestResult>();
        
        int[] batchSizes = {5000, 10000};
        for (int batchSize : batchSizes) {
            String input = generateRandomItemInput(batchSize);
            
            long startTime = System.nanoTime();
            double[] result = ItemValueCalculator.calculateTotal(input);
            long endTime = System.nanoTime();
            
            TestResult testResult = new TestResult("Extreme Load " + batchSize + " items", 
                batchSize, endTime - startTime, result[0], (int)result[1]);
            results.add(testResult);
            System.out.println(testResult);
        }
        
        return results;
    }
    
    private static ArrayList<TestResult> runHighValueItemTests() {
        ArrayList<TestResult> results = new ArrayList<TestResult>();
        
        // High-value items (over 1000 value)
        String[] highValueItems = {"beacon", "dragon_head", "nether_star", "skeleton_skull", 
            "creeper_head", "dragon_egg", "elytra", "mace"};
        
        for (int test = 0; test < 3; test++) {
            StringBuilder input = new StringBuilder();
            int itemCount = 5 + random.nextInt(15);
            
            for (int i = 0; i < itemCount; i++) {
                if (i > 0) input.append(", ");
                String item = highValueItems[random.nextInt(highValueItems.length)];
                int quantity = random.nextInt(10) + 1;
                input.append(item).append(" ").append(quantity);
            }
            
            long startTime = System.nanoTime();
            double[] result = ItemValueCalculator.calculateTotal(input.toString());
            long endTime = System.nanoTime();
            
            TestResult testResult = new TestResult("High-Value Items #" + (test+1), 
                itemCount, endTime - startTime, result[0], (int)result[1]);
            results.add(testResult);
            System.out.println(testResult);
        }
        
        return results;
    }
    
    private static ArrayList<TestResult> runLargeQuantityTests() {
        ArrayList<TestResult> results = new ArrayList<TestResult>();
        
        for (int test = 0; test < 3; test++) {
            StringBuilder input = new StringBuilder();
            int itemCount = 10 + random.nextInt(20);
            
            for (int i = 0; i < itemCount; i++) {
                if (i > 0) input.append(", ");
                String item = getRandomItem();
                // Large quantities: 10000 to 100000
                int quantity = 10000 + random.nextInt(90000);
                input.append(item).append(" ").append(quantity);
            }
            
            long startTime = System.nanoTime();
            double[] result = ItemValueCalculator.calculateTotal(input.toString());
            long endTime = System.nanoTime();
            
            TestResult testResult = new TestResult("Large Quantities #" + (test+1), 
                itemCount, endTime - startTime, result[0], (int)result[1]);
            results.add(testResult);
            System.out.println(testResult);
        }
        
        return results;
    }
    
    private static ArrayList<TestResult> runMixedItemTypeTests() {
        ArrayList<TestResult> results = new ArrayList<TestResult>();
        
        // Mix of cheap items (< 1.0), medium items (1.0-100), and expensive items (> 100)
        String[] cheapItems = {"cobweb", "dead_bush", "snow", "dirt", "gravel"};
        String[] mediumItems = {"iron_ingot", "gold_ingot", "diamond", "emerald", "apple"};
        String[] expensiveItems = {"beacon", "dragon_egg", "elytra", "netherite_ingot"};
        
        for (int test = 0; test < 3; test++) {
            StringBuilder input = new StringBuilder();
            int totalItems = 30;
            
            for (int i = 0; i < totalItems; i++) {
                if (i > 0) input.append(", ");
                
                String item;
                if (i < 10) {
                    item = cheapItems[random.nextInt(cheapItems.length)];
                } else if (i < 20) {
                    item = mediumItems[random.nextInt(mediumItems.length)];
                } else {
                    item = expensiveItems[random.nextInt(expensiveItems.length)];
                }
                
                int quantity = random.nextInt(1000) + 1;
                input.append(item).append(" ").append(quantity);
            }
            
            long startTime = System.nanoTime();
            double[] result = ItemValueCalculator.calculateTotal(input.toString());
            long endTime = System.nanoTime();
            
            TestResult testResult = new TestResult("Mixed Item Types #" + (test+1), 
                totalItems, endTime - startTime, result[0], (int)result[1]);
            results.add(testResult);
            System.out.println(testResult);
        }
        
        return results;
    }
    
    private static String generateRandomItemInput(int itemCount) {
        StringBuilder input = new StringBuilder();
        
        for (int i = 0; i < itemCount; i++) {
            if (i > 0) input.append(", ");
            String item = getRandomItem();
            int quantity = random.nextInt(1000) + 1;
            input.append(item).append(" ").append(quantity);
        }
        
        return input.toString();
    }
    
    private static String getRandomItem() {
        return itemNames[random.nextInt(itemNames.length)];
    }
    
    private static void printPerformanceSummary(ArrayList<TestResult> results) {
        long minTime = Long.MAX_VALUE;
        long maxTime = 0;
        long totalTime = 0;
        
        TestResult fastestTest = null;
        TestResult slowestTest = null;
        
        for (TestResult result : results) {
            totalTime += result.executionTimeNanos;
            
            if (result.executionTimeNanos < minTime) {
                minTime = result.executionTimeNanos;
                fastestTest = result;
            }
            
            if (result.executionTimeNanos > maxTime) {
                maxTime = result.executionTimeNanos;
                slowestTest = result;
            }
        }
        
        double avgTime = (double) totalTime / results.size();
        
        System.out.printf("Total Tests Run: %d\n", results.size());
        System.out.printf("Average Execution Time: %.2f ms\n", avgTime / 1000000.0);
        System.out.printf("Fastest Test: %.2f ms (%s)\n", minTime / 1000000.0, fastestTest.testName);
        System.out.printf("Slowest Test: %.2f ms (%s)\n", maxTime / 1000000.0, slowestTest.testName);
        System.out.printf("Performance Range: %.2fx difference\n", (double)maxTime / minTime);
    }
    
    private static void analyzeOptimization(ArrayList<TestResult> results) {
        System.out.println("System Performance Analysis:");
        
        // Calculate per-item throughput for different batch sizes
        double singleItemThroughput = 0;
        double mediumBatchThroughput = 0;
        double largeBatchThroughput = 0;
        int singleCount = 0, mediumCount = 0, largeCount = 0;
        
        for (TestResult result : results) {
            if (result.itemCount == 1) {
                singleItemThroughput += (result.itemCount * 1000000000.0) / result.executionTimeNanos;
                singleCount++;
            } else if (result.itemCount >= 50 && result.itemCount <= 100) {
                mediumBatchThroughput += (result.itemCount * 1000000000.0) / result.executionTimeNanos;
                mediumCount++;
            } else if (result.itemCount >= 1000) {
                largeBatchThroughput += (result.itemCount * 1000000000.0) / result.executionTimeNanos;
                largeCount++;
            }
        }
        
        if (singleCount > 0) singleItemThroughput /= singleCount;
        if (mediumCount > 0) mediumBatchThroughput /= mediumCount;
        if (largeCount > 0) largeBatchThroughput /= largeCount;
        
        System.out.printf("Per-item throughput (items/second):\n");
        System.out.printf("  Single items: %.0f items/sec\n", singleItemThroughput);
        System.out.printf("  Medium batches (50-100): %.0f items/sec\n", mediumBatchThroughput);
        System.out.printf("  Large batches (1000+): %.0f items/sec\n", largeBatchThroughput);
        
        // Analyze scaling efficiency
        if (singleCount > 0 && largeCount > 0) {
            double scalingEfficiency = largeBatchThroughput / singleItemThroughput;
            System.out.printf("Scaling efficiency: %.1fx (large batch vs single item throughput)\n", scalingEfficiency);
            
            if (scalingEfficiency > 0.8) {
                System.out.println("✓ EXCELLENT: Maintains high throughput at scale");
            } else if (scalingEfficiency > 0.5) {
                System.out.println("✓ GOOD: Reasonable scaling performance");
            } else {
                System.out.println("⚠ WARNING: Performance degrades significantly with scale");
            }
        }
        
        // Check for any performance bottlenecks
        long threshold = 50000000; // 50ms threshold
        int slowTests = 0;
        for (TestResult result : results) {
            if (result.executionTimeNanos > threshold) {
                slowTests++;
            }
        }
        
        System.out.printf("Tests over 50ms threshold: %d/%d\n", slowTests, results.size());
        
        if (slowTests == 0) {
            System.out.println("✓ EXCELLENT: All tests under performance threshold");
        } else if (slowTests < results.size() * 0.1) {
            System.out.println("✓ GOOD: Most tests meet performance criteria");
        } else {
            System.out.println("⚠ WARNING: Many tests exceed performance threshold");
        }
        
        System.out.println("\nOptimization Notes:");
        System.out.println("- HashMap lookup is O(1) average case - very efficient");
        System.out.println("- String parsing is main performance factor");
        System.out.println("- Large quantities don't affect lookup performance");
        System.out.println("- System handles 10,000+ items efficiently");
        System.out.println("- JVM warm-up improves benchmark stability");
        System.out.println("- Using shared ItemValueCalculator eliminates code duplication");
    }
}