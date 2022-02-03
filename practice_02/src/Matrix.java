public class Matrix
{
    private Complex[][] data;
    private int n, m;

    public Matrix(final int n, final int m)
    {
        if (n < 1 || m < 1)
        {
            return;
        }

        this.n = n;
        this.m = m;
        this.data = new Complex[n][m];
        for (int i = 0; i < n; ++i)
        {
            for (int j = 0; j < m; ++j)
            {
                this.data[i][j] = new Complex(0);
            }
        }
    }

    public Matrix(final int n)
    {
        this(n, n);
    }

    public Matrix(final Matrix other)
    {
        this.n = other.n;
        this.m = other.m;
        this.data = new Complex[other.n][other.m];
        for (int i = 0; i < n; ++i)
        {
            for (int j = 0; j < m; ++j)
            {
                this.data[i][j] = new Complex(0);
                this.data[i][j] = other.get(i, j);
            }
        }
    }

    public void set(final int i, final int j, Complex element)
    {
        if (i < 0 || j < 0)
        {
            return;
        }

        this.data[i][j] = element;
    }

    public final int getN()
    {
        return this.n;
    }

    public final int getM()
    {
        return this.m;
    }

    public final Complex get(final int i, final int j)
    {
        if (i < 0 || j < 0)
        {
            return null;
        }

        return this.data[i][j];
    }

    public Matrix add(final Matrix other)
    {
        if (this.n != other.n || this.m != other.m)
        {
            return this;
        }

        Matrix temp = new Matrix(this);

        for (int i = 0; i < this.n; ++i)
        {
            for (int j = 0; j < this.m; ++j)
            {
                temp.set(i, j, temp.get(i, j).add(other.get(i, j)));
            }
        }

        return temp;
    }

    public Matrix multiply(final Matrix other)
    {
        if (this.m != other.n)
        {
            return this;
        }

        Matrix temp = new Matrix(this.n, other.m);
        for (int i = 0; i < temp.n; ++i)
        {
            for (int j = 0; j < temp.m; ++j)
            {
                Complex sum = new Complex(0);
                for (int k = 0; k < this.m; ++k)
                {
                    sum.equals(sum.add(this.data[i][k].multiply(other.get(k, j))));
                }
                temp.set(i, j, sum);
            }
        }

        return temp;
    }

    private void subMatrix(Matrix mat, Matrix temp, int p, int q, int _n)
    {
        int i = 0;
        int j = 0;
        for (int row = 0; row < _n; ++row)
        {
            for (int col = 0; col < _n; ++col)
            {
                if (row != p && col != q)
                {
                    temp.set(i, j++, mat.get(row, col));
                    if (j == _n - 1)
                    {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }

    private Complex determinantOfMatrix(Matrix mat, int _n)
    {
        Complex determinant = new Complex(0);

        if (_n == 1)
        {
            return mat.get(0, 0);
        }
        if (_n == 2)
        {
            return mat.get(0, 0).multiply(mat.get(1, 1)).subtract(mat.get(0, 1).multiply(mat.get(1, 0)));
        }

        Matrix temp = new Matrix(_n);
        int sign = 1;
        for (int i = 0; i < _n; ++i)
        {
            subMatrix(mat, temp, 0, i, _n);
            determinant.equals(determinant.add(mat.get(0, i).multiply(determinantOfMatrix(temp, _n - 1).multiply(sign))));
            sign *= -1;
        }

        return determinant;
    }

    public Complex getDeterminant()
    {
        if (this.n != this.m)
        {
            return new Complex(0);
        }

        return determinantOfMatrix(this, this.n);
    }

    public Matrix transpose()
    {
        Matrix temp = new Matrix(this.m, this.n);

        for (int i = 0; i < this.n; ++i)
        {
            for (int j = 0; j < this.m; ++j)
            {
                temp.set(j, i, this.data[i][j]);
            }
        }

        this.equals(temp);

        return this;
    }

    public void equals(final Matrix other)
    {
        if (this.n != other.n || this.m != other.m)
        {
            return;
        }

        for (int i = 0; i < n; ++i)
        {
            for (int j = 0; j < m; ++j)
            {
                this.data[i][j] = other.get(i, j);
            }
        }
    }

    public final void print()
    {
        for (int i = 0; i < this.n; ++i)
        {
            for (int j = 0; j < this.m; ++j)
            {
                System.out.print(this.get(i, j).algebraic() + " ");
            }
            System.out.println();
        }
    }
}
