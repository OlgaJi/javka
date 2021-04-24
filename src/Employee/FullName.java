package Employee;

public class FullName {
    private final String firstName;
    private final String lastName;
    private final String fathersName;

    public FullName(String firstName, String lastName, String fathersName) throws IllegalArgumentException {

        this.firstName = firstName;
        this.lastName = lastName;
        this.fathersName = fathersName;
        if (lastName.isEmpty() || firstName.isEmpty()) {
            throw new IllegalArgumentException("Every man has first and last name");
        }
        if (lastName.matches("0-9") || firstName.matches("0-9") || fathersName.matches("0-9")) {
            throw new IllegalArgumentException("There must not be numbers in the name");
        }
    }

    public String getFullName() {
        return firstName.concat(" ").concat(fathersName).concat(" ").concat(lastName);
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFathersName() {
        return fathersName;
    }
}
