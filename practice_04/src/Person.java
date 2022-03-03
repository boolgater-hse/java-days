import java.time.DateTimeException;
import java.time.LocalDate;

public class Person {
    private final String surname;
    private final String name;
    private final String middle;

    private final LocalDate dateOfBirth;

    /**
     * Constructor of 'Person' class takes String array
     * with splinted surname, name, middle name and birthdate
     * of a person.
     * Name must be written in Russian.
     * Birthdate supports '.', '/' and '\' separators.
     *
     * @param data String array
     * @throws Exception throws when input is incorrect
     */
    Person(String[] data) throws Exception {
        String regexIllegalSymbols = ".*[^а-яА-Я].*";
        String regexSplitters = "[\\.\\/\\\\]";
        String[] splintedDate;

        try {
            surname = data[0];
            name = data[1];
            middle = data[2];
            splintedDate = data[3].split(regexSplitters);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Wrong input");
        }

        if (surname.matches(regexIllegalSymbols) || name.matches(regexIllegalSymbols) || middle.matches(regexIllegalSymbols)) {
            throw new Exception("Name contains numbers, English letters or other illegal symbols");
        }

        try {
            dateOfBirth = LocalDate.of(Integer.parseInt(splintedDate[2]), Integer.parseInt(splintedDate[1]), Integer.parseInt(splintedDate[0]));
        } catch (DateTimeException e) {
            throw new DateTimeException("Wrong date of birth format");
        }
    }

    /**
     * Returns short name of a person in format Surname (N.)ame (M.)iddle name
     *
     * @return String
     */
    public String getShortName() {
        return surname.substring(0, 1).toUpperCase() + surname.substring(1) + " "
                + Character.toUpperCase(name.charAt(0)) + ". "
                + Character.toUpperCase(middle.charAt(0)) + ".";
    }

    /**
     * Return gender of a person or 'undefined' when gender cannot be determined.
     *
     * @return String
     * @throws Exception throws when gender of a person cannot be determined
     */
    public String getGender() throws Exception {
        String[] femaleEndings = new String[]{"овна", "евна", "ична", "инична"};
        String[] maleEndings = new String[]{"ович", "евич", "ич"};

        for (int i = 0; i < femaleEndings.length; ++i) {
            if (middle.contains(femaleEndings[i])) {
                return "Женский";
            }
        }

        for (int i = 0; i < maleEndings.length; ++i) {
            if (middle.contains(maleEndings[i])) {
                return "Мужской";
            }
        }

        throw new Exception("Gender is undefined");
    }

    /**
     * Help function for getAge()
     * Counts number of leap years to 'date'.
     *
     * @return int
     */
    private int countLeapYears(LocalDate date) {
        int years = date.getYear();

        if (date.getMonthValue() <= 2) {
            years--;
        }

        return years / 4 - years / 100 + years / 400;
    }

    /**
     * Returns age of a person.
     *
     * @return integer
     */
    public int getAge() {
        LocalDate current = LocalDate.now();

        int[] daysInMonths = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        int daysBeforeBirth = dateOfBirth.getYear() * 365 + dateOfBirth.getDayOfMonth();
        for (int i = 0; i < dateOfBirth.getMonthValue() - 1; ++i) {
            daysBeforeBirth += daysInMonths[i];
        }
        daysBeforeBirth += countLeapYears(dateOfBirth);

        int daysBeforeNow = current.getYear() * 365 + current.getDayOfMonth();
        for (int i = 0; i < current.getMonthValue() - 1; ++i) {
            daysBeforeNow += daysInMonths[i];
        }
        daysBeforeNow += countLeapYears(current);

        final double oneDayInYears = 0.002738;
        double temp = ((daysBeforeNow - daysBeforeBirth) * oneDayInYears);
        if (temp < 0) {
            throw new DateTimeException("Date of birth is greater than current date");
        }

        return (int) temp;
    }
}
