import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Person {
    private final String name;
    private final String middle;
    private final String surname;

    LocalDate dateOfBirth;

    Person(String[] data) {
        surname = data[0];
        name = data[1];
        middle = data[2];
        String[] temp = data[3].split("\\.");

        for (int i = 0; i < 3; ++i) {
            try {
                dateOfBirth = LocalDate.of(Integer.parseInt(temp[2]), Integer.parseInt(temp[1]), Integer.parseInt(temp[0]));
            } catch (DateTimeException e) {
                throw new DateTimeException("Wrong date of birth");
            }
        }
    }

    String getShortName() {
        return surname + " " + name.charAt(0) + ". " + middle.charAt(0) + ".";
    }

    String getGender() {
        String[] maleEndings = new String[]{"ович", "евич", "ич"};
        String[] femaleEndings = new String[]{"овна", "евна", "ична", "инична"};

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


        return "не определен";
    }

    int getAge() {
        LocalDate current = LocalDate.now();

        return (int) ChronoUnit.YEARS.between(dateOfBirth, current);
    }
}
