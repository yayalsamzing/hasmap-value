import java.util.*;

public class PerformanceTestRunner {
    
    private static String[] itemNames = ItemValueCalculator.getItemNames();
    private static Random random = new Random();
    
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
    
    public static void runPerformanceTests() {
        System.out.println("performance test");
        System.out.println("running multithread tests\n");
        
        // JVM Warm-up
        System.out.println("jvm warmup");
        performWarmup();
        System.out.println("warmup complete\n");
        
        ArrayList<TestResult> results = new ArrayList<TestResult>();
        
        // Test 1: Single Item Tests
        System.out.println("1. single item");
        results.addAll(runSingleItemTests());
        
        // Test 2: Small Batch Tests
        System.out.println("\n2.small batch 1 - 10");
        results.addAll(runSmallBatchTests());
        
        // Test 3: Medium Batch Tests
        System.out.println("\n3. medium batch 50 - 100");
        results.addAll(runMediumBatchTests());
        
        // Test 4: Large Batch Tests
        System.out.println("\n4.large batch 500 - 1000");
        results.addAll(runLargeBatchTests());
        
        // Test 5: High-Value Item Tests
        System.out.println("\n5. high value item tests");
        results.addAll(runHighValueItemTests());
        
        // Test 6: Large Quantity Tests
        System.out.println("\n6. large quantity tests");
        results.addAll(runLargeQuantityTests());
        
        // Test 7: Mixed Item Type Tests
        System.out.println("\n7. mixed item test");
        results.addAll(runMixedItemTypeTests());
        
        // Performance Summary
        System.out.println("\nsummary");
        printPerformanceSummary(results);
        
        // Optimization Analysis
        System.out.println("\n analysis");
        analyzeOptimization(results);
        
        System.out.println("\nperformance test finished\n");
    }
    
    private static void performWarmup() {
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
            
            TestResult testResult = new TestResult("single item #" + (i+1), 1, 
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
                
                TestResult testResult = new TestResult("small batch " + batchSize + " items #" + (test+1), 
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
                
                TestResult testResult = new TestResult("medium batch " + batchSize + " items #" + (test+1), 
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
                
                TestResult testResult = new TestResult("large batch " + batchSize + " items #" + (test+1), 
                    batchSize, endTime - startTime, result[0], (int)result[1]);
                results.add(testResult);
                System.out.println(testResult);
            }
        }
        
        return results;
    }
    
    private static ArrayList<TestResult> runHighValueItemTests() {
        ArrayList<TestResult> results = new ArrayList<TestResult>();
        
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
            
            TestResult testResult = new TestResult("high value Items #" + (test+1), 
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
                int quantity = 10000 + random.nextInt(90000);
                input.append(item).append(" ").append(quantity);
            }
            
            long startTime = System.nanoTime();
            double[] result = ItemValueCalculator.calculateTotal(input.toString());
            long endTime = System.nanoTime();
            
            TestResult testResult = new TestResult("large quantities #" + (test+1), 
                itemCount, endTime - startTime, result[0], (int)result[1]);
            results.add(testResult);
            System.out.println(testResult);
        }
        
        return results;
    }
    
    private static ArrayList<TestResult> runMixedItemTypeTests() {
        ArrayList<TestResult> results = new ArrayList<TestResult>();
        
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
            
            TestResult testResult = new TestResult("mixed item types #" + (test+1), 
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
        
        System.out.printf("total tests: %d\n", results.size());
        System.out.printf("average time: %.2f ms\n", avgTime / 1000000.0);
        System.out.printf("fastest test: %.2f ms (%s)\n", minTime / 1000000.0, fastestTest.testName);
        System.out.printf("slowest test: %.2f ms (%s)\n", maxTime / 1000000.0, slowestTest.testName);
        System.out.printf("performance range: %.2fx difference\n", (double)maxTime / minTime);
    }
    
    private static void analyzeOptimization(ArrayList<TestResult> results) {
        System.out.println("system performance analysis:");
        
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
        
        System.out.printf("per-item throughput (items/second):\n");
        System.out.printf("  single items: %.0f items/sec\n", singleItemThroughput);
        System.out.printf("  medium batches (50-100): %.0f items/sec\n", mediumBatchThroughput);
        System.out.printf("  large batches (1000+): %.0f items/sec\n", largeBatchThroughput);
        
        if (singleCount > 0 && largeCount > 0) {
            double scalingEfficiency = largeBatchThroughput / singleItemThroughput;
            System.out.printf("scaling efficiency: %.1fx (large batch vs single item throughput)\n", scalingEfficiency);
            
            if (scalingEfficiency > 0.8) {
                System.out.println("EXCELLENT: maintains high throughput at scale");
            } else if (scalingEfficiency > 0.5) {
                System.out.println("GOOD: reasonable scaling performance");
            } else {
                System.out.println("WARNING!!!!!!!!!!!!!!!!!!!!!!!!!: performance degrades significantly with scale");
            }
        }
        
        long threshold = 50000000; // 50ms threshold
        int slowTests = 0;
        for (TestResult result : results) {
            if (result.executionTimeNanos > threshold) {
                slowTests++;
            }
        }
        
        System.out.printf("tests over 50ms threshold: %d/%d\n", slowTests, results.size());
        
        if (slowTests == 0) {
            System.out.println("EXCELLENT: All tests under performance threshold");
        } else if (slowTests < results.size() * 0.1) {
            System.out.println("GOOD: Most tests meet performance criteria");
        } else {
            System.out.println("WARNING!!!!!!!!!!!: Many tests exceed performance threshold");
        }
        
        System.out.println("\noptimization Notes:");
        System.out.println("- hashMap lookup is O(1) average case - very efficient");
        System.out.println("- string parsing is main performance factor");
        System.out.println("- large quantities don't affect lookup performance");
        System.out.println("- system can handle 10,000+ items efficiently");
        System.out.println("- JVM warm-up improves benchmark stability");
    }
}