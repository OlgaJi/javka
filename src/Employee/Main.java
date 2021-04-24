package Employee;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static Set<String> skills = new HashSet<String>();
    public static LocalDate dateOfBirth = LocalDate.now();
    public static LocalDate dateStartWork;

    public static void main(String[] args) {
        try {
            Employee Test = new Employee("A", "B", "C", dateOfBirth,
                    Sex.MALE, "Main Tester", new BigDecimal(100500),
                    skills, dateStartWork);
        } catch (Throwable error) {
            System.out.println("Error: ".concat(error.getMessage()));
        }


    }
}