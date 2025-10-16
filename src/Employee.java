import java.io.Serializable;

public class Employee implements Serializable {
    private final double salary;
    private final String name, position;

    public Employee(String name, String position, double salary) {
        this.name = name;
        this.position = position;
        this.salary = Math.max(salary, 1);
    }

    public double getSalary() {
        return salary;
    }

    public String getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }
}
