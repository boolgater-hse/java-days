import java.util.Random;

public class Main {
    public static void main(String[] args) {
        testComplex();

        testMatrix();
    }

    public static void testComplex() {
        Random random = new Random();

        System.out.print("Complex numbers:\n");

        Complex a = new Complex((double) (random.nextInt(9)) + 1, random.nextInt(8) + 1);
        Complex b = new Complex((double) (random.nextInt(9)) + 1, random.nextInt(8) + 1);

        System.out.println("a = " + a.algebraic());
        System.out.println("b = " + b.algebraic() + "\n");

        System.out.println("Adding:");
        System.out.println((a.add(b)).algebraic());
        System.out.println();

        System.out.println("Subtract:");
        System.out.println((a.subtract(b)).algebraic());
        System.out.println();

        System.out.println("Multiple:");
        System.out.println((a.multiply(b)).algebraic());
        System.out.println();

        System.out.println("Divide:");
        System.out.println((a.divide(b)).algebraic());
        System.out.println();

        System.out.println("Modulus:");
        double modulus = a.modulus();
        System.out.println(modulus);
        System.out.println();

        System.out.println("Argument:");
        double argument = a.argument();
        System.out.println(argument);
        System.out.println();

        System.out.println("Algebraic:");
        System.out.println(a.algebraic());
        System.out.println();

        System.out.println("Trigonometric:");
        System.out.println(a.trigonometric());
        System.out.println();
    }

    public static void testMatrix() {
        Random random = new Random();

        System.out.println("Random matrix:");
        Matrix mat = new Matrix(3);
        for (int i = 0; i < mat.getN(); ++i) {
            for (int j = 0; j < mat.getM(); ++j) {
                mat.set(i, j, new Complex((double) (random.nextInt(9)) + 1, random.nextInt(3)));
            }
        }
        mat.print();
        System.out.println();

        System.out.println("Second random matrix:");
        Matrix mat_two = new Matrix(3);
        for (int i = 0; i < mat.getN(); ++i) {
            for (int j = 0; j < mat.getM(); ++j) {
                mat_two.set(i, j, new Complex((double) (random.nextInt(9)) + 1, random.nextInt(2)));
            }
        }
        mat_two.print();
        System.out.println();

        System.out.println("First add second:");
        Matrix mat_add = new Matrix(mat.add(mat_two));
        mat_add.print();
        System.out.println();

        System.out.println("First multiply second:");
        Matrix mat_mult = new Matrix(mat.multiply(mat_two));
        mat_mult.print();
        System.out.println();

        System.out.print("First matrix determinant: ");
        Complex mat_det = new Complex(mat.getDeterminant());
        System.out.println(mat_det.algebraic());
        System.out.println();

        System.out.println("First matrix transpose:");
        Matrix mat_transpose = new Matrix(mat.transpose());
        mat_transpose.print();
    }
}
