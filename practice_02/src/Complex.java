public class Complex
{
    private double real;
    private double unit;

    public Complex(final double number, final double imaginary)
    {
        this.real = number;
        this.unit = imaginary;
    }

    public Complex(final double number)
    {
        this(number, 0);
    }

    public Complex(final Complex other)
    {
        this.real = other.real;
        this.unit = other.unit;
    }

    public void equals(final Complex other)
    {
        this.real = other.real;
        this.unit = other.unit;
    }

    public Complex add(final Complex other)
    {
        return new Complex(this.real + other.real,
                this.unit + other.unit);
    }

    public Complex add(final double other)
    {
        return new Complex(this.real + other,
                this.unit);
    }

    public Complex subtract(final Complex other)
    {
        return new Complex(this.real - other.real,
                this.unit - other.unit);
    }

    public Complex subtract(final double other)
    {
        return new Complex(this.real - other,
                this.unit);
    }

    public Complex multiply(final Complex other)
    {
        return new Complex(this.real * other.real - this.unit * other.unit,
                this.real * other.unit + this.unit * other.real);
    }

    public Complex multiply(final double other)
    {
        return new Complex(this.real * other,
                this.unit * other);
    }

    public Complex divide(final Complex other)
    {
        double d = other.real * other.real + other.unit * other.unit;
        double tempReal = this.real * other.real + this.unit * other.unit;
        double tempUnit = other.real * this.unit - this.real * other.unit;

        if (d == 0)
        {
            return this;
        }

        return new Complex(tempReal / d, tempUnit / d);
    }

    public Complex divide(final double other)
    {
        if (other == 0)
        {
            return this;
        }

        return new Complex(this.real / other,
                this.unit / other);
    }

    public final double modulus()
    {
        return Math.sqrt(Math.pow(this.real, 2) + Math.pow(this.unit, 2));
    }

    public final double argument()
    {
        double atan = Math.atan(this.unit / this.real);

        if (this.real > 0)
        {
            return atan;
        }
        else if (this.real < 0 && this.unit >= 0)
        {
            return atan + Math.PI;
        }
        else if (this.real < 0 && this.unit < 0)
        {
            return atan - Math.PI;
        }
        else if (this.real == 0 && this.unit > 0)
        {
            return Math.PI / 2;
        }
        else if (this.real == 0 && this.unit < 0)
        {
            return -1 * (Math.PI / 2);
        }
        else
        {
            return 0;
        }
    }

    public final String algebraic()
    {
        if (this.unit > 0)
        {
            return this.real + "+" + this.unit + "i";
        }
        else if (this.unit < 0)
        {
            return this.real + "" + this.unit + "i";
        }
        else
        {
            return this.real + "";
        }
    }

    public final String trigonometric()
    {
        return this.modulus() + " * " + "(cos(arctan(" + (this.unit / this.real) + ")" + " + " + "sin(arctan(" + (this.unit / this.real) + ")i)";
    }
}
