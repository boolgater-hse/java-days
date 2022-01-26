import java.util.Scanner;
import java.util.Locale;

public class Main
{
    /**
     * Function solves quadratic equations with "two roots" case, "one root" case and
     * "imaginary part" case.
     *
     * @param a double number a
     * @param b double number b
     * @param c double number c
     */
    public static void solveQuadraticEquation(double a, double b, double c)
    {
        double d = b * b - 4 * a * c;
        if (a > 0 || a < 0)
        {
            if (d > 0)
            {
                double x1 = ((-b) + Math.sqrt(d)) / (2 * a);
                double x2 = ((-b) - Math.sqrt(d)) / (2 * a);

                System.out.println("Roots are " + x1 + ", " + x2);
            }
            if (d == 0)
            {
                double x1 = -(b / (2 * a));

                System.out.println("Root is " + x1);
            }
            if (d < 0)
            {
                double real = -b / (2 * a);
                double imaginary = Math.sqrt(-d) / (2 * a);

                System.out.print("Roots are " + real + "+" + imaginary + "i, ");
                System.out.println(real + "-" + imaginary + "i");
            }
        } else
        {
            double x1 = (-c) / b;

            System.out.println("Root is " + x1);
        }
    }

    /**
     * Main class
     *
     * @param args Arguments
     */
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.ENGLISH);

        System.out.println("Enter three doubles:");
        double a = scanner.nextDouble();
        double b = scanner.nextDouble();
        double c = scanner.nextDouble();

        solveQuadraticEquation(a, b, c);

        scanner.close();
    }
}
