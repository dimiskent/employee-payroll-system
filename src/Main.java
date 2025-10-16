import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String fileName = "employees.ser";
        List<Employee> employees = new ArrayList<>();
        boolean go = true;

        try(ObjectInputStream stream = new ObjectInputStream(new FileInputStream(fileName))) {
            employees.addAll((List<Employee>) stream.readObject());
        } catch (FileNotFoundException e) {
            System.out.println("No list found, creating new session.");
        } catch (ClassNotFoundException e) {
            System.out.println("Employee list couldn't be read due to a class error.");
        } catch (IOException e) {
            System.out.println("A file I/O exception has occurred! " + e.getMessage());
        }

        do {
            System.out.println("Payroll Viewer");
            System.out.println("0) Exit");
            System.out.println("1) View Payrolls");
            System.out.println("2) Add Employee");
            System.out.println("3) Reset List");
            System.out.print("=> ");
            switch (scanner.nextLine().charAt(0)) {
                case '0' -> go = false;
                case '1' -> {
                    if(!employees.isEmpty()) {
                        double sum = 0;
                        for (Employee employee : employees) {
                            System.out.println("-----------------------");
                            System.out.println("Name: " + employee.getName());
                            System.out.println("Position: " + employee.getPosition());
                            System.out.println("Salary: " + employee.getSalary());
                            sum += employee.getSalary();
                        }
                        System.out.println("-----------------------");
                        System.out.println("Total: " + sum);
                    } else System.out.println("No employees found! Add some!");
                }
                case '2' -> {
                    String name, position;
                    while (true) {
                        System.out.print("Name: ");
                        name = scanner.nextLine().trim();
                        System.out.print("Position: ");
                        position = scanner.nextLine().trim();
                        if(name.isEmpty() || position.isEmpty()) System.out.println("Please make sure to fill everything correctly!");
                        else break;
                    }
                    double salary;
                    while (true) {
                        System.out.print("Salary: ");
                        if(scanner.hasNextDouble()) {
                            salary = scanner.nextDouble();
                            break;
                        } else {
                            System.out.println("Please input a number!");
                            scanner.nextLine();
                        }
                    }
                    employees.add(new Employee(name, position, salary));
                    save(fileName, employees);
                    System.out.println("Added employee!");
                    if(scanner.hasNextLine()) scanner.nextLine();
                }
                case '3' -> {
                    employees = new ArrayList<>();
                    save(fileName, employees);
                    System.out.println("Reset successfully :)");
                }
            }
        } while (go);
        System.out.println("Goodbye!");
        scanner.close();
    }
    public static void save(String fileName, List<Employee> employees) {
        try(ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            stream.writeObject(employees);
        } catch (IOException e) {
            System.out.println("Couldn't save file! Reason: " + e.getMessage());
        }
    }
}
