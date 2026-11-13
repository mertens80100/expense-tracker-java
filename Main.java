import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Expense> expenses = new ArrayList<>();
    private static String fileName = "expenses.txt";

    public static void main(String[] args) {
        loadExpenses();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Expense Tracker ===");
            System.out.println("1 - Add Expense");
            System.out.println("2 - List All Expenses");
            System.out.println("3 - Filter by Category");
            System.out.println("4 - Show Total Spending");
            System.out.println("0 - Exit");
            System.out.print("Choice: ");

            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                System.out.print("Date (e.g. 2026-11-14): ");
                String date = scanner.nextLine().trim();
                System.out.print("Category (Food, Transport, Books, Other): ");
                String cat = scanner.nextLine().trim();
                System.out.print("Amount: ");
                double amt = Double.parseDouble(scanner.nextLine().trim());
                System.out.print("Note: ");
                String note = scanner.nextLine().trim();

                expenses.add(new Expense(date, cat, amt, note));
                saveExpenses();
                System.out.println("Expense saved.");
            } else if (choice.equals("2")) {
                if (expenses.isEmpty()) {
                    System.out.println("No expenses recorded.");
                } else {
                    for (Expense e : expenses) {
                        System.out.println(e);
                    }
                }
            } else if (choice.equals("3")) {
                System.out.print("Enter category: ");
                String cat = scanner.nextLine().trim();
                double sum = 0;
                for (Expense e : expenses) {
                    if (e.getCategory().equalsIgnoreCase(cat)) {
                        System.out.println(e);
                        sum += e.getAmount();
                    }
                }
                System.out.println("Category Total: " + String.format("%.2f", sum) + " TL");
            } else if (choice.equals("4")) {
                double total = 0;
                for (Expense e : expenses) {
                    total += e.getAmount();
                }
                System.out.println("Total Spending: " + String.format("%.2f", total) + " TL");
            } else if (choice.equals("0")) {
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
        scanner.close();
    }

    private static void loadExpenses() {
        File f = new File(fileName);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(";");
                if (p.length == 4) {
                    expenses.add(new Expense(p[0], p[1], Double.parseDouble(p[2]), p[3]));
                }
            }
        } catch (Exception e) {
            System.out.println("Could not load file.");
        }
    }

    private static void saveExpenses() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            for (Expense e : expenses) {
                pw.println(e.toFileLine());
            }
        } catch (Exception e) {
            System.out.println("Could not save file.");
        }
    }
}
