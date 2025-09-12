import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("item value data structure hashmap and performance test");
        System.out.println("examples: 'kelp 64' or 'diamond 5, iron_ingot 32'");
        System.out.println("perf = run performance test");
        System.out.println();
        
        while (true) {
            System.out.print("enter items or command: ");
            String input = scanner.nextLine().toLowerCase().trim();
            
            if (input.equals("exit")) {
                System.out.println("lalalal!");
                break;
            } else if (input.equals("perf") || input.equals("performance")) {
                PerformanceTestRunner.runPerformanceTests();
            } else {
                calculateTotal(input);
            }
        }
        
        scanner.close();
    }
    
    private static void calculateTotal(String input) {
        String[] items = input.split(",");
        double total = 0.0;
        boolean foundAny = false;
        
        for (String item : items) {
            item = item.trim();
            
            // Split by space to get item name and quantity
            String[] parts = item.split("\\s+");
            
            if (parts.length == 1) {
                //item name, quantity = 1
                String itemName = parts[0];
                if (ItemValueCalculator.hasItem(itemName)) {
                    double value = ItemValueCalculator.getItemValue(itemName);
                    System.out.print("value: ");
                    System.out.print(value+"\n");
                    total += value;
                    foundAny = true;
                } else {
                    System.out.println("item not found");
                }
            } else if (parts.length == 2) {
                // Item name and quantity
                String itemName = parts[0];
                try {
                    int quantity = Integer.parseInt(parts[1]);
                    if (ItemValueCalculator.hasItem(itemName)) {
                        double value = ItemValueCalculator.getItemValue(itemName);
                        double subtotal = value * quantity;
                        System.out.print("value: " + subtotal+"\n");
                        total += subtotal;
                        foundAny = true;
                    } else {
                        System.out.println("item not found");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("invalid quantity");
                }
            } else {
                System.out.println("invalid format");
            }
        }
            
        if (foundAny) {
            if (items.length > 1) {
                System.out.println("total: " + total);
            }
            // Calculate tokens (1000 points = 1 token)
            int tokens = (int)(total / 1000);
            System.out.println("tokens: " + tokens);
        }
    }
}