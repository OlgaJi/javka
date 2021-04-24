package Employee;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Set;

public class Employee {
    private final int id;
    private static int globalId;
    private final FullName fullName;
    private final LocalDate birthday;
    private final Sex sex;
    private String post;
    private BigDecimal salary; //для денег лучше использовать этот тип, а не float
    private Set<String> skills;
    private final LocalDate dateOfStart;
    private boolean fired;
    private LocalDate dateOfFired;

    public static int howManyEmployeesEverBeen() {
        return globalId;
    }

    public Employee(String firstName, String lastName, String fathersName, LocalDate birthday,
                    Sex sex, String post, BigDecimal salary, Set<String> skills, LocalDate dateOfStart) throws Exception {

        this.post = post;
        this.id = globalId++;
        globalId++;
        this.birthday = birthday;
        int age = birthday.compareTo(LocalDate.now());
        if (age < 16) {
            throw new Exception("Age must be more than 16");
        }
        this.sex = sex;
        this.salary = salary;
        if (salary.compareTo(new BigDecimal(0)) == -1) {
            throw new IllegalArgumentException("Salary must not be negative");
        }
        this.skills = skills;
        this.dateOfStart = dateOfStart;
        if (dateOfStart.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Quantity of the workdays must be more than 0");
        }
        this.fullName = new FullName(firstName, lastName, fathersName);
    }

    public int getId() {
        return id;
    }

    public Sex getSex() {
        return sex;
    }

    public String getFullName() {
        return fullName.getFullName();
    }

    public String getFirstName() {
        return fullName.getFirstName();
    }

    public String getLastName() {
        return fullName.getLastName();
    }

    public String getFathersName() {
        return fullName.getFathersName();
    }

    public void setSalary(BigDecimal salary) {
        if (salary.compareTo(new BigDecimal(0)) == -1) {
            throw new IllegalArgumentException("Salary must not be negative");
        } else {
            this.salary = salary;
        }
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getPost() {
        return post;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public boolean isBirthdayInDate(LocalDate date) {
        return (birthday.get(ChronoField.DAY_OF_MONTH) == date.get(ChronoField.DAY_OF_MONTH)) &&
                (birthday.get(ChronoField.MONTH_OF_YEAR) == date.get(ChronoField.MONTH_OF_YEAR));
    }

    public boolean isBirthdayToday() {
        return isBirthdayInDate(LocalDate.now());
    }

    public int getAge() {
        return birthday.compareTo(LocalDate.now());
    }

    public int getSeniority() {
        return dateOfStart.compareTo(LocalDate.now());
    }

    public void setFired() {
        this.fired = true;
        this.dateOfFired = LocalDate.now();
    }

    public LocalDate getDayOfFired() throws Exception {
        if (fired) {
            return dateOfFired;
        } else {
            throw new Exception("The employee is not fired");
        }
    }

    public boolean isFired() {
        return fired;
    }

    //   public void addSkill() {

    //  }
}

