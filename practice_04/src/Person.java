import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class Person {
    private final String surname;
    private final String name;
    private final String middle;

    private final LocalDate dateOfBirth;

    Person(String[] data) throws Exception {
        String regexIllegalSymbols = ".*[^а-яА-Я].*";
        String regexSplitters = "[\\.\\-/\\\\]";
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

    public String getShortName() {
        return surname.substring(0, 1).toUpperCase() + surname.substring(1) + " "
                + Character.toUpperCase(name.charAt(0)) + ". "
                + Character.toUpperCase(middle.charAt(0)) + ".";
    }

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

    public int getAge() {
        LocalDate current = LocalDate.now();

        int temp = (int) ChronoUnit.YEARS.between(dateOfBirth, current);
        if (temp < 0) {
            throw new DateTimeException("Date of birth is greater than current date");
        }

        return temp;
    }
}
