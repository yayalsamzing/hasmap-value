import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Item Value Calculator");
        System.out.println("Examples: 'kelp 64' or 'diamond 5, iron_ingot 32'");
        System.out.println("Type 'exit' to quit");
        System.out.println();
        
        while (true) {
            System.out.print("Enter items: ");
            String input = scanner.nextLine().toLowerCase().trim();
            
            if (input.equals("exit")) {
                System.out.println("Goodbye!");
                break;
            }
            
            calculateTotal(input);
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
                    System.out.print("Value: ");
                    System.out.print(value+"\n");
                    total += value;
                    foundAny = true;
                } else {
                    System.out.println("Item not found");
                }
            } else if (parts.length == 2) {
                // Item name and quantity
                String itemName = parts[0];
                try {
                    int quantity = Integer.parseInt(parts[1]);
                    if (ItemValueCalculator.hasItem(itemName)) {
                        double value = ItemValueCalculator.getItemValue(itemName);
                        double subtotal = value * quantity;
                        System.out.print("Value: " + subtotal+"\n");
                        total += subtotal;
                        foundAny = true;
                    } else {
                        System.out.println("Item not found");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid quantity");
                }
            } else {
                System.out.println("Invalid format");
            }
        }
            
        if (foundAny) {
            if (items.length > 1) {
                System.out.println("Total: " + total);
            }
            // Calculate tokens (1000 points = 1 token)
            int tokens = (int)(total / 1000);
            System.out.println("Tokens: " + tokens);
        }
    }
}
