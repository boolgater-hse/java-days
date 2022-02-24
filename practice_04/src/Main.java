import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        Person person = new Person(scanner.nextLine().trim().split("\\s+"));

        System.out.println(person.getShortName());
        System.out.println("Пол: " + person.getGender());
        System.out.println("Возраст: " + person.getAge());

        scanner.close();
    }
}
